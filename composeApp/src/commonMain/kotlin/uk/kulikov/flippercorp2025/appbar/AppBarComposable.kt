package uk.kulikov.flippercorp2025.appbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.hyperether.resources.stringResource
import flipperculturalflip2025.composeapp.generated.resources.Res
import flipperculturalflip2025.composeapp.generated.resources.ic_arrow_back
import flipperculturalflip2025.composeapp.generated.resources.ic_forward
import flipperculturalflip2025.composeapp.generated.resources.ic_hamburger
import org.jetbrains.compose.resources.painterResource
import uk.kulikov.flippercorp2025.appbar.switch.LanguageSwitchComposable
import uk.kulikov.flippercorp2025.main.MainDecomposeComponent
import uk.kulikov.flippercorp2025.root.RootComponent

@Composable
fun AppBarComposable(
    component: MainDecomposeComponent,
    onOpenDrawer: () -> Unit,
) {
    val stack by component.stack.subscribeAsState()

    TopAppBar(
        title = {

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(stack.active.instance.title)
            )
        },
        actions = {
            LanguageSwitchComposable(Modifier.width(128.dp))

        },
        navigationIcon = {
            if (stack.items.size > 1) {
                Icon(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clickable(onClick = component::onBackClicked),
                    painter = painterResource(Res.drawable.ic_arrow_back),
                    contentDescription = null
                )
            } else {
                Icon(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clickable(onClick = onOpenDrawer),
                    painter = painterResource(Res.drawable.ic_hamburger),
                    contentDescription = null
                )
            }
        }
    )

}