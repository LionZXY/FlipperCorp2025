package com.flipperdevices.core.ui.tabswitch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import uk.kulikov.flippercorp2025.appbar.switch.TabTransition
import uk.kulikov.flippercorp2025.appbar.switch.tabIndicatorOffset
import kotlin.enums.EnumEntries

@Composable
fun <Tab : Enum<Tab>> ComposableTabSwitch(
    tabs: Iterable<Tab>,
    currentTab: Tab,
    modifier: Modifier = Modifier,
    content: @Composable (Tab) -> Unit,
) {
    Box(
        modifier = modifier
            .padding(horizontal = 18.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.surface)
    ) {
        ComposableFapHubSwitchInternal(
            tab = currentTab,
            activeColor = MaterialTheme.colors.primary,
            content = content,
            tabs = tabs
        )
    }
}

@Composable
private fun <Tab : Enum<Tab>> ComposableFapHubSwitchInternal(
    tab: Tab,
    activeColor: Color,
    tabs: Iterable<Tab>,
    content: @Composable (Tab) -> Unit,
) {
    val tabIndex = tabs.indexOf(tab)
    var tabPositions by remember {
        mutableStateOf(emptyList<TabPosition>())
    }
    var tabHeightPx by remember { mutableIntStateOf(0) }
    if (tabPositions.size > tabIndex) {
        val currentTabPosition = tabPositions[tabIndex]
        val tabHeight = with(LocalDensity.current) { tabHeightPx.toDp() }
        ComposeTabAnimatedBackground(currentTabPosition, tabHeight, activeColor)
    }
    TabRow(
        modifier = Modifier
            .onGloballyPositioned {
                tabHeightPx = it.size.height
            },
        selectedTabIndex = tabIndex,
        backgroundColor = Color.Transparent,
        indicator = { tabPositions = it },
        // remove bottom divider from tabRow
        divider = { }
    ) {
        tabs.forEach {
            TabTransition(
                activeColor = MaterialTheme.colors.onPrimary,
                inactiveColor = MaterialTheme.colors.onSurface,
                selected = it == tab,
            ) {
                content(it)
            }
        }
    }
}

@Composable
private fun ComposeTabAnimatedBackground(
    tabPosition: TabPosition,
    tabHeight: Dp,
    activeColor: Color
) {
    Box(
        modifier = Modifier
            .tabIndicatorOffset(tabPosition)
            .height(tabHeight)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 2.dp, vertical = 2.dp)
                .fillMaxHeight()
                .fillMaxWidth()
                .clip(RoundedCornerShape(size = 8.dp))
                .background(activeColor)
        )
    }
}
