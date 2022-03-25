package com.pedroimai.comics

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.pedroimai.shared.domain.ComicsPayload
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged

data class ComicsScreenModel(val comics: ComicsPayload.Comics)

@ExperimentalMaterialApi
@Composable
fun ComicsScreen(viewModel: ComicsViewModel) {
    val viewState by rememberFlowWithLifecycle(viewModel.uiState)
        .collectAsState(initial = ComicsUiState())

    when {
        viewState.loading && viewState.items.isNullOrEmpty() -> Loading()
        //viewState.loading -> ComicsList(viewModel, viewState.items, true)
        viewState.error != null -> ErrorScreen(onClick = { viewModel.fetchOrRetry() })
        viewState.items.isNullOrEmpty().not() -> ComicsList(
            viewState.items
        ) {
            viewModel.fetchOrRetry()
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun ComicsList(
    listItems: List<ComicsListItem>,
    onLoadMore: () -> Unit
) {
    val listState = rememberLazyListState()
    val context = LocalContext.current

    LazyColumn(
        state = listState
    ) {
        items(items = listItems) { comics ->
            when (comics) {
                is ComicsListItem.Comics -> ComicsListItemColumn(context, comics)
                is ComicsListItem.Loading -> Loading()
            }
        }
    }

    InfiniteListHandler(listState = listState) {
        onLoadMore()
    }
}

@ExperimentalMaterialApi
@Composable
private fun ComicsListItemColumn(
    context: Context,
    comics: ComicsListItem.Comics
) {
    Surface(onClick = {
        Toast.makeText(
            context,
            "${comics.title} clicked",
            Toast.LENGTH_SHORT
        ).show()
    }) {
        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 16.dp)
                .fillMaxWidth(),
            fontSize = 16.sp,
            text = comics.title,
        )
    }
}


@Composable
fun InfiniteListHandler(
    listState: LazyListState,
    buffer: Int = 2,
    onLoadMore: () -> Unit
) {
    val loadMore = remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItemsNumber = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1

            lastVisibleItemIndex > (totalItemsNumber - buffer)
        }
    }

    LaunchedEffect(loadMore) {
        snapshotFlow { loadMore.value }
            .distinctUntilChanged()
            .collect {
                onLoadMore()
            }
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
