package com.pedroimai.comics

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
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

data class ComicsScreenModel(val comics: ComicsPayload.Comics)

@Composable
fun ComicsScreen(viewModel: ComicsViewModel) {
    val viewState by rememberFlowWithLifecycle(viewModel.uiState)
        .collectAsState(initial = ComicsUiState())

    when {
        viewState.loading && viewState.items.isNullOrEmpty() -> Loading()
        //viewState.loading -> ComicsList(viewModel, viewState.items, true)
        viewState.error != null -> ErrorScreen(onClick = { viewModel.fetchOrRetry() })
        viewState.items.isNullOrEmpty().not() -> ComicsList(viewModel, viewState.items, false)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ComicsList(
    viewModel: ComicsViewModel,
    list: MutableList<ComicsPayload.Comics.ComicModel>,
    isLoading: Boolean
) {
    val context = LocalContext.current

    return InfiniteLoadingList(
        modifier = Modifier.fillMaxWidth(),
        items = list,
        loadMore = {
            Log.d("pedroca","loading next page")
            viewModel.fetchOrRetry()
        },
    ) { _, item ->
        val comics = item as ComicsPayload.Comics.ComicModel
        Surface(onClick = {
            Toast.makeText(
                context,
                "${comics.name} clicked",
                Toast.LENGTH_SHORT
            ).show()
        }) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 16.dp)
                    .fillMaxWidth(),
                fontSize = 16.sp,
                text = comics.name
            )
        }

        if (isLoading) {
            CircularProgressIndicator()
        }
    }
}


@Composable
fun InfiniteLoadingList(
    modifier: Modifier,
    items: List<Any>,
    loadMore: () -> Unit,
    rowContent: @Composable (Int, Any) -> Unit
) {
    val listState = rememberLazyListState()
    val firstVisibleIndex = remember { mutableStateOf(listState.firstVisibleItemIndex) }
    LazyColumn(state = listState, modifier = modifier) {
        itemsIndexed(items) { index, item ->
            rowContent(index, item)
        }
    }
    if (listState.shouldLoadMore(firstVisibleIndex)) {
        loadMore()
    }
}

fun LazyListState.shouldLoadMore(rememberedIndex: MutableState<Int>): Boolean {
    val firstVisibleIndex = this.firstVisibleItemIndex
    if (rememberedIndex.value != firstVisibleIndex) {
        rememberedIndex.value = firstVisibleIndex
        return layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
    }
    return false
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
