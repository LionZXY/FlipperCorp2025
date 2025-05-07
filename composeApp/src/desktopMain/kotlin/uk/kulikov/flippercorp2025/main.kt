package uk.kulikov.flippercorp2025

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "FlipperCulturalFlip2025",
    ) {
        App()
    }
}