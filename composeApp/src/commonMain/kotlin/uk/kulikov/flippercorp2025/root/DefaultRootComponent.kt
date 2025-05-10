package uk.kulikov.flippercorp2025.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandler
import com.russhwolf.settings.ObservableSettings
import uk.kulikov.flippercorp2025.loading.LoadingDecomposeComponent
import uk.kulikov.flippercorp2025.loading.LoadingDecomposeComponentImpl
import uk.kulikov.flippercorp2025.main.MainDecomposeComponent
import uk.kulikov.flippercorp2025.main.MainDecomposeComponentImpl
import uk.kulikov.flippercorp2025.root.RootComponent.Child.*
import uk.kulikov.flippercorp2025.utils.PlatformAppPath

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>
    val backHandler: BackHandler

    fun onBackClicked()
    // It's possible to pop multiple screens at a time on iOS
    fun onBackClicked(toIndex: Int)

    sealed interface Child {
        data class Loading(val component: LoadingDecomposeComponent) : Child
        data class Loaded(val component: MainDecomposeComponent) : Child
    }
}

class DefaultRootComponent(
    componentContext: ComponentContext,
    private val settings: ObservableSettings,
    private val platformAppPath: PlatformAppPath
) : RootComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<RootConfig>()

    override val stack: Value<ChildStack<RootConfig, RootComponent.Child>> =
        childStack(
            source = navigation,
            serializer = RootConfig.serializer(),
            initialConfiguration = RootConfig.Loading, // The initial child component is List
            childFactory = ::child,
        )

    private fun child(
        config: RootConfig, componentContext: ComponentContext
    ): RootComponent.Child = when (config) {
        is RootConfig.Loaded -> Loaded(
            MainDecomposeComponentImpl(
                componentContext, config.appState
            )
        )

        RootConfig.Loading -> Loading(
            LoadingDecomposeComponentImpl(
                componentContext,
                settings,
                onLoaded = { navigation.replaceAll(RootConfig.Loaded(it)) },
                platformAppPath = platformAppPath
            )
        )
    }

    override fun onBackClicked() {
        val child = stack.value.active.instance as? Loaded
        if (child != null) {
            child.component.onBackClicked()
        } else {
            navigation.pop()
        }
    }

    override fun onBackClicked(toIndex: Int) {
        val child = stack.value.active.instance as? Loaded
        if (child != null) {
            child.component.onBackClicked(toIndex)
        } else {
            navigation.popTo(toIndex)
        }
    }
}