package com.pedroimai.characters

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersListViewModel @Inject constructor(private val repository: CharacterRepository) :
    ViewModel() {
    fun getCharacters() = repository.getCharacters()
}