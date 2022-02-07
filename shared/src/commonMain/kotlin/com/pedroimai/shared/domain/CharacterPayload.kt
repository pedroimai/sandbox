package com.pedroimai.shared.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CharactersPayload(@SerialName("data") val data: Characters) {
    @Serializable
    data class Characters(@SerialName("results") val characters: List<CharacterModel>) {
        @Serializable
        data class CharacterModel(
            @SerialName("id") val id: String,
            @SerialName("name") val name: String,
            @SerialName("description") val description: String,
            @SerialName("thumbnail") val thumbnail: Thumbnail
        ) {
            @Serializable
            data class Thumbnail(
                @SerialName("path") val path: String,
                @SerialName("extension") val extension: String
            )
        }
    }
}
