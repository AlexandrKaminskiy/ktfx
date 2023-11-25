package linear

import kotlin.math.sqrt

data class Vector3D(val x: Double, val y: Double, val z: Double) {

    operator fun minus(v: Vector3D) = Vector3D(x - v.x, y - v.y, z - v.z)
    operator fun plus(v: Vector3D) = Vector3D(x + v.x, y + v.y, z + v.z)
    operator fun times(v: Vector3D) = x * v.x + y * v.y + z * v.z
    operator fun times(n: Double) = Vector3D(x * n, y * n, z * n)
    infix fun x(v: Vector3D): Vector3D {

        val i = y * v.z - z * v.y
        val j = -(x * v.z - z * v.x)
        val k = x * v.y - y * v.x

        return Vector3D(i, j, k)
    }

    fun norm() = sqrt(x * x + y * y + z * z)
    fun normalize() = this * (1 / norm())
    fun perpDotProduct(v: Vector3D): Double = this.x * v.y - this.y * v.x

    constructor(v4: Vector4D) : this(v4.x, v4.y, v4.z)
}
