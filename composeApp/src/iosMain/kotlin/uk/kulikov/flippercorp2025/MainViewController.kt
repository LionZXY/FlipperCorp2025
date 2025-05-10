package uk.kulikov.flippercorp2025

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackGestureOverlay
import com.arkivanov.essenty.backhandler.BackDispatcher
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.observable.makeObservable
import platform.UIKit.UIViewController
import uk.kulikov.flippercorp2025.root.DefaultRootComponent
import uk.kulikov.flippercorp2025.utils.PlatformAppPath
import uk.kulikov.flippercorp2025.utils.getAppPath

@OptIn(ExperimentalSettingsApi::class)
fun MainViewController(
    componentContext: ComponentContext,
    backDispatcher: BackDispatcher,
    settings: Settings
): UIViewController {
    val observableSettings = settings.makeObservable()
    val rootComponent = DefaultRootComponent(
        componentContext,
        settings = observableSettings,
        platformAppPath = PlatformAppPath(
            appPath = getAppPath()
        )
    )

    return ComposeUIViewController {
        PredictiveBackGestureOverlay(
            backDispatcher = backDispatcher,
            backIcon = null,
            modifier = Modifier.fillMaxSize(),
        ) {
            App(rootComponent)
        }
    }
}