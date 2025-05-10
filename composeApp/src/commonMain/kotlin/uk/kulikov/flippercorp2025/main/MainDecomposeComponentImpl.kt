package uk.kulikov.flippercorp2025.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import uk.kulikov.flippercorp2025.faq.FAQDecomposeComponent
import uk.kulikov.flippercorp2025.model.LoadedAppState
import uk.kulikov.flippercorp2025.model.api.Event
import uk.kulikov.flippercorp2025.model.api.EventActivity
import uk.kulikov.flippercorp2025.schedule.ScheduleDecomposeComponentImpl

class MainDecomposeComponentImpl(
    private val componentContext: ComponentContext,
    private val loadedAppState: LoadedAppState
) : MainDecomposeComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<MainConfig>()

    override val stack: Value<ChildStack<MainConfig, MainDecomposeComponent.Child>> =
        childStack(
            source = navigation,
            serializer = MainConfig.serializer(),
            initialConfiguration = MainConfig.Schedule, // The initial child component is List
            handleBackButton = true, // Automatically pop from the stack on back button presses
            childFactory = ::child,
        )

    override fun onOpenActivity(activity: EventActivity, event: Event) {
        navigation.pushNew(MainConfig.Activity(activity, event))
    }

    override fun replace(config: MainConfig) {
        navigation.replaceAll(config)
    }

    private fun child(
        config: MainConfig, componentContext: ComponentContext
    ): MainDecomposeComponent.Child = when (config) {
        is MainConfig.Schedule -> MainDecomposeComponent.Child.Schedule(
            ScheduleDecomposeComponentImpl(
                componentContext = componentContext,
                dayEvents = loadedAppState.dayEvents
            )
        )

        is MainConfig.Activity -> MainDecomposeComponent.Child.Activity(
            config.activity,
            config.event
        )

        MainConfig.FAQ -> MainDecomposeComponent.Child.FAQ(
            FAQDecomposeComponent(
                componentContext = componentContext,
                questions = loadedAppState.questions
            )
        )
    }

    override fun onBackClicked(toIndex: Int) {
        navigation.popTo(index = toIndex)
    }
}