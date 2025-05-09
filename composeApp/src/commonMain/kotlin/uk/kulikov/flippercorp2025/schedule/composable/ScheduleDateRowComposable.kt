package uk.kulikov.flippercorp2025.schedule.composable

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.hyperether.resources.stringResource
import flipperculturalflip2025.composeapp.generated.resources.Res
import flipperculturalflip2025.composeapp.generated.resources.week_friday
import flipperculturalflip2025.composeapp.generated.resources.week_monday
import flipperculturalflip2025.composeapp.generated.resources.week_saturday
import flipperculturalflip2025.composeapp.generated.resources.week_sunday
import flipperculturalflip2025.composeapp.generated.resources.week_thursday
import flipperculturalflip2025.composeapp.generated.resources.week_tuesday
import flipperculturalflip2025.composeapp.generated.resources.week_wednesday
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.todayIn
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ScheduleDateRowComposable(
    modifier: Modifier = Modifier,
    dates: List<LocalDate>,
    selected: LocalDate,
    onSelect: (LocalDate) -> Unit
) {

    LazyRow(modifier) {
        items(dates) { date ->
            ScheduleDateRowTab(
                date = date,
                selected = date == selected,
                onSelect = { onSelect(date) }
            )
        }
    }
}

@Composable
private fun ScheduleDateRowTab(
    date: LocalDate,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Tab(
        selected = selected,
        onClick = onSelect
    ) {
        Text(
            text = date.dayOfMonth.toString(),
        )
        val dayOfWeekNames = getDaysOfWeekNames()
        Text(
            text = date.format(LocalDate.Format {
                dayOfWeek(dayOfWeekNames)
            })
        )
    }
}

@Composable
private fun getDaysOfWeekNames(): DayOfWeekNames {
    return DayOfWeekNames(
        monday = stringResource(Res.string.week_monday),
        tuesday = stringResource(Res.string.week_tuesday),
        wednesday = stringResource(Res.string.week_wednesday),
        thursday = stringResource(Res.string.week_thursday),
        friday = stringResource(Res.string.week_friday),
        saturday = stringResource(Res.string.week_saturday),
        sunday = stringResource(Res.string.week_sunday)
    )
}

@Preview
@Composable
private fun ScheduleDateRowTabPreview() {
    MaterialTheme {
        val dates = (12..18).map { LocalDate(2025, 5, it) }
        ScheduleDateRowComposable(
            dates = dates,
            selected = LocalDate(2025, 5, 12),
            onSelect = {}
        )
    }
}

