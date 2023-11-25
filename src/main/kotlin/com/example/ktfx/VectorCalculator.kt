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
        zBuffer: ZBuffer,
        private val width: Double,
        private val height: Double,
        angle: Int,
        xMin: Double,
        yMin: Double,
        obj: Obj,
) {

    private val customThreadPool = ForkJoinPool(4)
    private var vectors4D: MutableList<Vector4D>
    private var normals: MutableList<Vector3D>
    private val faces: MutableList<PointMatch>
    private val shapesAlgos = ShapesAlgos(zBuffer)
    private val validator: PolygonValidator = PolygonValidatorImpl()

    private val viewMatrix = ViewMatrixProviderImpl()
    private val projectionMatrix = ProjectionMatrixProviderImpl(width, height, angle)
    private val viewport = ViewportMatrixProviderImpl(width, height, xMin, yMin)
    private var light4D = Vector4D(0.0, 0.0, -10.0, 0.0)

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
        normals = mutableListOf()
        for (i in 0 until obj.numVertices) {
            normals.add(
                    Vector3D(
                            obj.getNormal(i).x.toDouble(),
                            obj.getNormal(i).y.toDouble(),
                            obj.getNormal(i).z.toDouble(),
                    )
            )
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

    fun calculate(transformation: Matrix4x4) {
        vectors4D = vectors4D.map { transformation x it }.toMutableList()
        normals = normals.map { transformation x Vector4D(it) }.map { Vector3D(it) }.toMutableList()
        light4D = transformation x light4D
        val light = Vector3D(light4D.x, light4D.y, light4D.z)
        customThreadPool.run {
            faces.parallelStream()
                    .map {
                        Polygon(
                                arrayListOf(vectors4D[it.a], vectors4D[it.b], vectors4D[it.c]),
                                arrayListOf(normals[it.a], normals[it.b], normals[it.c]),
                                arrayListOf(vectors4D[it.a], vectors4D[it.b], vectors4D[it.c]),
                                0
                        )
                    }
//                    .peek { it.color d= lighting.lambertCalculation(it, light) }
                    .peek { it.vectors = it.vectors.stream().map { v -> v x viewMatrix.provide() }.toList() }
//                    .peek { it.startVectors = it.startVectors.stream().map { v -> v x viewMatrix.provide() }.toList() }
                    .peek { it.vectors = it.vectors.stream().map { v -> v x projectionMatrix.provide() }.toList() }
                    .peek { it.vectors = it.vectors.stream().map { v -> v x viewport.provide() }.toList() }
                    .peek { it.vectors = it.vectors.stream().map { v -> v * (1 / v.w) }.toList() }
                    .filter { validator.validateVisibility(it) }

                    .filter { validator.validateSizeConstraints(it, width, height) }

                    .forEach {
                        shapesAlgos.triangle(it, light)
                    }
        }

    }

}
