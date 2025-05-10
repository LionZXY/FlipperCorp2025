package uk.kulikov.flippercorp2025.faq

import com.arkivanov.decompose.ComponentContext
import kotlinx.collections.immutable.ImmutableList
import uk.kulikov.flippercorp2025.model.api.Question

class FAQDecomposeComponent(
    private val componentContext: ComponentContext,
    val questions: ImmutableList<Question>
) {
}