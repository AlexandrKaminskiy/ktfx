package com.example.ktfx

import MatrixInitializer
import VectorCalculator
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

    @FXML
    fun initialize() {
        drawService = DrawService(canvas.graphicsContext2D, canvas.width.toInt(), canvas.height.toInt())
        canvas.onKeyPressed = CustomKeyListener(this)
        canvas.isFocusTraversable = true
        vectorCalculator = VectorCalculator(canvas.width, canvas.height, ObjInfoExtractor.extract(), MatrixInitializer(canvas.width, canvas.height, xMin, yMin, angle))
        drawImage(Matrix4x4.DIAGONAL)
    }


    fun drawImage(transformation: Matrix4x4) {
        val currentTimeMillis = System.currentTimeMillis()
        val points = vectorCalculator.calculate(transformation)
        drawService.drawImage(points)
        avgFps.add(1000 / (System.currentTimeMillis() - currentTimeMillis))

        println("Average fps " + avgFps.average())


    }

}
