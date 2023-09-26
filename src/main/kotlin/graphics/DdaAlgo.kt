package graphics

import linear.Vector4D
import kotlin.math.abs

class DdaAlgo {

    fun process(v1: Vector4D, v2: Vector4D): List<Point> {
        val l = if (abs(v1.x - v2.x) > abs(v1.y - v2.y)) abs(v1.x - v2.x).toInt() else abs(v1.y - v2.y).toInt()
        val points = mutableListOf<Point>();

        val deltaX: Double = (v2.x - v1.x) / l
        val deltaY: Double = (v2.y - v1.y) / l

        var currX = v1.x;
        var currY = v1.y;

        for (i in 1 .. l) {
            points.add(Point(currX.toInt(), currY.toInt()));
            currX += deltaX
            currY += deltaY
        }

        return points;
    }

}
