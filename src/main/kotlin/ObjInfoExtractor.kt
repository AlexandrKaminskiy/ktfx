import com.sun.javafx.iio.ImageFrame
import com.sun.prism.Image
import com.sun.prism.PhongMaterial
import com.sun.prism.TextureMap
import de.javagl.obj.Obj
import de.javagl.obj.ObjReader
import de.javagl.obj.ObjUtils
import java.awt.Frame
import java.io.FileInputStream


object ObjInfoExtractor {

    @JvmStatic
    fun extract(): Obj {
        val inputStream = FileInputStream("dragon.obj")
        val obj = ObjUtils.convertToRenderable(
            ObjReader.read(inputStream)
        )
        var tm = TextureMap(PhongMaterial.MapType.DIFFUSE)
        return obj
    }
}
