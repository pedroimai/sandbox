package com.pedroimai.comics

data class ComicsUiState(
    val items: MutableList<ComicsListItem> = mutableListOf(),
    val currentPage: Int = 0,
    val loading: Boolean = true,
    val error: Throwable? = null
)

sealed class ComicsListItem {
    data class Comics(val id: String, val title: String) : ComicsListItem()
    object Loading : ComicsListItem()
}
