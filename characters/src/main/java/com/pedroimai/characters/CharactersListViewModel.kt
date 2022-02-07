package com.pedroimai.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.pedroimai.shared.domain.CharactersPayload
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersListViewModel @Inject constructor(private val repository: CharacterRepository) :
    ViewModel() {

    val characters: LiveData<Result<CharactersPayload.Characters>>
        get() = repository.getCharacters().asLiveData(viewModelScope.coroutineContext)
}