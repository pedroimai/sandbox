package com.pedroimai.comics

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
@Preview(showBackground = true)
fun comicsListPreview() {
    ComicsList(
        listItems = listOf(
            ComicsListItem.Comics(
                id = "1",
                title = "Avengers"
            ),
            ComicsListItem.Comics(
                id = "2",
                title = "X-men"
            ),
            ComicsListItem.Comics(
                id = "3",
                title = "Wolverine"
            )
        )
    ) {}
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