package graphics

import linear.Vector3D

class ZBuffer {
    fun getZBufferValue(polygon: Polygon, eye: Vector3D): Int {
        val v = eye - polygon.vectors[0]

        return (v.x * v.x + v.y * v.y + v.z * v.z).toInt()
    }
}
