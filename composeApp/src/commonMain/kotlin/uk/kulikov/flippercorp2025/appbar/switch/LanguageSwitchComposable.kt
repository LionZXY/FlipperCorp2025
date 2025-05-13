package uk.kulikov.flippercorp2025.appbar.switch

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.flipperdevices.core.ui.tabswitch.ComposableTabSwitch
import com.hyperether.resources.AppLocale
import uk.kulikov.flippercorp2025.utils.LanguageSettings
import uk.kulikov.flippercorp2025.utils.getLanguage

@Composable
fun LanguageSwitchComposable(
    modifier: Modifier,
    settings: LanguageSettings
) {
    ComposableTabSwitch(
        modifier = modifier,
        tabs = AppLocale.entries.reversed(),
        currentTab = settings.getLanguage()
    ) { language ->
        val text = when (language) {
            AppLocale.DEFAULT -> "EN"
            AppLocale.RU -> "RU"
        }
        Text(
            modifier = Modifier.fillMaxWidth()
                .clickable {
                    settings.setLanguage(language)
                },
            textAlign = TextAlign.Center,
            text = text
        )
    }
}