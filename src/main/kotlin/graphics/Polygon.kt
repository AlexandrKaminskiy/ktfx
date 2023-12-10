package graphics

import linear.Vector2D
import linear.Vector3D
import linear.Vector4D
import kotlin.math.absoluteValue

data class Polygon(
        var vectors: List<Vector4D>,
        var startVectors: List<Vector4D>,
        var texes: List<Vector2D>,
        var color: Int
) {

    fun intersection(v0: Vector3D, v1: Vector3D): Vector3D {
        val p0 = Vector3D(vectors[0])
        val p1 = Vector3D(vectors[1])
        val p2 = Vector3D(vectors[2])

        val e1 = p1 - p0
        val e2 = p2 - p0
        val n = (e1 x e2)

        val d = -(n.x * p0.x + n.y * p0.y + n.z * p0.z)

        val t = (n.x * v0.x + n.y * v0.y + n.z * v0.z + d) / (n.x * (v0.x - v1.x) + n.y * (v0.y - v1.y) + n.z * (v0.z - v1.z))
//        println("t = $t")

        if (t < 0) return Vector3D.NULL_VECTOR

//        println("t = $t")
        val point = v0 + (v1 - v0) * t

        if (point.x.absoluteValue > 100.5 || point.y.absoluteValue > 100.5 || point.z.absoluteValue > 100.5) return Vector3D.NULL_VECTOR
        val area = n.norm()
        val s0 = p0 - point
        val s1 = p1 - point
        val s2 = p2 - point

        val u = (s1 x s2).norm() / area
        val v = (s2 x s0).norm() / area
        val w = (s0 x s1).norm() / area
//        println(point)
//        if (u + v + w > 1) return Vector3D.NULL_VECTOR

//        println("u = ${u * 100} ")
//        println("v = ${v * 100}")
//        println("w = ${w * 100}")
//        println(area)

//        println(point)
        return point
    }

    fun getTextureCoordinate(v0: Vector3D, v1: Vector3D): Vector2D {
        var point = intersection(v0, v1)

        if (point == Vector3D.NULL_VECTOR) return Vector2D.NULL_VECTOR

        val x = point.x * texes[0].x + point.x * texes[1].x + point.x * texes[1].x
        val y = point.y * texes[0].y + point.y * texes[1].y + point.y * texes[1].y
        return Vector2D(x, y)
    }

}

