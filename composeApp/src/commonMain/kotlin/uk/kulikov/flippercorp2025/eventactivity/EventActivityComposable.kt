package uk.kulikov.flippercorp2025.eventactivity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import uk.kulikov.flippercorp2025.model.api.Event
import uk.kulikov.flippercorp2025.model.api.EventActivity
import uk.kulikov.flippercorp2025.model.api.localizeTextRemember

@Composable
fun EventActivityComposable(
    modifier: Modifier = Modifier,
    eventActivity: EventActivity,
    event: Event
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxWidth(),
            model = eventActivity.imageUri,
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        SelectionContainer {
            Column(
                Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = localizeTextRemember(eventActivity.name),
                    fontSize = 32.sp,
                    style = LocalTextStyle.current.copy(
                        lineHeight = 1.5.em,
                        lineHeightStyle = LineHeightStyle(
                            alignment = LineHeightStyle.Alignment.Center,
                            trim = LineHeightStyle.Trim.None
                        )
                    )
                )

                Text(
                    text = localizeTextRemember(eventActivity.description)
                )

                Text(
                    text = event.date.format(LocalDate.Format {
                        dayOfMonth()
                        chars("-")
                        monthNumber()
                        chars("-")
                        year()
                    }),
                    fontWeight = FontWeight.Bold
                )
                Text(text = "${event.startTime} - ${event.endTime}")
            }
        }

    }
}