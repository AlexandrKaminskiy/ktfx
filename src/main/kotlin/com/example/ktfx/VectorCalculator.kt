import de.javagl.obj.Obj
import graphics.DdaAlgo
import graphics.Point
import graphics.PointMatch
import linear.Matrix4x4
import linear.Vector4D
import java.util.concurrent.ForkJoinPool
import java.util.stream.Stream

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
    private val ddaAlgo = DdaAlgo()

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
            faces.add(PointMatch(face.getVertexIndex(0), face.getVertexIndex(1)))
            faces.add(PointMatch(face.getVertexIndex(1), face.getVertexIndex(2)))
            faces.add(PointMatch(face.getVertexIndex(0), face.getVertexIndex(2)))
        }
    }

    fun calculate(transformation: Matrix4x4): List<Point> {
        vectors4D = vectors4D.map { transformation x it }.toMutableList()

        val transformedVectors = customThreadPool.submit<List<Vector4D>> {
            vectors4D.parallelStream()
                    .map { it x matrixInitializer.finalMatrix }
                    .map { it * (1 / it.w) }
                    .toList()
        }.get()


        return customThreadPool.submit<List<Point>> {
            faces.parallelStream()
                    .flatMap {
                        if (validPointCondition(transformedVectors[it.a], transformedVectors[it.b]))
                            ddaAlgo.process(transformedVectors[it.a], transformedVectors[it.b]).stream()
                        else
                            Stream.empty()
                    }.toList()
        }.get()

    }

    private fun validPointCondition(v: Vector4D, v2: Vector4D) =
            v.x > 0 && v.y > 0 && v.x < width && v.y < height &&
                    v2.x > 0 && v2.y > 0 && v2.x < width && v2.y < height
}
