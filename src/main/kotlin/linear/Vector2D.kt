package linear

data class Vector2D(val x: Double, val y: Double) {
    operator fun times(n: Double) = Vector2D(x * n, y * n)

    companion object {
        val NULL_VECTOR = Vector2D(Double.NaN, Double.NaN)
    }
}
