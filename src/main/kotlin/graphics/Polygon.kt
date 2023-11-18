package graphics

import linear.Vector3D
import linear.Vector4D

data class Polygon(var vectors: List<Vector4D>, var normals: List<Vector3D>, var startVectors: List<Vector4D>, var color: Int);
