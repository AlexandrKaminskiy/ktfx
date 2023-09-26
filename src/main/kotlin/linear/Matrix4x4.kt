package linear

class Matrix4x4(val data: Array<DoubleArray>) {

    val column: Int = data.size
    val row: Int = data[0].size

    companion object {

         val DIAGONAL = Matrix4x4(
            arrayOf(
                doubleArrayOf(1.0, 0.0, 0.0, 0.0),
                doubleArrayOf(0.0, 1.0, 0.0, 0.0),
                doubleArrayOf(0.0, 0.0, 1.0, 0.0),
                doubleArrayOf(0.0, 0.0, 0.0, 1.0),
            )
        )
    }


    infix fun x(m: Matrix4x4): Matrix4x4 {
        val result: Array<DoubleArray> = Array(row) { DoubleArray(m.column) }

        for (i in 0 until row) {
            for (j in 0 until m.column) {
                for (k in 0 until column) {
                    result[i][j] += this.data[i][k] * m.data[k][j]
                }
            }
        }

        return Matrix4x4(result);
    }

    infix fun x(v: Vector4D): Vector4D {

        val x = data[0][0] * v.x + data[0][1] * v.y + data[0][2] * v.z + data[0][3] * v.w
        val y = data[1][0] * v.x + data[1][1] * v.y + data[1][2] * v.z + data[1][3] * v.w
        val z = data[2][0] * v.x + data[2][1] * v.y + data[2][2] * v.z + data[2][3] * v.w
        val w = data[3][0] * v.x + data[3][1] * v.y + data[3][2] * v.z + data[3][3] * v.w

        return Vector4D(x, y, z, w)
    }

    fun transposeMatrix(): Matrix4x4 {

        val transposedMatrix = Array(column) { DoubleArray(row) }

        for (i in 0 until row) {
            for (j in 0 until column) {
                transposedMatrix[j][i] = data[i][j]
            }
        }

        return Matrix4x4(transposedMatrix)
    }
}
