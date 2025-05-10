package uk.kulikov.flippercorp2025.appbar

import androidx.compose.foundation.clickable
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.hyperether.resources.stringResource
import flipperculturalflip2025.composeapp.generated.resources.Res
import flipperculturalflip2025.composeapp.generated.resources.ic_forward
import org.jetbrains.compose.resources.painterResource
import uk.kulikov.flippercorp2025.root.RootComponent

@Composable
fun AppBarComposable(
    component: RootComponent,
    onOpenDrawer: () -> Unit,
) {
    TopAppBar(
        title = {
            val stack by component.stack.subscribeAsState()

            Text(
                text = stringResource(stack.active.instance.title)
            )
        },
        actions = {

        },
        navigationIcon = {
            Icon(
                modifier = Modifier.clickable(onClick = onOpenDrawer),
                painter = painterResource(Res.drawable.ic_forward),
                contentDescription = null
            )
        }
    )

}