import de.javagl.obj.Obj
import de.javagl.obj.ObjReader
import de.javagl.obj.ObjUtils
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileInputStream
import javax.imageio.ImageIO


object ObjInfoExtractor {

    @JvmStatic
    fun extractObjFile(): Obj {
        val inputStream = FileInputStream("Head/Model.obj")
        val obj = ObjUtils.convertToRenderable(
            ObjReader.read(inputStream)
        )
        return obj
    }

    @JvmStatic
    fun extractTextures(): BufferedImage {
        return ImageIO.read(File("Head/Albedo Map.png"))
    }

    @JvmStatic
    fun extractNormals(): BufferedImage {
        return ImageIO.read(File("Head/Normal Map.png"))
    }

    @JvmStatic
    fun extractSpecular(): BufferedImage {
        return ImageIO.read(File("Head/Specular Map.png"))
    }

}
