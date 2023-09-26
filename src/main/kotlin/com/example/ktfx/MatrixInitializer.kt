import linear.Matrix4x4
import provider.impl.MatrixProviderImpl
import provider.impl.ProjectionMatrixProviderImpl
import provider.impl.ViewMatrixProviderImpl
import provider.impl.ViewportMatrixProviderImpl

class MatrixInitializer {

    val finalMatrix: Matrix4x4

    constructor(width: Double, height: Double, xMin: Double, yMin: Double, angle: Int) {
        val provider = ViewMatrixProviderImpl(
            ProjectionMatrixProviderImpl(
                ViewportMatrixProviderImpl(
                    MatrixProviderImpl(),
                    width, height, xMin, yMin
                ),
                width, height, angle
            )
        )
        finalMatrix = provider.provide(
            Matrix4x4(
                arrayOf(
                    doubleArrayOf(1.0, 0.0, 0.0, 0.0),
                    doubleArrayOf(0.0, 1.0, 0.0, 0.0),
                    doubleArrayOf(0.0, 0.0, 1.0, 0.0),
                    doubleArrayOf(0.0, 0.0, 0.0, 1.0),
                )
            )
        )
    }
}
