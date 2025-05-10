package uk.kulikov.flippercorp2025.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import uk.kulikov.flippercorp2025.eventactivity.EventActivityComposable
import uk.kulikov.flippercorp2025.faq.composable.FAQScreenComposable
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
            is RootComponent.Child.Schedule -> ScheduleMainScreenComposable(
                scheduleDecomposeComponent = child.component,
                modifier = Modifier.fillMaxSize(),
                onOpenActivity = component::onOpenActivity

            )

            is RootComponent.Child.Activity -> EventActivityComposable(
                eventActivity = child.activity,
                event = child.event
            )

            is RootComponent.Child.FAQ -> FAQScreenComposable(
                component = child.component,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}