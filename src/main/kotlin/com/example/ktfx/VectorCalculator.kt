import de.javagl.obj.Obj
import graphics.*
import linear.Matrix4x4
import linear.Vector3D
import linear.Vector4D
import provider.impl.ProjectionMatrixProviderImpl
import provider.impl.ViewMatrixProviderImpl
import provider.impl.ViewportMatrixProviderImpl
import validator.PolygonValidator
import validator.impl.PolygonValidatorImpl
import java.util.concurrent.ForkJoinPool

class VectorCalculator(
        private val width: Double,
        private val height: Double,
        angle: Int,
        xMin: Double,
        yMin: Double,
        obj: Obj,
) {

    private val customThreadPool = ForkJoinPool(4)
    private var vectors4D: MutableList<Vector4D>
    private val faces: MutableList<PointMatch>
    private val shapesAlgos = ShapesAlgos()
    private val validator: PolygonValidator = PolygonValidatorImpl()
    private val zBuffer = ZBuffer()
    private val lighting = Lighting()

    private val viewMatrix = ViewMatrixProviderImpl()
    private val projectionMatrix = ProjectionMatrixProviderImpl(width, height, angle)
    private val viewport = ViewportMatrixProviderImpl(width, height, xMin, yMin)

    init {
        val translation = Matrix4x4(
                arrayOf(
                        doubleArrayOf(1.0, 0.0, 0.0, 0.0),
                        doubleArrayOf(0.0, 1.0, 0.0, 0.0),
                        doubleArrayOf(0.0, 0.0, 1.0, 0.0),
                        doubleArrayOf(0.0, 0.0, 0.0, 1.0)
                )
        )
        vectors4D = mutableListOf()
        for (i in 0 until obj.numVertices) {
            vectors4D.add(
                    translation x Vector4D(
                            obj.getVertex(i).x.toDouble(),
                            obj.getVertex(i).y.toDouble(),
                            obj.getVertex(i).z.toDouble(),
                            1.0
                    )
            )
        }
        faces = mutableListOf()
        for (i in 0 until obj.numFaces) {
            val face = obj.getFace(i)
            faces.add(PointMatch(face.getVertexIndex(0), face.getVertexIndex(1), face.getVertexIndex(2)))
        }
    }

    fun calculate(transformation: Matrix4x4): List<Point> {
        vectors4D = vectors4D.map { transformation x it }.toMutableList()

        val points: List<Point> = customThreadPool.run {
            faces.parallelStream()
                    .map { Polygon(arrayListOf(vectors4D[it.a], vectors4D[it.b], vectors4D[it.c]), 0, 0.0) }
                    .peek { it.color = lighting.lambertCalculation(it, Vector3D(10.0, 0.0, 0.0)) }
                    .peek { it.vectors = it.vectors.stream().map { v -> v x viewMatrix.provide() }.toList() }
                    .filter { validator.validateVisibility(it) }
                    .peek { it.zBufferValue = zBuffer.getZBufferValue(it, Vector3D(0.0, 0.0, 0.0)) }
                    .peek { it.vectors = it.vectors.stream().map { v -> v x projectionMatrix.provide() }.toList() }
                    .peek { it.vectors = it.vectors.stream().map { v -> v x viewport.provide() }.toList() }
                    .peek { it.vectors = it.vectors.stream().map { v -> v * (1 / v.w) }.toList() }
                    .peek { println(it.color) }
                    .filter { validator.validateSizeConstraints(it, width, height) }

                    .flatMap {
                        shapesAlgos.triangle(it).stream()
                    }
                    .sorted(Comparator.comparingDouble<Point?> { it.z }.reversed())
                    .toList()
        }


//        val transformedVectors = customThreadPool.submit<List<Vector3D>> {
//            vectors4D.parallelStream()
//                    .map { it x viewMatrix.provide() }
//                    .map { it x projectionMatrix.provide() }
//                    .map { it x viewport.provide() }
//                    .map { it * (1 / it.w) }
//                    .map { Vector3D(it.x, it.y, it.z) }
//                    .toList()
//        }.get()

//
//        val points = customThreadPool.submit<List<Point>> {
//            faces.parallelStream()
//                    .map { Polygon(arrayListOf(transformedVectors[it.a], transformedVectors[it.b], transformedVectors[it.c]), 0, 0) }
//                    .filter { validator.validateVisibility(it) }
//                    .filter { validator.validateSizeConstraints(it, width, height) }
//                    .toList().stream()
//                    .peek { it.zBufferValue = zBuffer.getZBufferValue(it, Vector3D(0.0, 0.0, 10.0)) }
//                    .peek { it.color = lighting.lambertCalculation(it, Vector3D(0.0, 10.0, 10.0)) }
//                    .sorted { o1, o2 -> o1.zBufferValue - o2.zBufferValue }
//                    .flatMap {
//                        shapesAlgos.triangle(it).stream()
//                    }.toList()
//        }.get()

//        println(points.size)
        return points
    }

}
