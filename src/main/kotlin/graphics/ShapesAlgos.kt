package graphics

import java.util.*
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor

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
        val x1 = polygonVectors[0].x
        val y1 = polygonVectors[0].y
        val z1 = polygonVectors[0].z
        val x2 = polygonVectors[1].x
        val y2 = polygonVectors[1].y
        val z2 = polygonVectors[1].z
        val x3 = polygonVectors[2].x
        val y3 = polygonVectors[2].y
        val z3 = polygonVectors[2].z

        var currentY = y1

        while (currentY < y2) {

            var targetX1 = interpolate(currentY, x1, y1, x3, y3)
            var targetX2 = interpolate(currentY, x1, y1, x2, y2)

            val targetZ1 = interpolate(currentY, z1, y1, z3, y3)
            val targetZ2 = interpolate(currentY, z1, y1, z2, y2)

            if (targetX1 > targetX2) {
                targetX1 = targetX2.also { targetX2 = targetX1 }
            }

            var currentX = targetX1

            while (currentX < targetX2) {
                val zVal = interpolate(currentX, targetZ1, targetX1, targetZ2, targetX2)
                points.add(Point(floor(currentX).toInt(), ceil(currentY).toInt(), polygon.depth, polygon.color))
                currentX += 1.0
            }

//            val zVal = interpolate(currentX, targetZ1, targetX1, targetZ2, targetX2)
//            points.add(Point(floor(currentX).toInt(), ceil(currentY).toInt(), zVal, polygon.color))

            currentY += 1.0
        }

        currentY = y2

        while (currentY < y3) {

            var targetX1 = interpolate(currentY, x1, y1, x3, y3)
            var targetX2 = interpolate(currentY, x2, y2, x3, y3)

            val targetZ1 = interpolate(currentY, z1, y1, z3, y3)
            val targetZ2 = interpolate(currentY, z2, y2, z3, y3)

            if (targetX1 > targetX2) {
                targetX1 = targetX2.also { targetX2 = targetX1 }
            }

            var currentX = targetX1

            while (currentX < targetX2) {

                val zVal = interpolate(currentX, targetZ1, targetX1, targetZ2, targetX2)
                points.add(Point(floor(currentX).toInt(), ceil(currentY).toInt(), polygon.depth, polygon.color))
                currentX += 1.0
            }

//            val zVal = interpolate(currentX, targetZ1, targetX1, targetZ2, targetX2)
//            points.add(Point(floor(currentX).toInt(), ceil(currentY).toInt(), zVal, polygon.color))

            currentY += 1.0
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
