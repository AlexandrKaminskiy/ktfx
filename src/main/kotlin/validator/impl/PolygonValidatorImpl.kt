package validator.impl

import graphics.Polygon
import linear.Vector3D
import validator.PolygonValidator

class PolygonValidatorImpl : PolygonValidator {

    override fun validateSizeConstraints(polygon: Polygon, width: Double, height: Double): Boolean {
        val v1 = polygon.vectors[0]
        val v2 = polygon.vectors[1]
        val v3 = polygon.vectors[2]

        return v1.x > 0 && v1.y > 0 && v1.x < width && v1.y < height &&
                v2.x > 0 && v2.y > 0 && v2.x < width && v2.y < height &&
                v3.x > 0 && v3.y > 0 && v3.x < width && v3.y < height
    }

    override fun validateVisibility(polygon: Polygon, vector3D: Vector3D) : Boolean {
        //против

        val norm = polygon.vectors[2].normalize() x polygon.vectors[1].normalize()

        return norm.normalize() * vector3D < 0
    }

}