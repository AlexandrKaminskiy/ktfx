package validator

import graphics.Polygon

interface PolygonValidator {
    fun validateSizeConstraints(polygon: Polygon, width: Double, height: Double ) : Boolean
    fun validateVisibility(polygon: Polygon) : Boolean
}
