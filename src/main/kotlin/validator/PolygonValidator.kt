package validator

import graphics.Polygon
import linear.Vector3D

interface PolygonValidator {
    fun validateSizeConstraints(polygon: Polygon, width: Double, height: Double ) : Boolean
    fun validateVisibility(polygon: Polygon, vector3D: Vector3D ) : Boolean
}