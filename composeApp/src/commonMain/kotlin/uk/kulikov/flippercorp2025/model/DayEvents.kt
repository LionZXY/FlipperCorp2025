package uk.kulikov.flippercorp2025.model

import kotlinx.datetime.LocalDate

data class DayEvents(
    val date: LocalDate,
    val events: List<Event>
)