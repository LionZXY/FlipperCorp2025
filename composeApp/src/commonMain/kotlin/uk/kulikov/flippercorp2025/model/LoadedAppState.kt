package uk.kulikov.flippercorp2025.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.serialization.Serializable
import uk.kulikov.flippercorp2025.model.api.Question
import uk.kulikov.flippercorp2025.utils.ImmutableListSerializer

@Serializable
data class LoadedAppState(
    @Serializable(with = ImmutableListSerializer::class)
    val questions: ImmutableList<Question>,
    @Serializable(with = ImmutableListSerializer::class)
    val dayEvents: ImmutableList<DayEvents>
)