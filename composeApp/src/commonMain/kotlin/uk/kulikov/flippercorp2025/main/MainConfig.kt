package uk.kulikov.flippercorp2025.main

import kotlinx.serialization.Serializable
import uk.kulikov.flippercorp2025.model.api.Event
import uk.kulikov.flippercorp2025.model.api.EventActivity
import uk.kulikov.flippercorp2025.root.RootConfig
@Serializable
sealed interface MainConfig {
    @Serializable
    data object Schedule : MainConfig

    @Serializable
    data class Activity(
        val activity: EventActivity,
        val event: Event
    ) : MainConfig

    @Serializable
    data object FAQ : MainConfig
}