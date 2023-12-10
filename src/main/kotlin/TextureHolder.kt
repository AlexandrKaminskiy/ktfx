import graphics.Color
import linear.Vector2D
import linear.Vector3D
import java.awt.image.BufferedImage
import kotlin.math.abs
import kotlin.math.absoluteValue

class TextureHolder {

    var albedoTextures: BufferedImage
    var normalTextures: BufferedImage
    var specularTextures: BufferedImage
    var cubeTextures: Map<String, BufferedImage>
    var width: Int
    var height: Int
    var cubeWidth: Int
    var cubeHeight: Int

    init {
        albedoTextures = ObjInfoExtractor.extractTextures()
        cubeTextures = ObjInfoExtractor.extractCubeMap()
        normalTextures = ObjInfoExtractor.extractNormals()
        specularTextures = ObjInfoExtractor.extractSpecular()
        width = albedoTextures.width
        height = albedoTextures.height
        cubeWidth = cubeTextures["nx"]!!.width
        cubeHeight =  cubeTextures["nx"]!!.height
    }

    fun getPixel(u: Double, v: Double): Color {

//        return if (isCube) {
//            val xCoord = (cubeWidth * u).toInt().coerceIn(0, cubeWidth - 1)
//            val yCoord = (cubeHeight - (cubeHeight * v).toInt()).coerceIn(0, cubeHeight - 1)
//            val rgb = cubeTextures.getRGB(xCoord, yCoord)
//            val color = Color(rgb)
//            return Color(rgb)
//        } else {
            val xCoord = (width * u).toInt().coerceIn(0, width - 1)
            val yCoord = (height - (height * v).toInt()).coerceIn(0, height - 1)
            val rgb = albedoTextures.getRGB(xCoord, yCoord)
            val color = Color(rgb)
            return Color(rgb)
//        }

    }

    fun getNormal(u: Double, v: Double): Vector3D {
        var decX = 0
        if (abs(u - 1.0) < 0.00001) decX = 1
        var decY = 0
        if (abs(v) < 0.00001) decY = 1
        val rgb = normalTextures.getRGB((width * u).toInt() - decX, height - (height * v).toInt() - decY)
        val color = Color(rgb)
        return Vector3D(color.r.toDouble() / 255.0 * 2.0 - 1.0, color.g.toDouble() / 255.0 * 2.0 - 1.0, color.b.toDouble() / 255.0 * 2.0 - 1.0)
    }

    fun getSpecular(u: Double, v: Double): Color {
        var decX = 0
        if (abs(u - 1.0) < 0.00001) decX = 1
        var decY = 0
        if (abs(v) < 0.00001) decY = 1
        val rgb = specularTextures.getRGB((width * u).toInt() - decX, height - (height * v).toInt() - decY)
        val color = Color(rgb)
        return color
    }
    fun getCubeMapTexture(p: Vector3D): Color {

//        println(p.x)
//        println(p.y)
//        println(p.z)

//        println()
        val inc = 100.0
        val imageName = getImageName(p)
        val coords = when (imageName) {
            "px" -> getCorrected(inc, -p.z, -p.y)
            "py" -> getCorrected(inc, p.x, p.z)
            "pz" -> getCorrected(inc, p.x, -p.y)

            "nx" -> getCorrected(inc, p.z, -p.y)
            "ny" -> getCorrected(inc, p.x, -p.z)
            "nz" -> getCorrected(inc, -p.x, -p.y)

            else -> Vector2D.NULL_VECTOR
        }

        val xCoord = (cubeWidth * coords.x).toInt().coerceIn(0, cubeWidth - 1)
        val yCoord = (cubeHeight - (cubeHeight * coords.y).toInt()).coerceIn(0, cubeHeight - 1)
//        println("x $xCoord, y $yCoord, image $imageName")
        val rgb = cubeTextures[imageName]!!.getRGB(xCoord, yCoord)
        val color = Color(rgb)
//        println(color)
        return Color(rgb)
    }

    fun getImageName(p: Vector3D): String {
        if (p.x.absoluteValue > 99.999999) {
            return if (p.x > 0) "px" else "nx";
        }
        if (p.y.absoluteValue > 99.999999) {
            return if (p.x > 0) "py" else "ny";
        }
        if (p.z.absoluteValue > 99.999999) {
            return if (p.z > 0) "pz" else "nz";
        }
        return "nx"
    }

    fun getCorrected(inc: Double, v0: Double, v1: Double) = Vector2D((v0 + inc) / (inc * 2), (v1 + inc) / (inc * 2))


}
