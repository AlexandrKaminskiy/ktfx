package graphics

import linear.Vector3D
import linear.Vector4D
import java.util.Collections
import java.util.Comparator

class ZBuffer {
    fun getZBufferValue(point: Point, eye: Vector3D): Double {

        val vertex = Vector3D(point.x.toDouble(), point.y.toDouble(), point.z)
        val v = eye - vertex

        return point.z
    }

    fun getZBufferValue(polygon: Polygon, eye: Vector3D): Double {

        val v1 = eye - Vector3D(polygon.vectors[0].x,polygon.vectors[0].y,polygon.vectors[0].z)
        val v2 = eye - Vector3D(polygon.vectors[0].x,polygon.vectors[0].y,polygon.vectors[0].z)
        val v3 = eye - Vector3D(polygon.vectors[0].x,polygon.vectors[0].y,polygon.vectors[0].z)

        val n1 = v1.norm()
        val n2 = v2.norm()
        val n3 = v3.norm()

        return (n1 + n2 + n3) / 3.0
    }


    fun getSquare(polygon: Polygon): Double {
        var sum = 0.0
        for (i in polygon.vectors.indices) {
            val current: Vector4D = polygon.vectors[i]
            val next: Vector4D = polygon.vectors[(i + 1) % polygon.vectors.size]
            sum += (current.x * next.y - next.x * current.y)
        }

        return sum / 2.0
    }

    fun getCenter(polygon: Polygon): Vector3D {
        val vertices = ArrayList(polygon.vectors)
        Collections.sort(vertices, Comparator.comparingDouble { o1 -> o1.x })
        val x = (vertices[2].x + vertices[0].x) / 2
        Collections.sort(vertices, Comparator.comparingDouble { o1 -> o1.y })
        val y = (vertices[2].y + vertices[0].y) / 2
        Collections.sort(vertices, Comparator.comparingDouble { o1 -> o1.z })
        val z = (vertices[2].z + vertices[0].z) / 2

        return Vector3D(x, y, z)
    }

}

//fun main() {
//    println(ZBuffer().getCenter(Polygon(arrayListOf<Vector4D>(
//            Vector4D(0.0, 0.0, -10.0, 0.0),
//            Vector4D(1.0, 0.0, 0.0, 0.0),
//            Vector4D(0.0, -1.0, 15.0, 0.0),
//    ), 0, 0.0)))
//}
