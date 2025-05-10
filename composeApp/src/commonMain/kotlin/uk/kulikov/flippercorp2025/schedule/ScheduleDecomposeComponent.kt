package uk.kulikov.flippercorp2025.schedule

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.diamondedge.logging.logging
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch
import uk.kulikov.flippercorp2025.dao.NetworkDao
import uk.kulikov.flippercorp2025.model.DayEvents


class ScheduleDecomposeComponentImpl(
    componentContext: ComponentContext,
    val dayEvents: ImmutableList<DayEvents>
)