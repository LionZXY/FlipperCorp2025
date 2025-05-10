package uk.kulikov.flippercorp2025.appbar.switch

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.flipperdevices.core.ui.tabswitch.ComposableTabSwitch
import com.hyperether.resources.AppLocale
import com.hyperether.resources.currentLanguage
import org.jetbrains.compose.ui.tooling.preview.Preview
import uk.kulikov.flippercorp2025.FlipperCorpTheme

@Composable
fun LanguageSwitchComposable(
    modifier: Modifier
) {
    ComposableTabSwitch(
        modifier = modifier,
        tabs = AppLocale.entries.reversed(),
        currentTab = currentLanguage.value
    ) { language ->
        val text = when (language) {
            AppLocale.DEFAULT -> "EN"
            AppLocale.RU -> "RU"
        }
        Text(
            modifier = Modifier.fillMaxWidth()
                .clickable {
                    currentLanguage.value = language
                },
            textAlign = TextAlign.Center,
            text = text
        )
    }
}

@Preview
@Composable
private fun LanguageSwitchComposablePreview() {
    FlipperCorpTheme {
        LanguageSwitchComposable(Modifier)
    }
}