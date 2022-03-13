package com.pedroimai.comics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedroimai.shared.domain.ComicsPayload
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicsViewModel @Inject constructor(private val repository: ComicsRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow<Result<ComicsPayload.Comics>>(
        Result.success(
            ComicsPayload.Comics(listOf())
        )
    )
    val uiState: StateFlow<Result<ComicsPayload.Comics>> = _uiState

    init {
        fetchOrRetry()
    }

    fun fetchOrRetry() {
        viewModelScope.launch {
            repository.getComics().collect { result ->
                _uiState.value = result
            }
        }
    }
}