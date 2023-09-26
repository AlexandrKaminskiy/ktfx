package com.example.ktfx

import graphics.Point
import javafx.scene.canvas.GraphicsContext
import javafx.scene.image.PixelBuffer
import javafx.scene.image.PixelFormat
import javafx.scene.image.WritableImage
import javafx.scene.paint.Color
import java.nio.IntBuffer


class DrawService(private val context: GraphicsContext, private val width: Int, private val height: Int) {

    private val intColor: Int

    init {
        val color = Color(0.0, 0.0, 0.0, 1.0)
        intColor =
            color.opacity.toInt() * 255 shl 24 or (color.red.toInt() * 255 shl 16) or (color.green.toInt() * 255 shl 8) or color.blue.toInt() * 255
    }

    fun drawImage(points: List<Point>) {
        context.clearRect(0.0, 0.0, width.toDouble(), height.toDouble())

        val intBuffer = IntBuffer.allocate(width * height)
        val pixels = intBuffer.array()
        points.forEach {
            val pos = it.y * (width) + it.x
            if (pos > 0 && pos < pixels.size) {
                pixels[pos] = intColor
            }
        }

        val pixelBuffer = PixelBuffer(width, height, intBuffer, PixelFormat.getIntArgbPreInstance())
        val image = WritableImage(pixelBuffer)



        context.drawImage(image, 0.0, 0.0)
        pixelBuffer.updateBuffer{ null }
    }


}
