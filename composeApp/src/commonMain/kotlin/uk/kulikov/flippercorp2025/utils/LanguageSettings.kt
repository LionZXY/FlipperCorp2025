package uk.kulikov.flippercorp2025.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.hyperether.resources.AppLocale
import com.hyperether.resources.currentLanguage
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.coroutines.getStringOrNullFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val LANGUAGE_CODE_KEY = "language_code"

class LanguageSettings(
    private val settings: ObservableSettings
) {
    fun getLanguageFlow(): Flow<AppLocale?> {
        return settings.getStringOrNullFlow(LANGUAGE_CODE_KEY)
            .map { code ->
                code?.let { AppLocale.findByCode(it) }
            }
    }

    fun setLanguage(language: AppLocale) {
        settings.putString(LANGUAGE_CODE_KEY, language.code)
    }
}

@Composable
fun LanguageSettings.getLanguage(): AppLocale {
    return getLanguageFlow().collectAsState(null).value ?: currentLanguage.value
}