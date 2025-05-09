package uk.kulikov.flippercorp2025.schedule.composable

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uk.kulikov.flippercorp2025.model.Event

@Composable
fun ScheduleEventsLazyColumnComposable(
    events: List<Event>
) {
    LazyColumn {
        items(events) { event ->
            ScheduleEventComposable(
                modifier = Modifier,
                event = event,
                onOpenActivity = {}
            )
        }
    }
}