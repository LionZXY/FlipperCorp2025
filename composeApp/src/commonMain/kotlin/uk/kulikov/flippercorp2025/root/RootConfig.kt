package uk.kulikov.flippercorp2025.root

import kotlinx.serialization.Serializable

@Serializable
sealed class RootConfig {
    @Serializable
    data object Schedule : RootConfig()
}