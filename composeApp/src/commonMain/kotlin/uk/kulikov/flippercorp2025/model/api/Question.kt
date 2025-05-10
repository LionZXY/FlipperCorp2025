package uk.kulikov.flippercorp2025.model.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Question(
    @SerialName("question")
    val question: String,
    @SerialName("answer")
    val answer: String,
)