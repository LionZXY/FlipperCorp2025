package uk.kulikov.flippercorp2025.loading

import com.arkivanov.decompose.value.Value

interface LoadingDecomposeComponent {
    val state: Value<LoadingState>

    fun tryLoadData()
}