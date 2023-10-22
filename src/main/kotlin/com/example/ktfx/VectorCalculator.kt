import de.javagl.obj.Obj
import graphics.Point
import graphics.PointMatch
import graphics.Polygon
import graphics.ShapesAlgos
import linear.Matrix4x4
import linear.Vector3D
import linear.Vector4D
import validator.PolygonValidator
import validator.impl.PolygonValidatorImpl
import java.util.Collections
import java.util.concurrent.ForkJoinPool

class VectorCalculator(
    val width: Double,
    val height: Double,
    obj: Obj,
    initializer: MatrixInitializer
) {

    private val customThreadPool = ForkJoinPool(4)
    private var vectors4D: MutableList<Vector4D>
    private val faces: MutableList<PointMatch>
    private val matrixInitializer: MatrixInitializer = initializer
    private val shapesAlgos = ShapesAlgos()
    private val polygonValidator: PolygonValidator = PolygonValidatorImpl();

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

        val transformedVectors = customThreadPool.submit<List<Vector3D>> {
            vectors4D.parallelStream()

                .map { it x matrixInitializer.viewMatrix }
                .map { it x matrixInitializer.projectionMatrix }
                .map { it * (1 / it.w) }
//                .map { it x matrixInitializer.viewPortMatrix }
                .map { Vector3D(it.x, it.y, it.z) }
                .toList()
        }.get()


        return faces.parallelStream()
            .map {
                Polygon(
                    arrayListOf(
                        transformedVectors[it.a],
                        transformedVectors[it.b],
                        transformedVectors[it.c]
                    ), 1
                )
            }
//            .filter { polygonValidator.validateSizeConstraints(it, width, height) }
            .filter { polygonValidator.validateVisibility(it, Vector3D(0.0, 0.0, -10.0)) }
            .peek {
                it.vectors = it.vectors.stream().map { v3 ->
                    Vector4D(
                        v3.x, v3.y, v3.z, 1.0
                    )
                }.map { v4 -> v4 x matrixInitializer.viewPortMatrix }.map { v1 -> Vector3D(v1.x, v1.y, v1.z) }.toList()
            }
            .flatMap {
                shapesAlgos.triangle(
                    it
                ).stream()
            }.toList()

    }
}
