package provider.impl

import linear.Matrix4x4
import provider.MatrixProvider

class ViewportMatrixProviderImpl(
    val provider: MatrixProvider,
    val width: Double,
    val height: Double,
    val xMin: Double,
    val yMin: Double
) : MatrixProvider {
    override fun provide(matrix: Matrix4x4): Matrix4x4 {
        val viewport = Matrix4x4(
            arrayOf(
                doubleArrayOf(width / 2, 0.0, 0.0, 0.0),
                doubleArrayOf(0.0, -height / 2, 0.0, 0.0),
                doubleArrayOf(0.0, 0.0, 1.0, 0.0),
                doubleArrayOf(xMin + width / 2, yMin + height / 2, 0.0, 1.0),
            )
        )

        return provider.provide(matrix x viewport)
    }
}
