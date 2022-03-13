package com.pedroimai.comics

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.pedroimai.shared.domain.ComicsPayload

@Preview
@Composable
fun comicsListPreview() {
    ComicsList(
        list = ComicsPayload.Comics(
            listOf(
                ComicsPayload.Comics.ComicModel(
                    id = "1",
                    name = "Avengers"
                ),
                ComicsPayload.Comics.ComicModel(
                    id = "2",
                    name = "X-men"
                ),
                ComicsPayload.Comics.ComicModel(
                    id = "3",
                    name = "Wolverine"
                )
            )
        )
    )
}

@Preview(showBackground = true)
@Composable
fun LoadingPreview(){
    Loading()
}

@Preview(showBackground = true)
@Composable
fun ErrorPreview(){
    ErrorScreen()
}