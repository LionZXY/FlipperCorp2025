package uk.kulikov.flippercorp2025

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import uk.kulikov.flippercorp2025.eventactivity.EventActivityComposable
import uk.kulikov.flippercorp2025.root.RootComponent
import uk.kulikov.flippercorp2025.schedule.composable.ScheduleMainScreenComposable


@Composable
fun App(component: RootComponent, modifier: Modifier = Modifier) {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .crossfade(true)
            .build()
    }
    FlipperCorpTheme {
        Surface(
            modifier
                .safeDrawingPadding()
        ) {
            Children(
                stack = component.stack,
                modifier = Modifier,
                animation = stackAnimation(fade()),
            ) {
                when (val child = it.instance) {
                    is RootComponent.Child.Schedule -> ScheduleMainScreenComposable(
                        scheduleDecomposeComponent = child.component,
                        modifier = Modifier.fillMaxSize(),
                        onOpenActivity = component::onOpenActivity

                    )

                    is RootComponent.Child.Activity -> EventActivityComposable(
                        eventActivity = child.activity,
                        event = child.event
                    )
                }
            }
        }
    }
}