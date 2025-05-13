package uk.kulikov.flippercorp2025.main

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandler
import flipperculturalflip2025.composeapp.generated.resources.Res
import flipperculturalflip2025.composeapp.generated.resources.title_activity
import flipperculturalflip2025.composeapp.generated.resources.title_faq
import flipperculturalflip2025.composeapp.generated.resources.title_schedule
import org.jetbrains.compose.resources.StringResource
import uk.kulikov.flippercorp2025.faq.FAQDecomposeComponent
import uk.kulikov.flippercorp2025.model.api.Event
import uk.kulikov.flippercorp2025.model.api.EventActivity
import uk.kulikov.flippercorp2025.schedule.ScheduleDecomposeComponentImpl
import uk.kulikov.flippercorp2025.utils.LanguageSettings

interface MainDecomposeComponent {
    val stack: Value<ChildStack<*, Child>>
    val backHandler: BackHandler
    val languageSettings: LanguageSettings

    // It's possible to pop multiple screens at a time on iOS
    fun onBackClicked()

    // It's possible to pop multiple screens at a time on iOS
    fun onBackClicked(toIndex: Int)

    fun replace(config: MainConfig)

    fun onOpenActivity(activity: EventActivity, event: Event)

    sealed interface Child {
        val title: StringResource

        class Schedule(val component: ScheduleDecomposeComponentImpl) : Child {
            override val title = Res.string.title_schedule
        }

        class Activity(
            val activity: EventActivity,
            val event: Event
        ) : Child {
            override val title = Res.string.title_activity
        }

        class FAQ(
            val component: FAQDecomposeComponent
        ) : Child {
            override val title = Res.string.title_faq
        }
    }
}