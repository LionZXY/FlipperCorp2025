package uk.kulikov.flippercorp2025.schedule.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hyperether.resources.stringResource
import flipperculturalflip2025.composeapp.generated.resources.Res
import flipperculturalflip2025.composeapp.generated.resources.week_friday
import flipperculturalflip2025.composeapp.generated.resources.week_monday
import flipperculturalflip2025.composeapp.generated.resources.week_saturday
import flipperculturalflip2025.composeapp.generated.resources.week_sunday
import flipperculturalflip2025.composeapp.generated.resources.week_thursday
import flipperculturalflip2025.composeapp.generated.resources.week_tuesday
import flipperculturalflip2025.composeapp.generated.resources.week_wednesday
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.DayOfWeekNames
import org.jetbrains.compose.ui.tooling.preview.Preview
import uk.kulikov.flippercorp2025.FlipperCorpTheme

@Composable
fun ScheduleDateRowComposable(
    modifier: Modifier = Modifier,
    dates: List<LocalDate>,
    selected: LocalDate,
    onSelect: (LocalDate) -> Unit
) {
    ScrollableTabRow(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.primary,
        selectedTabIndex = dates.indexOf(selected),
        tabs = {
            dates.forEach { date ->
                ScheduleDateRowTab(
                    modifier = Modifier,
                    date = date,
                    selected = date == selected,
                    onSelect = { onSelect(date) }
                )
            }
        }
    )
}

@Composable
private fun ScheduleDateRowTab(
    modifier: Modifier = Modifier,
    date: LocalDate,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Tab(
        modifier = modifier,
        selected = selected,
        onClick = onSelect
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
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
    FlipperCorpTheme {
        val dates = (12..18).map { LocalDate(2025, 5, it) }
        ScheduleDateRowComposable(
            dates = dates,
            selected = LocalDate(2025, 5, 12),
            onSelect = {}
        )
    }
}

