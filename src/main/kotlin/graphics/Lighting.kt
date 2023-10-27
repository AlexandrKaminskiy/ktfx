package graphics

import linear.Vector3D
import linear.Vector4D
import kotlin.math.acos

class Lighting {

    fun lambertCalculation(p: Polygon, eye: Vector3D): Int {


        val v1 = Vector3D(p.vectors[0].x, p.vectors[0].y, p.vectors[0].z)
        val v2 = Vector3D(p.vectors[1].x, p.vectors[1].y, p.vectors[1].z)
        var norm: Vector3D;
        if (!isClockwise(p.vectors)) {
            norm = (v1 x v2)
        } else {
            norm = (v2 x v1)
        }

        val view = (v1 - eye)
        var theta = Math.toDegrees(acos(norm * view / (view.norm() * norm.norm())))

        theta = if (theta > 90) 90.0 else theta

//        theta = 90 - theta
//        println(theta)
        return theta.toInt()
    }

    private fun isClockwise(vertices: List<Vector4D>): Boolean {
        var sum = 0.0
        for (i in vertices.indices) {
            val current: Vector4D = vertices[i]
            val next: Vector4D = vertices[(i + 1) % vertices.size]
            sum += (next.x - current.x) * (next.y + current.y)
        }
        return sum < 0
    }
}
