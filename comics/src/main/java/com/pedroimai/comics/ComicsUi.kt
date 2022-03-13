package com.pedroimai.comics

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.pedroimai.shared.domain.ComicsPayload
import kotlinx.coroutines.flow.Flow

data class ComicsScreenModel(val comics: ComicsPayload.Comics)

@Composable
fun ComicsScreen(viewModel: ComicsViewModel) {
    val viewState by rememberFlowWithLifecycle(viewModel.uiState)
        .collectAsState(initial = Result.loading())

    when (viewState) {
        is Result.Loading -> Loading()
        is Result.Failed -> ErrorScreen(onClick = { viewModel.fetchOrRetry() })
        is Result.Success -> ComicsList((viewState as Result.Success<ComicsPayload.Comics>).data)
    }
}

@Composable
fun ComicsList(list: ComicsPayload.Comics) =
    LazyColumn {
        items(items = list.comics) { comic ->
            Text(text = comic.name)
        }
    }

@Composable
fun Loading() =
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }

@Composable
fun ErrorScreen(
    title: String = "Algo deu errado",
    retryButtonText: String = "Tentar Novamente",
    onClick: () -> Unit = {}
) =
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = title)
            Button(onClick = onClick) {
                Text(text = retryButtonText)
            }
        }
    }

@Composable
fun <T> rememberFlowWithLifecycle(
    flow: Flow<T>,
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): Flow<T> = remember(flow, lifecycle) {
    flow.flowWithLifecycle(
        lifecycle = lifecycle,
        minActiveState = minActiveState
    )
}
