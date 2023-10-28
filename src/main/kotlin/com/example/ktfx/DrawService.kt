package com.example.ktfx

import graphics.Point
import javafx.scene.canvas.GraphicsContext
import javafx.scene.image.PixelBuffer
import javafx.scene.image.PixelFormat
import javafx.scene.image.WritableImage
import java.nio.IntBuffer


class DrawService(private val context: GraphicsContext, private val width: Int, private val height: Int) {

    fun drawImage(points: List<Point>) {
        context.clearRect(0.0, 0.0, width.toDouble(), height.toDouble())

        val intBuffer = IntBuffer.allocate(width * height)
        val pixels = intBuffer.array()
        points.stream().forEach {
            val pos = it.y * (width) + it.x
            if (pos > 0 && pos < pixels.size) {
                pixels[pos] = getColor(it.c)
            }
        }

        val pixelBuffer = PixelBuffer(width, height, intBuffer, PixelFormat.getIntArgbPreInstance())
        val image = WritableImage(pixelBuffer)



        context.drawImage(image, 0.0, 0.0)
        pixelBuffer.updateBuffer{ null }
    }


    private fun getColor(col: Int): Int {

        return ((180.0 - col.toDouble()) * 255).toInt() shl 24
    }
}
