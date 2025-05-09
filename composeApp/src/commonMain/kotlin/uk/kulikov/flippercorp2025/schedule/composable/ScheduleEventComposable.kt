package uk.kulikov.flippercorp2025.schedule.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hyperether.resources.currentLanguage
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

    Card(modifier = cardModifier) {
        Row {
            Text(
                text = "${event.startTime}-${event.endTime}"
            )
            Text(
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