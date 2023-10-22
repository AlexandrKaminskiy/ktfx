package provider.impl

import linear.Matrix4x4
import provider.MatrixProvider

class MatrixProviderImpl: MatrixProvider {
    override fun provide(matrix: Matrix4x4): Matrix4x4 {

        return matrix;
    }
}
