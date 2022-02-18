package com.pedroimai.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedroimai.shared.domain.CharactersPayload
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersListViewModel @Inject constructor(private val repository: CharacterRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow<Result<CharactersPayload.Characters>>(
        Result.success(
            CharactersPayload.Characters(listOf())
        )
    )
    val uiState: StateFlow<Result<CharactersPayload.Characters>> = _uiState

    init {
        viewModelScope.launch {
            repository.getCharacters().collect { result ->
                _uiState.value = result
            }
        }
    }

}