package provider

import linear.Matrix4x4

fun interface MatrixProvider {
    fun provide(matrix: Matrix4x4): Matrix4x4
}
