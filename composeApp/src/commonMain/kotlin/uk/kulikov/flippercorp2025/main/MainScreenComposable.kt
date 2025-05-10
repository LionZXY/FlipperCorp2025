package uk.kulikov.flippercorp2025.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.DrawerValue
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.primarySurface
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import kotlinx.coroutines.launch
import uk.kulikov.flippercorp2025.appbar.AppBarComposable
import uk.kulikov.flippercorp2025.appbar.DrawerContentComposable
import uk.kulikov.flippercorp2025.eventactivity.EventActivityComposable
import uk.kulikov.flippercorp2025.faq.composable.FAQScreenComposable
import uk.kulikov.flippercorp2025.schedule.composable.ScheduleMainScreenComposable
import uk.kulikov.flippercorp2025.utils.backAnimation

@Composable
fun MainScreenComposable(
    component: MainDecomposeComponent,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier
            .background(MaterialTheme.colors.primarySurface)
            .statusBarsPadding()
            .background(MaterialTheme.colors.background)
            .safeDrawingPadding()
            .fillMaxSize()
    ) {
        val scope = rememberCoroutineScope()
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        ModalDrawer(
            drawerState = drawerState,
            gesturesEnabled = false,
            drawerContent = {
                DrawerContentComposable(component, onBack = {
                    scope.launch {
                        drawerState.close()
                    }
                })
            }
        ) {
            Scaffold(
                topBar = {
                    AppBarComposable(component, onOpenDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    })
                },
            ) { innerPadding ->
                MainScreenContentComposable(
                    component = component,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Composable
private fun MainScreenContentComposable(component: MainDecomposeComponent, modifier: Modifier) {
    Children(
        stack = component.stack,
        modifier = modifier,
        animation = backAnimation(
            backHandler = component.backHandler,
            onBack = component::onBackClicked,
        ),
    ) {
        when (val child = it.instance) {
            is MainDecomposeComponent.Child.Schedule -> ScheduleMainScreenComposable(
                scheduleDecomposeComponent = child.component,
                modifier = Modifier.fillMaxSize(),
                onOpenActivity = component::onOpenActivity

            )

            is MainDecomposeComponent.Child.Activity -> EventActivityComposable(
                eventActivity = child.activity,
                event = child.event
            )

            is MainDecomposeComponent.Child.FAQ -> FAQScreenComposable(
                component = child.component,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}