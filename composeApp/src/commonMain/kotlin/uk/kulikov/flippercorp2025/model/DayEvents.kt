package uk.kulikov.flippercorp2025.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import uk.kulikov.flippercorp2025.model.api.Event
import uk.kulikov.flippercorp2025.utils.ImmutableListSerializer

@Serializable
data class DayEvents(
    val date: LocalDate,
    @Serializable(with = ImmutableListSerializer::class)
    val events: ImmutableList<Event>
)