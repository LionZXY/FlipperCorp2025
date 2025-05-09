package uk.kulikov.flippercorp2025.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.hyperether.resources.AppLocale
import com.hyperether.resources.currentLanguage
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class Event(
    @SerialName("id") var id: Int,
    @SerialName("date") var date: LocalDate,
    @SerialName("startTime") var startTime: LocalTime,
    @SerialName("endTime") var endTime: LocalTime,
    @SerialName("activity") var activity: EventActivity?,
    @SerialName("title") var title: String
)

@Composable
fun Event.getLocalizedTitle(): String {
    return localizeTextRemember(title)
}

@Composable
fun localizeTextRemember(text: String): String {
    val appLocale = currentLanguage.value

    return remember(text, appLocale) {
        val segments = text.split("_EN_")

        when (appLocale) {
            AppLocale.RU -> segments.getOrNull(index = 0)
            else -> segments.getOrNull(index = 1)
        } ?: text
    }
}
