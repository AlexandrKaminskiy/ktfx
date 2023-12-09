package graphics

import linear.Vector3D
import kotlin.math.max
import kotlin.math.pow

class Lighting {

    val kAmbient: Double = 0.3
    val iColor = Color(60, 0, 10)

    val kDiffuse: Double = 0.7

    val dColor = Color(127, 61, 61)


    val kSpecular: Double = 1.0
    val alpha: Double = 15.0
    val sColor = Color(255, 255, 255)


    fun calculateLight(light: Vector3D, norm: Vector3D, eye: Vector3D, shape: Vector3D, color: Color, spec: Color): Int {
        val ambient = color * kAmbient
        val diffuse = color * kDiffuse * (fongCalculation(shape - light, norm))
        val specular = spec * theta(light, norm, eye).pow(alpha)
        return (ambient + diffuse + specular).build()
                .toIntColor()
    }

    fun fongCalculation(light: Vector3D, norm: Vector3D): Double {


        val eyeNorm = light.normalize()
        val normNorm = norm.normalize()
        val angleCos = max(normNorm * eyeNorm, 0.0)


        return max(angleCos, 0.0)
    }

    fun theta(l: Vector3D, n: Vector3D, eye: Vector3D): Double {

        val r = l - n.normalize() * (l * n.normalize()) * 2.0
        val angleCos = eye.normalize() * r.normalize()
        return max(angleCos, 0.0)
    }
}
