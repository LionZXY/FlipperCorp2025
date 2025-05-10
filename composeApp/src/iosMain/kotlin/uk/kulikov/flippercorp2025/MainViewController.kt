package uk.kulikov.flippercorp2025

import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.ComponentContext
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
        App(rootComponent)
    }
}