package com.example.ktfx.listener

import TranslationType
import TranslationUtils
import com.example.ktfx.HelloController
import javafx.event.EventHandler
import javafx.scene.input.KeyEvent
import linear.Matrix4x4

class CustomKeyListener(val controller: HelloController) : EventHandler<KeyEvent> {
    override fun handle(event: KeyEvent?) {
        println("BABUBA")

        val matrix4x4 = when (event?.code?.code) {
            87 -> TranslationUtils.translate(TranslationType.TRANSLATE_X, true)
            83 -> TranslationUtils.translate(TranslationType.TRANSLATE_X, false)
            65 -> TranslationUtils.translate(TranslationType.TRANSLATE_Y, true)
            68 -> TranslationUtils.translate(TranslationType.TRANSLATE_Y, false)
            90 -> TranslationUtils.translate(TranslationType.ZOOM, true)
            88 -> TranslationUtils.translate(TranslationType.ZOOM, false)
            89 -> TranslationUtils.translate(TranslationType.TRANSLATE_Z, true)
            85 -> TranslationUtils.translate(TranslationType.TRANSLATE_Z, false)
            else -> Matrix4x4.DIAGONAL
        }
        controller.drawImage(matrix4x4)
    }
}
