package com.pedroimai.comics

import javax.inject.Inject

class ComicsViewStateMapper @Inject constructor() {
    operator fun invoke(model: List<ComicsModel>): List<ComicsListItem> =
        mutableListOf<ComicsListItem>().apply {
            addAll(
                model.map {
                    ComicsListItem.Comics(id = it.id, title = it.title)
                }
            )
        }
}
