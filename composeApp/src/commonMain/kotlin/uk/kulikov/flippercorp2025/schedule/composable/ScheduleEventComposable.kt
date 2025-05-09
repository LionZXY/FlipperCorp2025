package uk.kulikov.flippercorp2025.schedule.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import flipperculturalflip2025.composeapp.generated.resources.Res
import flipperculturalflip2025.composeapp.generated.resources.ic_forward
import org.jetbrains.compose.resources.painterResource
import uk.kulikov.flippercorp2025.model.Event
import uk.kulikov.flippercorp2025.model.EventActivity
import uk.kulikov.flippercorp2025.model.getLocalizedTitle

@Composable
fun ScheduleEventComposable(
    modifier: Modifier,
    event: Event,
    onOpenActivity: (EventActivity) -> Unit
) {
    val eventActivity = event.activity
    var cardModifier = modifier

    if (eventActivity != null) {
        cardModifier = cardModifier.clickable {
            onOpenActivity(eventActivity)
        }
    }

    Card(
        modifier = cardModifier,
        border = BorderStroke(1.dp, MaterialTheme.colors.primary),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "${event.startTime}-${event.endTime}",
                fontSize = 18.sp,
            )
            Text(
                modifier = Modifier.padding(horizontal = 8.dp)
                    .weight(1f),
                fontSize = 18.sp,
                text = event.getLocalizedTitle()
            )
            if (eventActivity != null) {
                Icon(
                    painter = painterResource(Res.drawable.ic_forward),
                    tint = MaterialTheme.colors.primary,
                    contentDescription = null
                )
            }
        }
    }
}