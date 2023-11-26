package graphics

import linear.Vector2D
import linear.Vector4D

data class Polygon(
        var vectors: List<Vector4D>,
        var startVectors: List<Vector4D>,
        var texes: List<Vector2D>,
        var color: Int
)
