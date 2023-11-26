package com.example.ktfx

import ObjInfoExtractor
import TextureHolder
import VectorCalculator
import ZBuffer
import com.example.ktfx.listener.CustomKeyListener
import javafx.fxml.FXML
import javafx.scene.canvas.Canvas
import linear.Matrix4x4

class HelloController {

    @FXML
    private lateinit var canvas: Canvas
    private val angle = 45
    private val xMin = 0.0
    private val yMin = 0.0
    private lateinit var vectorCalculator: VectorCalculator
    private lateinit var drawService: DrawService
    private val avgFps = mutableListOf<Long>()
    private lateinit var zBuffer: ZBuffer

    @FXML
    fun initialize() {
        canvas.onKeyPressed = CustomKeyListener(this)
        canvas.isFocusTraversable = true
        zBuffer = ZBuffer(canvas.width.toInt(), canvas.height.toInt())
        drawService = DrawService(canvas.graphicsContext2D, canvas.width.toInt(), canvas.height.toInt(), zBuffer)

        vectorCalculator = VectorCalculator(
                zBuffer,
                canvas.width,
                canvas.height,
                angle, xMin, yMin,
                ObjInfoExtractor.extractObjFile()
        )
        drawImage(Matrix4x4.DIAGONAL)
    }


    fun drawImage(transformation: Matrix4x4) {
        val currentTimeMillis = System.currentTimeMillis()
        val points = vectorCalculator.calculate(transformation)
//        val polygon1 = Polygon(arrayListOf(
//                Vector4D(100.0, 100.0, 0.0, 0.0),
//
//                Vector4D(50.0, 500.0, 0.0, 0.0),
//                Vector4D(500.0, 50.0, 0.0, 0.0)
//        ), 90
//        )
//        val polygon2 = Polygon(arrayListOf(
//                Vector4D(505.3, 150.0, 90.0, 0.0),
//
//                Vector4D(500.0, 100.0, 90.0, 0.0),
//                Vector4D(700.0, 100.0, 90.0, 0.0)
//        ), 90
//        )

//        val points = arrayListOf<Polygon>(polygon1).stream().forEach { ShapesAlgos(zBuffer).triangle(it) }
        drawService.drawImage()
        avgFps.add(1000 / (System.currentTimeMillis() - currentTimeMillis))

    }

}
