package graphics

import linear.Vector4D

data class Polygon(var vectors: List<Vector4D>, var color: Int, var depth: Double = 0.0);
