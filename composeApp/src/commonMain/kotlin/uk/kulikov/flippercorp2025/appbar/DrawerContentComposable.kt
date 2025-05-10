package uk.kulikov.flippercorp2025.appbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hyperether.resources.stringResource
import flipperculturalflip2025.composeapp.generated.resources.Res
import flipperculturalflip2025.composeapp.generated.resources.ic_arrow_back
import flipperculturalflip2025.composeapp.generated.resources.ic_calendar
import flipperculturalflip2025.composeapp.generated.resources.ic_help
import flipperculturalflip2025.composeapp.generated.resources.title_faq
import flipperculturalflip2025.composeapp.generated.resources.title_schedule
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import uk.kulikov.flippercorp2025.main.MainConfig
import uk.kulikov.flippercorp2025.main.MainDecomposeComponent

@Composable
fun DrawerContentComposable(
    component: MainDecomposeComponent,
    onBack: () -> Unit
) {
    Column {
        Box(
            Modifier
                .background(MaterialTheme.colors.primary)
                .clickable(onClick = onBack)
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        ) {
            Icon(
                modifier = Modifier.padding(16.dp),
                painter = painterResource(Res.drawable.ic_arrow_back),
                contentDescription = null,
                tint = MaterialTheme.colors.onPrimary
            )
        }
        Spacer(Modifier.weight(1f))

        Divider()

        DrawerElementComposable(
            modifier = Modifier.clickable {
                component.replace(MainConfig.Schedule)
                onBack()
            },
            text = Res.string.title_schedule,
            icon = Res.drawable.ic_calendar
        )

        Divider()

        DrawerElementComposable(
            modifier = Modifier.clickable {
                component.replace(MainConfig.FAQ)
                onBack()
            },
            text = Res.string.title_faq,
            icon = Res.drawable.ic_help
        )

        Divider()
    }

}

@Composable
private fun Divider() {
    Box(
        Modifier.fillMaxWidth()
            .height(1.dp)
            .background(MaterialTheme.colors.onSurface)
    )
}

@Composable
private fun DrawerElementComposable(
    icon: DrawableResource,
    text: StringResource,
    modifier: Modifier
) {
    Row(
        modifier
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .padding(end = 16.dp)
                .size(24.dp),
            painter = painterResource(icon),
            contentDescription = null,
            tint = MaterialTheme.colors.onSurface
        )
        Text(
            text = stringResource(text),
            modifier = Modifier
                .weight(1f),
            fontSize = 24.sp
        )
    }
}