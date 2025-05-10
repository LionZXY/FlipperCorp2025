package uk.kulikov.flippercorp2025.loading

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.diamondedge.logging.logging
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.serialization.decodeValueOrNull
import com.russhwolf.settings.serialization.encodeValue
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import uk.kulikov.flippercorp2025.loading.network.NetworkDao
import uk.kulikov.flippercorp2025.model.LoadedAppState

private const val SETTINGS_KEY = "app_state"

class LoadingDecomposeComponentImpl(
    private val componentContext: ComponentContext,
    private val settings: ObservableSettings,
    private val onLoaded: (LoadedAppState) -> Unit,
) : LoadingDecomposeComponent, ComponentContext by componentContext {
    private val logger = logging("LoadingDecomposeComponent")
    private val _state = MutableValue<LoadingState>(LoadingState.Loading.LoadingDates)
    override val state = _state

    init {
        tryLoadData()
    }

    override fun tryLoadData() {
        coroutineScope().launch {
            _state.value = LoadingState.Loading.LoadingDates
            val loadingLocally = tryLoadLocally()
                .onSuccess { onLoaded(it) }
                .onFailure { logger.error(it) { "Failed to load local storage. Reason: ${it.message}" } }
                .isSuccess
            if (loadingLocally) {
                return@launch
            }

            NetworkDao.load {
                _state.value = it
            }.onSuccess {
                //TODO saveLocally(it)
                onLoaded(it)
            }.onFailure {
                logger.error(it) { "Failed to load from network. Reason: ${it.message}" }
                _state.value = LoadingState.Failed
            }
        }
    }


    @OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
    private fun saveLocally(appState: LoadedAppState) {
        settings.encodeValue(SETTINGS_KEY, appState)
    }

    @OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
    private fun tryLoadLocally(): Result<LoadedAppState> {
        return runCatching { settings.decodeValueOrNull<LoadedAppState>(SETTINGS_KEY) }
            .mapCatching { it ?: error("Not found any loaded app state") }
    }
}