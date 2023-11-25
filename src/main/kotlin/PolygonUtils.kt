import graphics.PointMatch
import graphics.Polygon
import linear.Vector3D
import linear.Vector4D
import linear.Vertex

object PolygonUtils {

    @JvmStatic
    fun toPolygons(faces: List<PointMatch>, vectors4D: List<Vector4D>): List<Polygon> {

        val norms: List<Vector3D> = faces.map {
            val a = Vector3D(vectors4D[it.a])
            val b = Vector3D(vectors4D[it.b])
            val c = Vector3D(vectors4D[it.c])
            val ab = b - a
            val ac = c - a

            (ac x ab).normalize()
        }.toList()

        val vertexPolygonMap = hashMapOf<Int, ArrayList<Int>>()
        for (i in vectors4D.indices) {
            for (j in faces.indices) {
                if (faces[j].a == i || faces[j].b == i || faces[j].c == i) {
                    if (vertexPolygonMap[i] == null) {
                        vertexPolygonMap[i] = arrayListOf(i)
                    } else {
                        vertexPolygonMap[i]?.add(j)
                    }
                }
            }
        }

        vertexPolygonMap.map {
            var v = Vector3D(0.0, 0.0, 0.0)

            for (i in it.value) {
                v += norms[i]
            }
            Vertex(vectors4D[it.key], v.normalize())
        }.toList()
        return arrayListOf()
    }

}
