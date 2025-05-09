package uk.kulikov.flippercorp2025.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.value.Value
import uk.kulikov.flippercorp2025.schedule.ScheduleDecomposeComponent
import uk.kulikov.flippercorp2025.schedule.ScheduleDecomposeComponentImpl

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>

    // It's possible to pop multiple screens at a time on iOS
    fun onBackClicked(toIndex: Int)

    // Defines all possible child components
    sealed class Child {
        class Schedule(val component: ScheduleDecomposeComponent) : Child()
    }
}

class DefaultRootComponent(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<RootConfig.Schedule>()

    override val stack: Value<ChildStack<RootConfig, RootComponent.Child>> =
        childStack(
            source = navigation,
            serializer = RootConfig.Schedule.serializer(),
            initialConfiguration = RootConfig.Schedule, // The initial child component is List
            handleBackButton = true, // Automatically pop from the stack on back button presses
            childFactory = ::child,
        )


    private fun child(
        config: RootConfig, componentContext: ComponentContext
    ): RootComponent.Child = when (config) {
        is RootConfig.Schedule -> RootComponent.Child.Schedule(
            ScheduleDecomposeComponentImpl(
                componentContext
            )
        )
    }

    override fun onBackClicked(toIndex: Int) {
        navigation.popTo(index = toIndex)
    }
}