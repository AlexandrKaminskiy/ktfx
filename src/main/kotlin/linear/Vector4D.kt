package linear

data class Vector4D(val x: Double, val y: Double, val z: Double, val w: Double) {

    operator fun times(n: Double) = Vector4D(x * n, y * n, z * n, w * n)

    infix fun x(m: Matrix4x4): Vector4D {

        val newX = x * m.data[0][0] + y * m.data[1][0] + z * m.data[2][0] + w * m.data[3][0]
        val newY = x * m.data[0][1] + y * m.data[1][1] + z * m.data[2][1] + w * m.data[3][1]
        val newZ = x * m.data[0][2] + y * m.data[1][2] + z * m.data[2][2] + w * m.data[3][2]
        val newW = x * m.data[0][3] + y * m.data[1][3] + z * m.data[2][3] + w * m.data[3][3]

        return Vector4D(newX, newY, newZ, newW)
    }

    fun perpDotProduct(v: Vector4D): Double = this.x * v.y - this.y * v.x

    fun to3D() = Vector3D(x, y, z)
}
