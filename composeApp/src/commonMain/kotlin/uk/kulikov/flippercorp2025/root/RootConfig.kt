package uk.kulikov.flippercorp2025.root

import kotlinx.serialization.Serializable
import uk.kulikov.flippercorp2025.model.Event
import uk.kulikov.flippercorp2025.model.EventActivity

@Serializable
sealed class RootConfig {
    @Serializable
    data object Schedule : RootConfig()

    @Serializable
    data class Activity(
        val activity: EventActivity,
        val event: Event
    ) : RootConfig()
}