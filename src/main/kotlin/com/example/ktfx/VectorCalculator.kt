import de.javagl.obj.Obj
import graphics.PointMatch
import graphics.Polygon
import graphics.ShapesAlgos
import linear.Matrix4x4
import linear.Vector2D
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
        val width: Double,
        val height: Double,
        angle: Int,
        xMin: Double,
        yMin: Double,
        obj: Obj,
        cube: Obj,
) {

    private val customThreadPool = ForkJoinPool(4)
    private var cubeVectors: MutableList<Vector4D>
    private var vectors4D: MutableList<Vector4D>
    private var texes: MutableList<Vector2D>
    private var cubeTexes: MutableList<Vector2D>
    private val faces: MutableList<PointMatch>
    private val cubeFaces: MutableList<PointMatch>
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
                        doubleArrayOf(100.0, 0.0, 0.0, 0.0),
                        doubleArrayOf(0.0, 100.0, 0.0, 0.0),
                        doubleArrayOf(0.0, 0.0, 100.0, 0.0),
                        doubleArrayOf(0.0, 0.0, 0.0, 1.0)
                )
        )

        cubeVectors = mutableListOf()
        for (i in 0 until cube.numVertices) {

            cubeVectors.add(
                    translation x Vector4D(
                            cube.getVertex(i).x.toDouble(),
                            cube.getVertex(i).y.toDouble(),
                            cube.getVertex(i).z.toDouble(),
                            1.0
                    )
            )
        }

        vectors4D = mutableListOf()
        for (i in 0 until obj.numVertices) {

            vectors4D.add(
                    Vector4D(
                            obj.getVertex(i).x.toDouble(),
                            obj.getVertex(i).y.toDouble(),
                            obj.getVertex(i).z.toDouble(),
                            1.0
                    )
            )
        }
        cubeTexes = mutableListOf()
        for (i in 0 until cube.numTexCoords) {
            cubeTexes.add(Vector2D(cube.getTexCoord(i).x.toDouble(), cube.getTexCoord(i).y.toDouble()))
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
        }

        cubeFaces = mutableListOf()
        for (i in 0 until cube.numFaces) {
            val face = cube.getFace(i)
            cubeFaces.add(
                    PointMatch(
                            face.getVertexIndex(0),
                            face.getVertexIndex(1),
                            face.getVertexIndex(2),
                            face.getTexCoordIndex(0),
                            face.getTexCoordIndex(1),
                            face.getTexCoordIndex(2)
                    )
            )
        }

    }

    fun calculate(transformation: Matrix4x4) {
//        cubeVectors = cubeVectors.map { transformation x it }.toMutableList()
        val cubeStartVectors = cubeVectors.map { transformation x it }.toMutableList()
        vectors4D = vectors4D.map { transformation x it }.toMutableList()
        light4D = transformation x light4D
        val light = Vector3D(light4D.x, light4D.y, light4D.z)

        var cubePolygons = cubeFaces.parallelStream()
                .map {
                    Polygon(
                            arrayListOf(cubeVectors[it.aV], cubeVectors[it.bV], cubeVectors[it.cV]),
                            arrayListOf(cubeStartVectors[it.aV], cubeStartVectors[it.bV], cubeStartVectors[it.cV]),
                            arrayListOf(cubeTexes[it.aT], cubeTexes[it.bT], cubeTexes[it.cT]),
                            0
                    )
                }
                .toList()

//        cubeFaces.parallelStream()
//                .map {
//                    Polygon(
//                            arrayListOf(cubeVectors[it.aV], cubeVectors[it.bV], cubeVectors[it.cV]),
//                            arrayListOf(cubeStartVectors[it.aV], cubeStartVectors[it.bV], cubeStartVectors[it.cV]),
//                            arrayListOf(cubeTexes[it.aT], cubeTexes[it.bT], cubeTexes[it.cT]),
//                            0
//                    )
//                }
//                .peek { it.vectors = it.vectors.stream().map { v -> v x viewMatrix.provide() }.toList() }
//                .peek { it.vectors = it.vectors.stream().map { v -> v x projectionMatrix.provide() }.toList() }
//                .peek { it.vectors = it.vectors.stream().map { v -> v x viewport.provide() }.toList() }
//                .peek {
//                    it.vectors = it.vectors.stream().map { v ->
//                        Vector4D(
//                                v.x.coerceIn(0.0, width),
//                                v.y.coerceIn(0.0, height),
//                                v.z,
//                                v.w
//                        )
//                    }.toList()
//                }
//                .filter { validator.validateVisibility(it) }
//                .forEach {
//                    shapesAlgos.triangle(it, light, transformation, cubePolygons)
//                }

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
                        shapesAlgos.triangle(it, light, transformation, cubePolygons)
                    }
        }


    }

}
