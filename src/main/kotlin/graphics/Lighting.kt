package graphics

import linear.Vector3D
import linear.Vector4D
import kotlin.math.acos
import kotlin.math.max

class Lighting {

//    fun lambertCalculation(v: Vector3D, eye: Vector3D): Int {
//
//
//        val v1 = Vector3D(p.vectors[0].x, p.vectors[0].y, p.vectors[0].z)
//        val v2 = Vector3D(p.vectors[1].x, p.vectors[1].y, p.vectors[1].z)
//        val v3 = Vector3D(p.vectors[2].x, p.vectors[2].y, p.vectors[2].z)
//
//        val v1ForNorm = v2 - v1
//        val v2ForNorm = v3 - v1
//
//        val norm: Vector3D = if (!isClockwise(p.vectors)) {
//            (v2ForNorm.normalize() x v1ForNorm.normalize())
//        } else {
//            (v1ForNorm.normalize() x v2ForNorm.normalize())
//        }
//
//        val thetaV1 = getAngleForVertex(eye, v1, norm)
//        val thetaV2 = getAngleForVertex(eye, v2, norm)
//        val thetaV3 = getAngleForVertex(eye, v3, norm)
//
//        val avgTheta = ((thetaV1 + thetaV2 + thetaV3) / 3.0).toInt()
////        println(avgTheta)
//        return avgTheta
//    }


    fun fongCalculation(eye: Vector3D, norm: Vector3D): Double {
        val eyeNorm = eye.normalize()
        val normNorm = norm.normalize()
        val angleCos = normNorm * eyeNorm
        return max(angleCos, 0.0)
    }


    private fun getAngleForVertex(eye: Vector3D, vertex: Vector3D, norm: Vector3D): Double {
        val view = (eye - vertex).normalize()
        return Math.toDegrees(acos(norm * view / (view.norm() * norm.norm())))
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
