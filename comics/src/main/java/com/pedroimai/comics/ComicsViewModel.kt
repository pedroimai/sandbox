package com.pedroimai.comics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicsViewModel @Inject constructor(
    private val repository: ComicsRepository,
    private val viewStateMapper: ComicsViewStateMapper
) :
    ViewModel() {

    private val _uiState = MutableStateFlow(
        ComicsUiState()
    )

    val uiState: StateFlow<ComicsUiState> = _uiState

    init {
        fetchOrRetry()
    }

    fun fetchOrRetry() {
        viewModelScope.launch {
            repository.getComics(uiState.value.currentPage)
                .collect { uiState ->
                    _uiState.value = reduce(uiState, _uiState)
                }
        }
    }

    private fun reduce(
        result: Result<List<ComicsModel>>,
        uiState: MutableStateFlow<ComicsUiState>
    ): ComicsUiState =
        when (result) {
            is Result.Success -> uiState.value.copy(
                items = uiState.value.items.apply { addAll(viewStateMapper(result.data)) },
                currentPage = uiState.value.currentPage + 1,
                loading = false,
                error = null
            )
            is Result.Loading -> uiState.value.copy(
                loading = true,
                error = null
            )
            is Result.Failed -> uiState.value.copy(
                loading = false,
                error = result.error
            )
        }
}