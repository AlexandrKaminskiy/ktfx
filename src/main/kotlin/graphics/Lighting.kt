package graphics

import linear.Vector3D
import kotlin.math.acos

class Lighting {

    fun lambertCalculation(p: Polygon, eye: Vector3D): Int {


        val v1 = Vector3D(p.vectors[0].x, p.vectors[0].y, p.vectors[0].z)
        val v2 = Vector3D(p.vectors[1].x, p.vectors[1].y, p.vectors[1].z)

        val norm = (v1 x v2)
        val view = (v1 - eye)
        var theta = Math.toDegrees(acos(norm * view / (view.norm() * norm.norm())))

        theta = if (theta > 90) 90.0 else theta

//        theta = 90 - theta
//        println(theta)
        return theta.toInt()
    }


}
