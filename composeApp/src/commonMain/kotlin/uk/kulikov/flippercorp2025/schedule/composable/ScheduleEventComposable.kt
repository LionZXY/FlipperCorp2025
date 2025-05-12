package uk.kulikov.flippercorp2025.schedule.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import flipperculturalflip2025.composeapp.generated.resources.Res
import flipperculturalflip2025.composeapp.generated.resources.ic_forward
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import uk.kulikov.flippercorp2025.FlipperCorpTheme
import uk.kulikov.flippercorp2025.model.api.Event
import uk.kulikov.flippercorp2025.model.api.EventActivity
import uk.kulikov.flippercorp2025.model.api.getLocalizedTitle

@Composable
fun ScheduleEventComposable(
    modifier: Modifier = Modifier,
    event: Event,
    isDisabled: Boolean,
    onOpenActivity: (EventActivity) -> Unit
) {
    val eventActivity = event.activity
    var cardModifier = modifier

    if (eventActivity != null) {
        cardModifier = cardModifier.clickable {
            onOpenActivity(eventActivity)
        }
    }
    if (isDisabled) {
        cardModifier = cardModifier.alpha(0.5f)
    }
    SelectionContainer {
        Card(
            modifier = cardModifier,
            border = BorderStroke(
                1.dp, if (isDisabled) {
                    Color(0xFF808080)
                } else {
                    MaterialTheme.colors.primary
                }
            ),
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.widthIn(min = 102.dp),
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
}

@Preview
@Composable
private fun ScheduleEventComposablePreview() {
    FlipperCorpTheme {
        Column {
            ScheduleEventComposable(
                event = Event(
                    startTime = LocalTime(9, 0, 0),
                    endTime = LocalTime(0, 0, 0),
                    id = 1,
                    date = LocalDate(2023, 1, 1),
                    activity = null,
                    title = "Event"
                ),
                isDisabled = false,
                onOpenActivity = {}
            )

            ScheduleEventComposable(
                event = Event(
                    startTime = LocalTime(9, 0, 0),
                    endTime = LocalTime(0, 0, 0),
                    id = 1,
                    date = LocalDate(2023, 1, 1),
                    activity = null,
                    title = "Event"
                ),
                isDisabled = true,
                onOpenActivity = {}
            )
        }
    }
}