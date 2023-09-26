import linear.Matrix4x4
import linear.Vector4D
import kotlin.math.cos
import kotlin.math.sin

object TranslationUtils {

    private const val angle = Math.PI / 36
    private const val zoom = 1.1

    @JvmStatic
    fun translate(type: TranslationType, increment: Boolean): Matrix4x4 {

        val currAngle = if (increment) angle else -angle
        val curZoom = if (increment) zoom else 1 / zoom

        return when (type) {
            TranslationType.TRANSLATE_X -> Matrix4x4(
                arrayOf(
                    doubleArrayOf(1.0, 0.0, 0.0, 0.0),
                    doubleArrayOf(0.0, cos(currAngle), -sin(currAngle), 0.0),
                    doubleArrayOf(0.0, sin(currAngle), cos(currAngle), 0.0),
                    doubleArrayOf(0.0, 0.0, 0.0, 1.0)
                )
            )

            TranslationType.TRANSLATE_Y -> Matrix4x4(
                arrayOf(
                    doubleArrayOf(cos(currAngle), 0.0, sin(currAngle), 0.0),
                    doubleArrayOf(0.0, 1.0, 0.0, 0.0),
                    doubleArrayOf(-sin(currAngle), 0.0, cos(currAngle), 0.0),
                    doubleArrayOf(0.0, 0.0, 0.0, 1.0)
                )
            )

            TranslationType.TRANSLATE_Z -> Matrix4x4(
                arrayOf(
                    doubleArrayOf(cos(currAngle), -sin(currAngle), 0.0, 0.0),
                    doubleArrayOf(sin(currAngle), cos(currAngle), 0.0, 0.0),
                    doubleArrayOf(0.0, 0.0, 1.0, 0.0),
                    doubleArrayOf(0.0, 0.0, 0.0, 1.0)
                )
            )

            TranslationType.ZOOM -> Matrix4x4(
                arrayOf(
                    doubleArrayOf(curZoom, 0.0, 0.0, 0.0),
                    doubleArrayOf(0.0, curZoom, 0.0, 0.0),
                    doubleArrayOf(0.0, 0.0, curZoom, 0.0),
                    doubleArrayOf(0.0, 0.0, 0.0, 1.0)
                )
            )

        }

    }
}
