package uk.kulikov.flippercorp2025.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class EventActivity(
    @SerialName("id") var id: Int,
    @SerialName("typeCode") var typeCode: String?,
    @SerialName("imageUri") var imageUri: String,
    @SerialName("name") var name: String,
    @SerialName("description") var description: String,
    @SerialName("profitable") var profitable: Boolean
)