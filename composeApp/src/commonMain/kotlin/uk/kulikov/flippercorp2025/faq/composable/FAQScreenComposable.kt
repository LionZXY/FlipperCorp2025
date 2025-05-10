package uk.kulikov.flippercorp2025.faq.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import flipperculturalflip2025.composeapp.generated.resources.Res
import flipperculturalflip2025.composeapp.generated.resources.ic_drop_down
import flipperculturalflip2025.composeapp.generated.resources.ic_drop_up
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import uk.kulikov.flippercorp2025.FlipperCorpTheme
import uk.kulikov.flippercorp2025.faq.FAQDecomposeComponent
import uk.kulikov.flippercorp2025.model.Question
import uk.kulikov.flippercorp2025.model.localizeTextRemember

@Composable
fun FAQScreenComposable(
    component: FAQDecomposeComponent,
    modifier: Modifier = Modifier,
) {
    val questions by component.screen.subscribeAsState()

    LazyColumn(
        modifier = modifier
    ) {
        items(questions) { question ->
            SelectionContainer {
                QuestionElement(question)
            }
        }
    }
}

@Composable
private fun QuestionElement(element: Question) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)

    ) {
        Column {
            Row(
                modifier = Modifier.clickable {
                    expanded = !expanded
                }.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = localizeTextRemember(element.question),
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = if (expanded) {
                        FontWeight.Bold
                    } else {
                        FontWeight.Normal
                    },
                )

                val icon = if (expanded) {
                    Res.drawable.ic_drop_up
                } else {
                    Res.drawable.ic_drop_down
                }
                Icon(
                    modifier = Modifier.padding(8.dp),
                    painter = painterResource(icon),
                    contentDescription = null,
                )
            }
            if (expanded) {
                Text(
                    text = localizeTextRemember(element.answer),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun QuestionElementPreview() {
    FlipperCorpTheme {
        QuestionElement(Question("Question", "Answer"))
    }
}