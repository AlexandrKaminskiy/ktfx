package com.example.ktfx

import graphics.Point
import javafx.scene.canvas.GraphicsContext
import javafx.scene.image.PixelBuffer
import javafx.scene.image.PixelFormat
import javafx.scene.image.WritableImage
import javafx.scene.paint.Color
import java.nio.IntBuffer


class DrawService(private val context: GraphicsContext, private val width: Int, private val height: Int) {

//    private val intColor: Int

    init {
        val color = Color(0.0, 0.0, 0.0, 1.0)
//        intColor =
//            color.opacity.toInt() * 255 shl 24 or (color.red.toInt() * 255 shl 16) or (color.green.toInt() * 255 shl 8) or color.blue.toInt() * 255
    }

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
//        val color = Color(222.0, 41.0, 41.0, 1.0)
//        println(color)
        return (col.toDouble() / 90.0 * 255).toInt() shl 24;// or ( shl 16) or (col * 255 shl 8) or col * 255
    }
}
