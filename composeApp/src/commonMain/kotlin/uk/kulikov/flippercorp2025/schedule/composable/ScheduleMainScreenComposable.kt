package uk.kulikov.flippercorp2025.schedule.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import org.jetbrains.compose.ui.tooling.preview.Preview
import uk.kulikov.flippercorp2025.model.DayEvents
import uk.kulikov.flippercorp2025.model.api.Event
import uk.kulikov.flippercorp2025.model.api.EventActivity
import uk.kulikov.flippercorp2025.schedule.ScheduleDecomposeComponentImpl

@Composable
fun ScheduleMainScreenComposable(
    scheduleDecomposeComponent: ScheduleDecomposeComponentImpl,
    onOpenActivity: (EventActivity, Event) -> Unit,
    modifier: Modifier = Modifier
)  = Column(modifier) {
    var selectedDay by remember {
        mutableStateOf(
            getNearestDay(
                scheduleDecomposeComponent.dayEvents,
                Clock.System.todayIn(TimeZone.currentSystemDefault())
            )
        )
    }

    ScheduleDateRowComposable(
        dates = scheduleDecomposeComponent.dayEvents.map { it.date },
        selected = selectedDay.date,
        onSelect = { localDate ->
            val foundDay = scheduleDecomposeComponent.dayEvents.find { localDate == it.date }
            if (foundDay != null) {
                selectedDay = foundDay
            }
        }
    )

    ScheduleEventsLazyColumnComposable(
        events = selectedDay.events, onOpenActivity = onOpenActivity
    )
}

private fun getNearestDay(dates: List<DayEvents>, date: LocalDate): DayEvents {
    return dates.minByOrNull {
        it.date.toEpochDays() - date.toEpochDays()
    } ?: dates.first()
}

@Preview
@Composable
private fun ScheduleMainScreenComposableContentPreview() {

}