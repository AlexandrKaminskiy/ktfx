import de.javagl.obj.Obj
import graphics.PointMatch
import graphics.Polygon
import graphics.ShapesAlgos
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import linear.Matrix4x4
import linear.Vector2D
import linear.Vector3D
import linear.Vector4D
import provider.impl.ProjectionMatrixProviderImpl
import provider.impl.ViewMatrixProviderImpl
import provider.impl.ViewportMatrixProviderImpl
import validator.PolygonValidator
import validator.impl.PolygonValidatorImpl
import java.io.File
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
    private var texes: MutableList<Vector2D>
    private val faces: MutableList<PointMatch>
    private val shapesAlgos = ShapesAlgos(zBuffer)
    private val validator: PolygonValidator = PolygonValidatorImpl()
    private val viewMatrix = ViewMatrixProviderImpl()
    private val projectionMatrix = ProjectionMatrixProviderImpl(width, height, angle)
    private val viewport = ViewportMatrixProviderImpl(width, height, xMin, yMin)
    private var light4D = Vector4D(0.0, 0.0, -10.0, 0.0)

    init {
//        val musicFile = "saul-goodman-head/saul.mp3"
//
//        val sound = Media(File(musicFile).toURI().toString())
//        val mediaPlayer = MediaPlayer(sound)
//        mediaPlayer.play()

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

        texes = mutableListOf()
        for (i in 0 until obj.numTexCoords) {
            texes.add(Vector2D(obj.getTexCoord(i).x.toDouble(), obj.getTexCoord(i).y.toDouble()))
        }

        faces = mutableListOf()
        for (i in 0 until obj.numFaces) {
            val face = obj.getFace(i)
            faces.add(
                    PointMatch(
                            face.getVertexIndex(0),
                            face.getVertexIndex(1),
                            face.getVertexIndex(2),
                            face.getTexCoordIndex(0),
                            face.getTexCoordIndex(1),
                            face.getTexCoordIndex(2)
                    )
            )
//            faces.add(
//                    PointMatch(
//                            face.getVertexIndex(0),
//                            face.getVertexIndex(2),
//                            face.getVertexIndex(3),
//                            face.getTexCoordIndex(0),
//                            face.getTexCoordIndex(2),
//                            face.getTexCoordIndex(3)
//                    )
//            )
        }
    }

    fun calculate(transformation: Matrix4x4) {
        vectors4D = vectors4D.map { transformation x it }.toMutableList()
        light4D = transformation x light4D
        val light = Vector3D(light4D.x, light4D.y, light4D.z)
        customThreadPool.run {
            faces.parallelStream()
                    .map {
                        Polygon(
                                arrayListOf(vectors4D[it.aV], vectors4D[it.bV], vectors4D[it.cV]),
                                arrayListOf(vectors4D[it.aV], vectors4D[it.bV], vectors4D[it.cV]),
                                arrayListOf(texes[it.aT], texes[it.bT], texes[it.cT]),
                                0
                        )
                    }
                    .peek { it.vectors = it.vectors.stream().map { v -> v x viewMatrix.provide() }.toList() }
                    .peek { it.vectors = it.vectors.stream().map { v -> v x projectionMatrix.provide() }.toList() }
                    .peek { it.vectors = it.vectors.stream().map { v -> v x viewport.provide() }.toList() }
                    .peek { it.vectors = it.vectors.stream().map { v -> v * (1 / v.w) }.toList() }
                    .filter { validator.validateVisibility(it) }

                    .filter { validator.validateSizeConstraints(it, width, height) }

                    .forEach {
                        shapesAlgos.triangle(it, light, transformation)
                    }
        }

    }

}
