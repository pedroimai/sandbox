package com.pedroimai.comics

import com.pedroimai.shared.domain.ComicsPayload

data class ComicsUiState(
    val items: MutableList<ComicsPayload.Comics.ComicModel> = mutableListOf(),
    val currentPage: Int = 0,
    val loading: Boolean = true,
    val error: Throwable? = null
)
