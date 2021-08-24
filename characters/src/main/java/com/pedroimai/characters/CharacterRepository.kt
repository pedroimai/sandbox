package com.pedroimai.characters

import javax.inject.Inject

class CharacterRepository @Inject constructor() {
    fun getCharacters() = listOf(
        CharacterModel(id = "1", name = "Spiderman"),
        CharacterModel(id = "2", name = "Wolverine"),
        CharacterModel(id = "3", name = "Iron-Man"),
        CharacterModel(id = "4", name = "Thanos"),
    )
}