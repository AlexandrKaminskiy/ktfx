package graphics

import ZBuffer
import linear.Vector3D
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor

class ShapesAlgos(val zBuffer: ZBuffer) {


    fun triangle(polygon: Polygon) {

        val points = ArrayList(polygon.vectors)
        Collections.sort(points, Comparator.comparingDouble { v1 -> v1.y })
        val yMin = points.first().y.toInt()
        val yMax = points.last().y.toInt()
        Collections.sort(points, Comparator.comparingDouble { v1 -> v1.x })
        val xMin = points.first().x.toInt()
        val xMax = points.last().x.toInt()

        val a = Vector3D(polygon.vectors[0].x - xMin, polygon.vectors[0].y - yMin, polygon.vectors[0].z)
        val b = Vector3D(polygon.vectors[1].x - xMin, polygon.vectors[1].y - yMin, polygon.vectors[1].z)
        val c = Vector3D(polygon.vectors[2].x - xMin, polygon.vectors[2].y - yMin, polygon.vectors[2].z)

        val ab = b - a
        val ac = c - a

        val sABC = ac.perpDotProduct(ab)
        val startVertex = Vector3D(0.0, 0.0, 0.0)

        val pa = a - startVertex
        val pb = b - startVertex
        val pc = c - startVertex

        val dvdx = (pa.y - pc.y) / sABC
        val dwdx = (pb.y - pa.y) / sABC
        val dudx = (pc.y - pb.y) / sABC
        val dudy = (pb.x - pc.x) / sABC
        val dvdy = (pc.x - pa.x) / sABC
        val dwdy = (pa.x - pb.x) / sABC

        val u00 = (pc.x * pb.y - pc.y * pb.x) / sABC
        val v00 = (pa.x * pc.y - pa.y * pc.x) / sABC
        val w00 = 1 - u00 - v00

        var incX = 0
        var incY = 0

        for (i in yMin..yMax) {
            for (j in xMin..xMax) {
                val u = u00 + dudx * incX + dudy * incY
                val v = v00 + dvdx * incX + dvdy * incY
                val w = w00 + dwdx * incX + dwdy * incY

                if (u >= 0 && v >= 0 && w >= 0) {
                    val z = a.z * u + v * b.z + w * c.z
                    val col = a.z * u + v * b.z + w * c.z
                    zBuffer.setColor(j, i, Point(z, ((180.0 - polygon.color) * 255).toInt() shl 24))
                }
                incX++
            }
            incX = 0
            incY++
        }
    }

    fun zeroCheck(v1: Double, v2: Double) = abs(v1 - v2) < 0.00000001

    /**
     * Retrieving of X value by Y value
     */
    fun interpolate(targetY: Double, x1: Double, y1: Double, x2: Double, y2: Double) =
            if (zeroCheck(y1, y2)) x1 else (x1 - x2) / (y1 - y2) * (targetY - y1) + x1


}

fun main() {
    val a = Vector3D(0.0, 1.0, 0.0)
    val b = Vector3D(1.0, 0.0, 0.0)
    println(a.perpDotProduct(b))
}

