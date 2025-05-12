package uk.kulikov.flippercorp2025.schedule.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import uk.kulikov.flippercorp2025.model.api.Event
import uk.kulikov.flippercorp2025.model.api.EventActivity
import uk.kulikov.flippercorp2025.model.api.getEndDateTime
import uk.kulikov.flippercorp2025.model.api.getStartDateTime

@Composable
fun ScheduleEventsLazyColumnComposable(
    events: List<Event>,
    onOpenActivity: (EventActivity, Event) -> Unit,
) {
    val sortedEvents = remember(events) { events.sortedBy { it.getStartDateTime() } }
    LazyColumn(
        modifier = Modifier.padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(sortedEvents) { event ->
            ScheduleEventComposable(
                modifier = Modifier,
                event = event,
                isDisabled = event.getEndDateTime() < Clock.System.now()
                    .toLocalDateTime(TimeZone.currentSystemDefault()),
                onOpenActivity = {
                    onOpenActivity(it, event)
                }
            )
        }
    }
}