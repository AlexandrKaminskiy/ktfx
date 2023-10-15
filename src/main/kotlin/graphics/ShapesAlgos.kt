package graphics

import linear.Vector4D
import java.util.*
import kotlin.math.abs

class ShapesAlgos {

    fun process(v1: Vector4D, v2: Vector4D): List<Point> {
        val l = if (abs(v1.x - v2.x) > abs(v1.y - v2.y)) abs(v1.x - v2.x).toInt() else abs(v1.y - v2.y).toInt()
        val points = mutableListOf<Point>();

        val deltaX: Double = (v2.x - v1.x) / l
        val deltaY: Double = (v2.y - v1.y) / l

        var currX = v1.x;
        var currY = v1.y;

        for (i in 1..l) {
            points.add(Point(currX.toInt(), currY.toInt()));
            currX += deltaX
            currY += deltaY
        }

        return points;
    }

    fun triangle(vectors: List<Vector4D>): List<Point> {
        val points = arrayListOf<Point>();
        Collections.sort(vectors, Comparator.comparingDouble { v1 -> v1.y })
        val x1 = vectors[0].x
        val y1 = vectors[0].y
        val x2 = vectors[1].x
        val y2 = vectors[1].y
        val x3 = vectors[2].x
        val y3 = vectors[2].y

        for (i in vectors[0].y.toInt() until vectors[1].y.toInt()) {

            var targetX1 = interpolate(i.toDouble(), x1, y1, x3, y3).toInt()
            var targetX2 = interpolate(i.toDouble(), x1, y1, x2, y2).toInt()

            if (targetX1 > targetX2) {
                targetX1 = targetX2.also { targetX2 = targetX1 }
            }

            for (j in targetX1..targetX2) {
                points.add(Point(j, i));
            }
        }

        for (i in vectors[1].y.toInt() until vectors[2].y.toInt()) {

            var targetX1 = interpolate(i.toDouble(), x1, y1, x3, y3).toInt()
            var targetX2 = interpolate(i.toDouble(), x2, y2, x3, y3).toInt()

            if (targetX1 > targetX2) {
                targetX1 = targetX2.also { targetX2 = targetX1 }
            }

            for (j in targetX1..targetX2) {
                points.add(Point(j, i));
            }
        }
        return points

    }

    fun zeroCheck(v1: Double, v2: Double) = abs(v1 - v2) < 0.00000001

    /**
     * Retrieving of X value by Y value
     */
    fun interpolate(targetY: Double, x1: Double, y1: Double, x2: Double, y2: Double) =
        if (zeroCheck(y1, y2)) x1 else (x1 - x2) / (y1 - y2) * (targetY - y1) + x1


}
