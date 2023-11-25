import graphics.Point

class ZBuffer(private val width: Int, val height: Int) {

    private var frame: Array<Point> = Array(width * height) { Point(Double.MAX_VALUE, 0) }

    fun setColor(x: Int, y: Int, newPoint: Point) {
        val index = y * width + x
        val p = frame[index]
        if (newPoint.z < p.z) {
            frame[index] = newPoint
        }
    }

    fun getColorMap(): IntArray {
        return frame.map { point -> point.c }.toIntArray()
    }

    fun clear() {
        frame = Array(width * height) { Point(Double.MAX_VALUE, 0) }
    }

}

