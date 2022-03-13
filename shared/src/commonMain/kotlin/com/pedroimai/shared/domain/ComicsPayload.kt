package com.pedroimai.shared.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ComicsPayload(@SerialName("data") val data: Comics) {
    @Serializable
    data class Comics(@SerialName("results") val comics: List<ComicModel>) {
        @Serializable
        data class ComicModel(
            @SerialName("id") val id: String,
            @SerialName("title") val name: String,
        )
    }
}
