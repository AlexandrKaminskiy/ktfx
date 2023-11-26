package graphics

import kotlin.math.min

data class Color(var r: Int, var g: Int, var b: Int) {

    constructor(rgb: Int) : this(rgb and 0xFF0000 shr 16, rgb and 0x00FF00 shr 8, rgb and 0x00FF)

    operator fun times(m: Double) = Color((r * m).toInt(), (g * m).toInt(), (b * m).toInt())
    operator fun plus(c: Color) = Color(c.r + r, c.g + g, c.b + b)
    fun toIntColor() = (255 shl 24) or (r shl 16) or (g shl 8) or (b)
    fun build() = Color(min(r, 255), min(g, 255), min(b, 255))
}
