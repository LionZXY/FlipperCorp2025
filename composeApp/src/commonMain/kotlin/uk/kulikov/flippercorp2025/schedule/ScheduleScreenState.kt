package uk.kulikov.flippercorp2025.schedule

import uk.kulikov.flippercorp2025.model.DayEvents

sealed class ScheduleScreenState {
    data object Loading : ScheduleScreenState()

    data class Loaded(val data: List<DayEvents>) : ScheduleScreenState()
}