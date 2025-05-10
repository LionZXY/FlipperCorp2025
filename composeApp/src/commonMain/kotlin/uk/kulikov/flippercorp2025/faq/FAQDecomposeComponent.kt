package uk.kulikov.flippercorp2025.faq

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.diamondedge.logging.logging
import kotlinx.coroutines.launch
import uk.kulikov.flippercorp2025.dao.NetworkDao
import uk.kulikov.flippercorp2025.model.Question

class FAQDecomposeComponent(
    private val componentContext: ComponentContext
) {
    private val _screen = MutableValue<List<Question>>(emptyList())
    val screen: Value<List<Question>> = _screen

    init {
        componentContext.coroutineScope()
            .launch {
                runCatching {
                    NetworkDao.getQuestions()
                }.onSuccess {
                    _screen.value = it
                }.onFailure {
                    logging("FAQDecomposeComponent").error(it) { "Failed to load data" }
                }
            }
    }
}