package com.pedroimai.comics

import com.pedroimai.shared.domain.ComicsPayload
import javax.inject.Inject

class ComicsModelMapper @Inject constructor() {
    operator fun invoke(payload: ComicsPayload): List<ComicsModel> {
        return mutableListOf<ComicsModel>().apply {
            addAll(
                payload.data.comics.map {
                    ComicsModel(id = it.id, title = it.name)
                }.toList()
            )
        }
    }
}
