package provider.impl

import linear.Matrix4x4
import provider.MatrixProvider
import kotlin.math.tan

class ProjectionMatrixProviderImpl(
    val provider: MatrixProvider,
    val width: Double,
    val height: Double,
    val angle: Int
) : MatrixProvider {
    override fun provide(matrix: Matrix4x4): Matrix4x4 {
        val fov = angle / 180.0 * Math.PI;
        val aspect = width / height
        val zNear = 10.0
        val zFar = 100.0

        val projection = Matrix4x4(
            arrayOf(
                doubleArrayOf(1 / (aspect * tan(fov / 2)), 0.0, 0.0, 0.0),
                doubleArrayOf(0.0, 1 / tan(fov / 2), 0.0, 0.0),
                doubleArrayOf(0.0, 0.0, (zNear - zFar) / zFar, -1.0),
                doubleArrayOf(0.0, 0.0, zNear * zFar / (zNear - zFar), 0.0),
            )
        )

        return provider.provide(matrix x projection)
    }
}