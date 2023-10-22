package provider

import linear.Matrix4x4

fun interface MatrixProvider {
    fun provide(): Matrix4x4
}
