package uk.kulikov.flippercorp2025.schedule

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.launch
import uk.kulikov.flippercorp2025.dao.NetworkDao

interface ScheduleDecomposeComponent {
    val screen: Value<ScheduleScreenState>
}

class ScheduleDecomposeComponentImpl(
    componentContext: ComponentContext
) : ScheduleDecomposeComponent {
    private val _screen = MutableValue<ScheduleScreenState>(
        ScheduleScreenState.Loading
    )
    override val screen: Value<ScheduleScreenState> = _screen

    init {
        componentContext.coroutineScope()
            .launch {
                _screen.value = ScheduleScreenState.Loaded(NetworkDao.getAllData())
            }
    }
}