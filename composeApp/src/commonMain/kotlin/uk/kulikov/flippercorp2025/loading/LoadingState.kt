package uk.kulikov.flippercorp2025.loading

sealed interface LoadingState {
    sealed interface Loading : LoadingState {
        data object LoadingDates : Loading

        sealed interface WithProgress : Loading {
            val current: Int
            val total: Int

            data class LoadingEvents(
                override val current: Int,
                override val total: Int
            ) : WithProgress

            data class LoadingImages(
                override val current: Int,
                override val total: Int
            ) : WithProgress
        }

        data object LoadingQuestions : Loading
    }

    data object Failed : LoadingState
}