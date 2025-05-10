package uk.kulikov.flippercorp2025.appbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
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
import flipperculturalflip2025.composeapp.generated.resources.ic_forward
import flipperculturalflip2025.composeapp.generated.resources.ic_hamburger
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
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clickable(onClick = onOpenDrawer),
                painter = painterResource(Res.drawable.ic_hamburger),
                contentDescription = null
            )
        }
    )

}