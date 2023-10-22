package graphics

import linear.Vector4D
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs

class ShapesAlgos {

//    fun process(v1: Vector4D, v2: Vector4D): List<Point> {
//        val l = if (abs(v1.x - v2.x) > abs(v1.y - v2.y)) abs(v1.x - v2.x).toInt() else abs(v1.y - v2.y).toInt()
//        val points = mutableListOf<Point>();
//
//        val deltaX: Double = (v2.x - v1.x) / l
//        val deltaY: Double = (v2.y - v1.y) / l
//
//        var currX = v1.x;
//        var currY = v1.y;
//
//        for (i in 1..l) {
//            points.add(Point(currX.toInt(), currY.toInt()));
//            currX += deltaX
//            currY += deltaY
//        }
//
//        return points;
//    }

    fun triangle(polygon: Polygon): List<Point> {
        val points = arrayListOf<Point>()
        val polygonVectors = ArrayList(polygon.vectors)
        Collections.sort(polygonVectors, Comparator.comparingDouble { v1 -> v1.y })
        val x1 = polygonVectors[0].x.toInt()
        val y1 = polygonVectors[0].y.toInt()
        val x2 = polygonVectors[1].x.toInt()
        val y2 = polygonVectors[1].y.toInt()
        val x3 = polygonVectors[2].x.toInt()
        val y3 = polygonVectors[2].y.toInt()

        for (i in y1 until y2) {

            var targetX1 = interpolate(i.toDouble(), x1.toDouble(), y1.toDouble(), x3.toDouble(), y3.toDouble()).toInt()
            var targetX2 = interpolate(i.toDouble(), x1.toDouble(), y1.toDouble(), x2.toDouble(), y2.toDouble()).toInt()

            if (targetX1 > targetX2) {
                targetX1 = targetX2.also { targetX2 = targetX1 }
            }

            for (j in targetX1..targetX2) {
                points.add(Point(j, i, polygon.zBufferValue, polygon.color));
            }
        }

        for (i in y2 until y3) {

            var targetX1 = interpolate(i.toDouble(), x1.toDouble(), y1.toDouble(), x3.toDouble(), y3.toDouble()).toInt()
            var targetX2 = interpolate(i.toDouble(), x2.toDouble(), y2.toDouble(), x3.toDouble(), y3.toDouble()).toInt()

            if (targetX1 > targetX2) {
                targetX1 = targetX2.also { targetX2 = targetX1 }
            }

            for (j in targetX1..targetX2) {
                points.add(Point(j, i, polygon.zBufferValue, polygon.color));
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
