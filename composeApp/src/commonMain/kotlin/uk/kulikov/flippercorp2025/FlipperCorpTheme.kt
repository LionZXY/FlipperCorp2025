package uk.kulikov.flippercorp2025

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun FlipperCorpTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = MaterialTheme.colors
            .copy(
                primary = Color(0xFFff8200),
            ),
        content = content
    )
}