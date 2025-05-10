package uk.kulikov.flippercorp2025.schedule

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.diamondedge.logging.logging
import kotlinx.coroutines.launch
import uk.kulikov.flippercorp2025.dao.NetworkDao


class ScheduleDecomposeComponentImpl(
    componentContext: ComponentContext
)  {
    private val _screen = MutableValue<ScheduleScreenState>(
        ScheduleScreenState.Loading
    )
    val screen: Value<ScheduleScreenState> = _screen

    init {
        componentContext.coroutineScope()
            .launch {
                runCatching {
                    ScheduleScreenState.Loaded(NetworkDao.getAllData())
                }.onSuccess {
                    _screen.value = it
                }.onFailure {
                    logging("ScheduleDecomposeComponent").error(it) { "Failed to load data" }
                }
            }
    }
}