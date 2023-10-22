package provider.impl

import linear.Matrix4x4
import linear.Vector3D
import provider.MatrixProvider

class ViewMatrixProviderImpl() : MatrixProvider {

    override fun provide(): Matrix4x4 {
        val target = Vector3D(0.0, 0.0, 0.0)
        val eye = Vector3D(0.0, 0.0, 10.0)
        val up = Vector3D(0.0, 10.0, 10.0)

        val zAxis: Vector3D = (eye - target).normalize()
        val xAxis: Vector3D = (up x zAxis).normalize()
        val yAxis: Vector3D = zAxis x xAxis

        return Matrix4x4(
                arrayOf(
                        doubleArrayOf(xAxis.x, yAxis.x, zAxis.x, 0.0),
                        doubleArrayOf(xAxis.y, yAxis.y, zAxis.y, 0.0),
                        doubleArrayOf(xAxis.z, yAxis.z, zAxis.z, 0.0),
                        doubleArrayOf(-(xAxis * eye), -(yAxis * eye), -(zAxis * eye), 1.0),
                )
        )
    }

}
