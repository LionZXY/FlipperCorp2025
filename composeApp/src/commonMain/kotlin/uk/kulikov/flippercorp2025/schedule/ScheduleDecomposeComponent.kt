package uk.kulikov.flippercorp2025.schedule

import com.arkivanov.decompose.ComponentContext
import kotlinx.collections.immutable.ImmutableList
import uk.kulikov.flippercorp2025.model.DayEvents


class ScheduleDecomposeComponentImpl(
    componentContext: ComponentContext,
    val dayEvents: ImmutableList<DayEvents>
)