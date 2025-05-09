package uk.kulikov.flippercorp2025.schedule.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import org.jetbrains.compose.ui.tooling.preview.Preview
import uk.kulikov.flippercorp2025.model.DayEvents
import uk.kulikov.flippercorp2025.schedule.ScheduleDecomposeComponent
import uk.kulikov.flippercorp2025.schedule.ScheduleScreenState

@Composable
fun ScheduleMainScreenComposable(
    scheduleDecomposeComponent: ScheduleDecomposeComponent,
    modifier: Modifier
) {
    val screen by scheduleDecomposeComponent.screen.subscribeAsState()

    when (val localScreen = screen) {
        is ScheduleScreenState.Loaded -> ScheduleMainScreenComposableContent(
            modifier = modifier,
            screenStateLoaded = localScreen
        )

        ScheduleScreenState.Loading -> Box(
            modifier
                .fillMaxSize()
        ) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }
}

@Composable
private fun ScheduleMainScreenComposableContent(
    modifier: Modifier,
    screenStateLoaded: ScheduleScreenState.Loaded
) = Column(modifier) {
    var selectedDay by remember {
        mutableStateOf(
            getNearestDay(
                screenStateLoaded.data,
                Clock.System.todayIn(TimeZone.currentSystemDefault())
            )
        )
    }

    ScheduleDateRowComposable(
        dates = screenStateLoaded.data.map { it.date },
        selected = selectedDay.date,
        onSelect = { localDate ->
            val foundDay = screenStateLoaded.data.find { localDate == it.date }
            if (foundDay != null) {
                selectedDay = foundDay
            }
        }
    )

    ScheduleEventsLazyColumnComposable(events = selectedDay.events)
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