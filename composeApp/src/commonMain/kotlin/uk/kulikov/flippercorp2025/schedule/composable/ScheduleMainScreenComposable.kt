package uk.kulikov.flippercorp2025.schedule.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import org.jetbrains.compose.ui.tooling.preview.Preview
import uk.kulikov.flippercorp2025.model.DayEvents
import uk.kulikov.flippercorp2025.model.api.Event
import uk.kulikov.flippercorp2025.model.api.EventActivity
import uk.kulikov.flippercorp2025.schedule.ScheduleDecomposeComponentImpl
import kotlin.math.abs

@Composable
fun ScheduleMainScreenComposable(
    scheduleDecomposeComponent: ScheduleDecomposeComponentImpl,
    onOpenActivity: (EventActivity, Event) -> Unit,
    modifier: Modifier = Modifier
) = Column(modifier) {
    var selectedDay by remember {
        mutableStateOf(
            getNearestDay(
                scheduleDecomposeComponent.dayEvents,
                Clock.System.todayIn(TimeZone.currentSystemDefault())
            )
        )
    }

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = scheduleDecomposeComponent.dayEvents.indexOf(selectedDay),
        pageCount = { scheduleDecomposeComponent.dayEvents.size }
    )
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            selectedDay = scheduleDecomposeComponent.dayEvents[page]
        }
    }

    ScheduleDateRowComposable(
        dates = scheduleDecomposeComponent.dayEvents.map { it.date },
        selected = selectedDay.date,
        onSelect = { localDate ->
            val foundDay = scheduleDecomposeComponent.dayEvents.find { localDate == it.date }
            if (foundDay != null) {
                selectedDay = foundDay
                scope.launch {
                    pagerState.scrollToPage(scheduleDecomposeComponent.dayEvents.indexOf(foundDay))
                }
            }
        }
    )

    HorizontalPager(
        state = pagerState,
        key = { index -> scheduleDecomposeComponent.dayEvents[index].date.toString() }
    ) { page ->
        val events = scheduleDecomposeComponent.dayEvents[page].events
        ScheduleEventsLazyColumnComposable(
            events = events, onOpenActivity = onOpenActivity
        )
    }

}

private fun getNearestDay(dates: List<DayEvents>, date: LocalDate): DayEvents {
    return dates.minBy {
        abs(it.date.toEpochDays() - date.toEpochDays())
    }
}

@Preview
@Composable
private fun ScheduleMainScreenComposableContentPreview() {

}