package provider.impl

import linear.Matrix4x4
import linear.Vector3D
import provider.MatrixProvider

class ViewMatrixProviderImpl(val provider: MatrixProvider) : MatrixProvider {

    override fun provide(matrix: Matrix4x4): Matrix4x4 {
        val target = Vector3D(1.5, 9.0, -2.0)
        val eye = Vector3D(1.5, 9.0, 50.0)
        val up = Vector3D(1.5, 20.0, 50.0)

        val ZAxis: Vector3D = (eye - target).normalize()
        val XAxis: Vector3D = (up x ZAxis).normalize()
        val YAxis: Vector3D = ZAxis x XAxis

        val viewMatrix = Matrix4x4(
            arrayOf(
                doubleArrayOf(XAxis.x, YAxis.x, ZAxis.x, 0.0),
                doubleArrayOf(XAxis.y, YAxis.y, ZAxis.y, 0.0),
                doubleArrayOf(XAxis.z, YAxis.z, ZAxis.z, 0.0),
                doubleArrayOf(-(XAxis * eye), -(YAxis * eye), -(ZAxis * eye), 1.0),
            )
        )
        return provider.provide(matrix x viewMatrix)
    }

}
