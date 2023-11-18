package graphics

import linear.Vector3D
import kotlin.math.max

class Lighting {

    val kAmbient: Double = 2.0
    val iAmbient: Double = 0.2
    val kDiffuse: Double = 2.0
    val iDiffuse: Double = 0.2
    val kSpecular: Double = 1.0
    val iSpecular: Double = 2.0
    val fi: Double = 1.0


    fun calculateLight(light: Vector3D, norm: Vector3D, eye: Vector3D, shape: Vector3D): Double {
        val ambient = kAmbient * iAmbient
        val diffuse = kDiffuse * iDiffuse * fongCalculation(light - shape, norm)
        val specular = kSpecular * iSpecular * Math.pow(theta(light, norm, eye), fi)

        return ambient + diffuse + specular
    }
    fun fongCalculation(light: Vector3D, norm: Vector3D): Double {


        val eyeNorm = light.normalize()
        val normNorm = norm.normalize()
        val angleCos = max(normNorm * eyeNorm, 0.0)


        return max(angleCos, 0.0)
    }

    fun theta(l: Vector3D, n: Vector3D, eye: Vector3D): Double {

        val r = l - n.normalize() * (l * n.normalize()) * 2.0
        val angleCos = eye.normalize() * r
        return angleCos
    }
}
