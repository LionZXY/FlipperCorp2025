package uk.kulikov.flippercorp2025.root

import kotlinx.serialization.Serializable
import uk.kulikov.flippercorp2025.model.LoadedAppState
import uk.kulikov.flippercorp2025.model.api.Event
import uk.kulikov.flippercorp2025.model.api.EventActivity

@Serializable
sealed class RootConfig {
    data object Loading : RootConfig()
    data class Loaded(
        val appState: LoadedAppState
    ) : RootConfig()
}