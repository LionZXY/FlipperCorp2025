package uk.kulikov.flippercorp2025.root

import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import uk.kulikov.flippercorp2025.loading.composable.LoadingScreenComposable
import uk.kulikov.flippercorp2025.main.MainScreenComposable
import uk.kulikov.flippercorp2025.utils.backAnimation

@Composable
fun RootComposable(
    component: RootComponent,
    modifier: Modifier
) {
    Children(
        stack = component.stack,
        modifier = modifier,
        animation = backAnimation(
            backHandler = component.backHandler,
            onBack = component::onBackClicked,
        ),
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.Loaded -> MainScreenComposable(
                child.component,
                modifier = Modifier.background(MaterialTheme.colors.background)
            )

            is RootComponent.Child.Loading -> LoadingScreenComposable(
                child.component,
                modifier = Modifier.background(MaterialTheme.colors.background)
            )
        }
    }
}