package uk.kulikov.flippercorp2025.schedule.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uk.kulikov.flippercorp2025.model.api.Event
import uk.kulikov.flippercorp2025.model.api.EventActivity

@Composable
fun ScheduleEventsLazyColumnComposable(
    events: List<Event>,
    onOpenActivity: (EventActivity, Event) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(events) { event ->
            ScheduleEventComposable(
                modifier = Modifier,
                event = event,
                onOpenActivity = {
                    onOpenActivity(it, event)
                }
            )
        }
    }
}