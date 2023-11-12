package com.example.ktfx

import ZBuffer
import graphics.Point
import javafx.scene.canvas.GraphicsContext
import javafx.scene.image.PixelBuffer
import javafx.scene.image.PixelFormat
import javafx.scene.image.WritableImage
import java.nio.IntBuffer


class DrawService(private val context: GraphicsContext, private val width: Int, private val height: Int, private val zBuffer: ZBuffer) {

    fun drawImage() {
        context.clearRect(0.0, 0.0, width.toDouble(), height.toDouble())

        val intBuffer = IntBuffer.wrap(zBuffer.getColorMap())

        val pixelBuffer = PixelBuffer(width, height, intBuffer, PixelFormat.getIntArgbPreInstance())
        val image = WritableImage(pixelBuffer)



        context.drawImage(image, 0.0, 0.0)
        pixelBuffer.updateBuffer{ null }
        zBuffer.clear()
    }


    private fun getColor(col: Int): Int {

        return ((180.0 - col.toDouble()) * 255).toInt() shl 24
    }
}
