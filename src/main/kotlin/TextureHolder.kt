import graphics.Color
import linear.Vector3D
import java.awt.image.BufferedImage
import kotlin.math.abs

class TextureHolder {

    var albedoTextures: BufferedImage
    var normalTextures: BufferedImage
    var specularTextures: BufferedImage
    var width: Int
    var height: Int

    init {
        albedoTextures = ObjInfoExtractor.extractTextures()
        normalTextures = ObjInfoExtractor.extractNormals()
        specularTextures = ObjInfoExtractor.extractSpecular()
        width = albedoTextures.width
        height = albedoTextures.height
    }

    fun getPixel(u: Double, v: Double): Color {
        var decX = 0
        if (abs(u - 1.0) < 0.00001) decX = 1
        var decY = 0
        if (abs(v) < 0.00001) decY = 1
        val rgb = albedoTextures.getRGB((width * u).toInt() - decX, height - (height * v).toInt() - decY)
        return Color(rgb)
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

    fun getSpecular(u: Double, v: Double): Double {
        var decX = 0
        if (abs(u - 1.0) < 0.00001) decX = 1
        var decY = 0
        if (abs(v) < 0.00001) decY = 1
        val rgb = specularTextures.getRGB((width * u).toInt() - decX, height - (height * v).toInt() - decY)
        val color = Color(rgb)
        return color.luminance()
    }

}
