import de.javagl.obj.Obj
import de.javagl.obj.ObjReader
import de.javagl.obj.ObjUtils
import java.io.FileInputStream


object ObjInfoExtractor {

    @JvmStatic
    fun extract(): Obj {
        val inputStream = FileInputStream("skull.obj")
        val obj = ObjUtils.convertToRenderable(
            ObjReader.read(inputStream)
        )
        return obj
    }
}
