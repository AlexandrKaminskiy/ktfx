import graphics.PointMatch
import linear.Vector2D
import linear.Vector4D

class CubeMapProcessing {

    private var cubeVectors: MutableList<Vector4D>
    private var cubeTexes: MutableList<Vector2D>
//    private val cubeFaces: MutableList<PointMatch>


    constructor() {
        cubeVectors = mutableListOf(
                Vector4D(1.000000, 1.000000, -1.000000, 0.0),
                Vector4D(1.000000, -1.000000, -1.000000, 0.0),
                Vector4D(1.000000, 1.000000, 1.000000, 0.0),
                Vector4D(1.000000, -1.000000, 1.000000, 0.0),
                Vector4D(-1.000000, 1.000000, -1.000000, 0.0),
                Vector4D(-1.000000, -1.000000, -1.000000, 0.0),
                Vector4D(-1.000000, 1.000000, 1.000000, 0.0),
                Vector4D(-1.000000, -1.000000, 1.000000, 0.0)
        )
        cubeTexes = mutableListOf(
                Vector2D(1.0, 0.666666),
                Vector2D(1.0, 0.333333),
                Vector2D(0.5, 0.666666),
                Vector2D(0.5, 0.333333),
                Vector2D(0.25, 0.666666),
                Vector2D(0.25, 0.333333),
                Vector2D(0.25, 0.666666),
//                Vector2D(0.25, 0.333333),
        )
    }
}