fun main() {
    var v0 = Vector3D(1.0, 1.0, 0.0)
    var v1 = Vector3D(1.0, 1.0, 1.01)


    v0 = Vector3D(x=2.1405963327610933, y=2.721046657777911, z=2.8833578580000356)
    v1 = Vector3D(x=1.2080479941685776, y=2.774118514445778, z=2.526234666064586)
    mutableListOf(Polygon(mutableListOf(
            Vector4D(x = 100.0, y = -100.0, z = 100.0, w = 1.0),
            Vector4D(x = 100.0, y = 100.0, z = 100.0, w = 1.0),
            Vector4D(x = -100.0, y = 100.0, z = 100.0, w = 1.0)),
            mutableListOf(),
            mutableListOf(), 0),
    Polygon(mutableListOf(
            Vector4D(x = 100.0, y = -100.0, z = 100.0, w = 1.0),
            Vector4D(x = -100.0, y = 100.0, z = 100.0, w = 1.0),
            Vector4D(x = -100.0, y = -100.0, z = 100.0, w = 1.0)),
            mutableListOf(),
            mutableListOf(), 0),
    Polygon(mutableListOf(
            Vector4D(x = 100.0, y = 100.0, z = -100.0, w = 1.0),
            Vector4D(x = -100.0, y = 100.0, z = -100.0, w = 1.0),
            Vector4D(x = -100.0, y = 100.0, z = 100.0, w = 1.0)),
            mutableListOf(),
            mutableListOf(), 0),
    Polygon(mutableListOf(
            Vector4D(x = -100.0, y = -100.0, z = 100.0, w = 1.0),
            Vector4D(x = -100.0, y = 100.0, z = 100.0, w = 1.0),
            Vector4D(x = -100.0, y = 100.0, z = -100.0, w = 1.0)),
            mutableListOf(),
            mutableListOf(), 0),
    Polygon(mutableListOf(
            Vector4D(x = 100.0, y = 100.0, z = -100.0, w = 1.0),
            Vector4D(x = -100.0, y = 100.0, z = 100.0, w = 1.0),
            Vector4D(x = 100.0, y = 100.0, z = 100.0, w = 1.0)),
            mutableListOf(),
            mutableListOf(), 0),
    Polygon(mutableListOf(
            Vector4D(x = 100.0, y = 100.0, z = -100.0, w = 1.0),
            Vector4D(x = -100.0, y = 100.0, z = -100.0, w = 1.0),
            Vector4D(x = -100.0, y = 100.0, z = 100.0, w = 1.0)),
            mutableListOf(),
            mutableListOf(), 0),
    Polygon(mutableListOf(
            Vector4D(x = 100.0, y = 100.0, z = -100.0, w = 1.0),
            Vector4D(x = -100.0, y = 100.0, z = 100.0, w = 1.0),
            Vector4D(x = 100.0, y = 100.0, z = 100.0, w = 1.0)),
            mutableListOf(),
            mutableListOf(), 0),
    Polygon(mutableListOf(
            Vector4D(x = 100.0, y = -100.0, z = 100.0, w = 1.0),
            Vector4D(x = 100.0, y = 100.0, z = 100.0, w = 1.0),
            Vector4D(x = -100.0, y = 100.0, z = 100.0, w = 1.0)),
            mutableListOf(),
            mutableListOf(), 0),
    Polygon(mutableListOf(
            Vector4D(x = 100.0, y = -100.0, z = 100.0, w = 1.0),
            Vector4D(x = -100.0, y = 100.0, z = 100.0, w = 1.0),
            Vector4D(x = -100.0, y = -100.0, z = 100.0, w = 1.0)),
            mutableListOf(),
            mutableListOf(), 0),
    Polygon(mutableListOf(
            Vector4D(x = 100.0, y = -100.0, z = 100.0, w = 1.0),
            Vector4D(x = 100.0, y = 100.0, z = 100.0, w = 1.0),
            Vector4D(x = -100.0, y = 100.0, z = 100.0, w = 1.0)),
            mutableListOf(),
            mutableListOf(), 0),
    Polygon(mutableListOf(
            Vector4D(x = 100.0, y = -100.0, z = 100.0, w = 1.0),
            Vector4D(x = -100.0, y = 100.0, z = 100.0, w = 1.0),
            Vector4D(x = -100.0, y = -100.0, z = 100.0, w = 1.0)),
            mutableListOf(),
            mutableListOf(), 0),
    Polygon(mutableListOf(
            Vector4D(x = -100.0, y = -100.0, z = 100.0, w = 1.0),
            Vector4D(x = -100.0, y = 100.0, z = 100.0, w = 1.0),
            Vector4D(x = -100.0, y = 100.0, z = -100.0, w = 1.0)),
            mutableListOf(),
            mutableListOf(), 0),
    Polygon(mutableListOf(
            Vector4D(x = -100.0, y = -100.0, z = 100.0, w = 1.0),
            Vector4D(x = -100.0, y = 100.0, z = -100.0, w = 1.0),
            Vector4D(x = -100.0, y = -100.0, z = -100.0, w = 1.0)),
            mutableListOf(),
            mutableListOf(), 0),
    Polygon(mutableListOf(
            Vector4D(x = 100.0, y = -100.0, z = 100.0, w = 1.0),
            Vector4D(x = 100.0, y = 100.0, z = 100.0, w = 1.0),
            Vector4D(x = -100.0, y = 100.0, z = 100.0, w = 1.0)),
            mutableListOf(),
            mutableListOf(), 0),
    Polygon(mutableListOf(
            Vector4D(x = 100.0, y = -100.0, z = 100.0, w = 1.0),
            Vector4D(x = -100.0, y = 100.0, z = 100.0, w = 1.0),
            Vector4D(x = -100.0, y = -100.0, z = 100.0, w = 1.0)),
            mutableListOf(),
            mutableListOf(), 0),
    Polygon(mutableListOf(
            Vector4D(x = 100.0, y = -100.0, z = 100.0, w = 1.0),
            Vector4D(x = -100.0, y = 100.0, z = 100.0, w = 1.0),
            Vector4D(x = -100.0, y = -100.0, z = 100.0, w = 1.0)),
            mutableListOf(),
            mutableListOf(), 0),
    Polygon(mutableListOf(
            Vector4D(x = -100.0, y = -100.0, z = 100.0, w = 1.0),
            Vector4D(x = -100.0, y = 100.0, z = 100.0, w = 1.0),
            Vector4D(x = -100.0, y = 100.0, z = -100.0, w = 1.0)),
            mutableListOf(),
            mutableListOf(), 0),
    Polygon(mutableListOf(
            Vector4D(x = -100.0, y = -100.0, z = 100.0, w = 1.0),
            Vector4D(x = -100.0, y = 100.0, z = -100.0, w = 1.0),
            Vector4D(x = -100.0, y = -100.0, z = -100.0, w = 1.0)),
            mutableListOf(),
            mutableListOf(), 0),
    Polygon(mutableListOf(
            Vector4D(x = 100.0, y = -100.0, z = 100.0, w = 1.0),
            Vector4D(x = 100.0, y = 100.0, z = 100.0, w = 1.0),
            Vector4D(x = -100.0, y = 100.0, z = 100.0, w = 1.0)),
            mutableListOf(),
            mutableListOf(), 0),
    Polygon(mutableListOf(
            Vector4D(x = 100.0, y = -100.0, z = 100.0, w = 1.0),
            Vector4D(x = -100.0, y = 100.0, z = 100.0, w = 1.0),
            Vector4D(x = -100.0, y = -100.0, z = 100.0, w = 1.0)),
            mutableListOf(),
            mutableListOf(), 0),
    Polygon(mutableListOf(
            Vector4D(x = -100.0, y = -100.0, z = -100.0, w = 1.0),
            Vector4D(x = 100.0, y = 100.0, z = -100.0, w = 1.0),
            Vector4D(x = 100.0, y = -100.0, z = -100.0, w = 1.0)),
            mutableListOf(),
            mutableListOf(), 0)
    ).stream().forEach { println(it.intersection(v0, v1))}




    val p = Polygon(mutableListOf(
            Vector4D(100.0, -100.0, 100.0, 0.0),
            Vector4D(100.0, 100.0, 100.0, 0.0),
            Vector4D(-100.0, 100.0, 100.0, 0.0)
    ),
            mutableListOf(),
            mutableListOf(),
            2)

//    println(p.hasIntersection(v0, v1))
    println(p.intersection(v0, v1))
}
