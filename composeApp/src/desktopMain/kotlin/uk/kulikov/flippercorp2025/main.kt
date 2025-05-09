package uk.kulikov.flippercorp2025

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import uk.kulikov.flippercorp2025.root.DefaultRootComponent

fun main() = application {
    val lifecycle = LifecycleRegistry()

    // Always create the root component outside Compose on the UI thread
    val root = runOnUiThread {
        DefaultRootComponent(
            componentContext = DefaultComponentContext(lifecycle = lifecycle),
        )
    }
    Window(
        onCloseRequest = ::exitApplication,
        title = "FlipperCulturalFlip2025",
    ) {
        App(root)
    }
}