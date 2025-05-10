package uk.kulikov.flippercorp2025.loading.composable

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.hyperether.resources.stringResource
import flipperculturalflip2025.composeapp.generated.resources.Res
import flipperculturalflip2025.composeapp.generated.resources.loading_dates
import flipperculturalflip2025.composeapp.generated.resources.loading_events
import flipperculturalflip2025.composeapp.generated.resources.loading_failed
import flipperculturalflip2025.composeapp.generated.resources.loading_failed_btn
import flipperculturalflip2025.composeapp.generated.resources.loading_images
import flipperculturalflip2025.composeapp.generated.resources.loading_questions
import uk.kulikov.flippercorp2025.loading.LoadingDecomposeComponent
import uk.kulikov.flippercorp2025.loading.LoadingState

@Composable
fun LoadingScreenComposable(
    component: LoadingDecomposeComponent,
    modifier: Modifier = Modifier
) {
    val state by component.state.subscribeAsState()

    Column(
        modifier
            .safeDrawingPadding()
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()

        AnimatedContent(
            modifier = Modifier.padding(top = 8.dp),
            targetState = state,
            transitionSpec = { fadeIn().togetherWith(fadeOut()) },
            contentKey = {
                when (it) {
                    LoadingState.Failed -> 0
                    LoadingState.Loading.LoadingDates -> 1
                    LoadingState.Loading.LoadingQuestions -> 2
                    is LoadingState.Loading.WithProgress.LoadingEvents -> 3
                    is LoadingState.Loading.WithProgress.LoadingImages -> 4
                }
            },
        ) { currentState ->
            LoadingScreenStateComposable(currentState, component::tryLoadData)
        }
    }
}

@Composable
private fun LoadingScreenStateComposable(
    currentState: LoadingState,
    onRepeat: () -> Unit
) = Column(
    verticalArrangement = Arrangement.spacedBy(8.dp),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    var text = stringResource(
        when (currentState) {
            LoadingState.Failed -> Res.string.loading_failed
            LoadingState.Loading.LoadingDates -> Res.string.loading_dates
            LoadingState.Loading.LoadingQuestions -> Res.string.loading_questions
            is LoadingState.Loading.WithProgress.LoadingEvents -> Res.string.loading_events
            is LoadingState.Loading.WithProgress.LoadingImages -> Res.string.loading_images
        }
    )

    if (currentState is LoadingState.Loading) {
        text += animatedDots()
    }

    if (currentState is LoadingState.Loading.WithProgress) {
        text += " [${currentState.current}/${currentState.total}]"
    }

    Text(
        text = text,
        textAlign = TextAlign.Center,
    )

    if (currentState is LoadingState.Failed) {
        Button(onClick = onRepeat) {
            Text(
                stringResource(Res.string.loading_failed_btn)
            )
        }
    }
}


