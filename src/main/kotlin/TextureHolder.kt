import graphics.Color
import linear.Vector3D
import java.awt.image.BufferedImage
import kotlin.math.abs

class TextureHolder {

    var albedoTextures: BufferedImage
    var normalTextures: BufferedImage
    var specularTextures: BufferedImage
    var cubeTextures: BufferedImage
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
        cubeWidth = cubeTextures.width
        cubeHeight = cubeTextures.height
    }

    fun getPixel(u: Double, v: Double, isCube: Boolean): Color {

        return if (isCube) {
            val xCoord = (cubeWidth * u).toInt().coerceIn(0, cubeWidth - 1)
            val yCoord = (cubeHeight - (cubeHeight * v).toInt()).coerceIn(0, cubeHeight - 1)
            val rgb = cubeTextures.getRGB(xCoord, yCoord)
            val color = Color(rgb)
            return Color(rgb)
        } else {
            val xCoord = (width * u).toInt().coerceIn(0, width - 1)
            val yCoord = (height - (height * v).toInt()).coerceIn(0, height - 1)
            val rgb = albedoTextures.getRGB(xCoord, yCoord)
            val color = Color(rgb)
            return Color(rgb)
        }

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

}
