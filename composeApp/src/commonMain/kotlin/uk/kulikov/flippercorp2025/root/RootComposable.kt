package uk.kulikov.flippercorp2025.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import uk.kulikov.flippercorp2025.eventactivity.EventActivityComposable
import uk.kulikov.flippercorp2025.faq.composable.FAQScreenComposable
import uk.kulikov.flippercorp2025.loading.LoadingScreenComposable
import uk.kulikov.flippercorp2025.main.MainScreenComposable
import uk.kulikov.flippercorp2025.schedule.composable.ScheduleMainScreenComposable

@Composable
fun RootComposable(
    component: RootComponent,
    modifier: Modifier
) {
    Children(
        stack = component.stack,
        modifier = modifier,
        animation = stackAnimation(fade()),
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.Loaded -> MainScreenComposable(child.component)
            is RootComponent.Child.Loading -> LoadingScreenComposable(child.component)
        }
    }
}