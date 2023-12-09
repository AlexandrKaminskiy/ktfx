import de.javagl.obj.Obj
import de.javagl.obj.ObjReader
import de.javagl.obj.ObjUtils
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileInputStream
import javax.imageio.ImageIO


object ObjInfoExtractor {

    val model: String = "Shovel Knight"
    val cubeMap: String = "Cube"

    @JvmStatic
    fun extractObjFile(): Obj {
        val inputStream = FileInputStream("${model}/Model.obj")
        val obj = ObjUtils.convertToRenderable(
            ObjReader.read(inputStream)
        )
        return obj
    }

    @JvmStatic
    fun extractTextures(): BufferedImage {
        return ImageIO.read(File("${model}/Albedo Map.png"))
    }

    @JvmStatic
    fun extractNormals(): BufferedImage {
        return ImageIO.read(File("${model}/Normal Map.png"))
    }

    @JvmStatic
    fun extractSpecular(): BufferedImage {
        return ImageIO.read(File("${model}/Metalness Map.png"))
    }

    @JvmStatic
    fun extractCube(): Obj {
        val inputStream = FileInputStream("${cubeMap}/Model.obj")
        val obj = ObjUtils.convertToRenderable(
                ObjReader.read(inputStream)
        )
        return obj
    }

    @JvmStatic
    fun extractCubeMap(): BufferedImage {
        return ImageIO.read(File("StandardCubeMap.png"))
    }
}
