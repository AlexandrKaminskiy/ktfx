import linear.Matrix4x4
import provider.impl.ProjectionMatrixProviderImpl
import provider.impl.ViewMatrixProviderImpl
import provider.impl.ViewportMatrixProviderImpl

class MatrixInitializer {

    val projectionMatrix: Matrix4x4
    val viewPortMatrix: Matrix4x4
    val viewMatrix: Matrix4x4

    constructor(width: Double, height: Double, xMin: Double, yMin: Double, angle: Int) {
        projectionMatrix = ProjectionMatrixProviderImpl(width, height, angle).provide()
        viewPortMatrix = ViewportMatrixProviderImpl(width, height, xMin, yMin).provide()
        viewMatrix = ViewMatrixProviderImpl().provide()
    }
}
