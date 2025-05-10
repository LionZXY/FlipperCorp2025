package uk.kulikov.flippercorp2025

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import uk.kulikov.flippercorp2025.root.RootComponent
import uk.kulikov.flippercorp2025.root.RootComposable


@Composable
fun App(
    component: RootComponent,
    modifier: Modifier = Modifier
) {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .crossfade(true)
            .build()
    }
    FlipperCorpTheme {
        RootComposable(component, modifier)
    }
}