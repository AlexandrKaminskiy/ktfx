package graphics

import linear.Vector3D
import kotlin.math.acos

class Lighting {

    fun lambertCalculation(p: Polygon, eye: Vector3D): Int {
        val v1 = p.vectors[0]
        val v2 = p.vectors[1]

        val norm = (v1 x v2).normalize()
        val view = (v1 - eye).normalize()
        val theta = acos(norm * view / (view.norm() * norm.norm()))

        println(theta)
        return (theta * 100.0).toInt()
    }
}
