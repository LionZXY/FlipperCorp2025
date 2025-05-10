package uk.kulikov.flippercorp2025

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.DrawerValue
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.primarySurface
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.stack.active
import com.hyperether.resources.stringResource
import uk.kulikov.flippercorp2025.root.RootComponent
import uk.kulikov.flippercorp2025.root.RootComposable


@Composable
fun App(component: RootComponent, modifier: Modifier = Modifier) {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .crossfade(true)
            .build()
    }
    FlipperCorpTheme {
        Surface(
            modifier
                .background(MaterialTheme.colors.primarySurface)
                .statusBarsPadding()
                .background(MaterialTheme.colors.background)
                .safeDrawingPadding()
                .fillMaxSize()
        ) {
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            ModalDrawer(
                drawerContent = {
                }
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                val stack by component.stack.subscribeAsState()

                                Text(
                                    text = stringResource(stack.active.instance.title)
                                )
                            }
                        )
                    },
                ) { innerPadding ->
                    RootComposable(
                        component = component,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}