package com.example.mbroutoapp.presentation.screens.home

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.mbroutoapp.R
import com.example.mbroutoapp.ui.theme.topAppBarBackGroundColor
import com.example.mbroutoapp.ui.theme.topAppBarContentColor

@Composable
fun HomeTopBar(onSearchClicked:()->Unit) {
    TopAppBar(
        title = {
            Text(
                text = "Explore",
                color = MaterialTheme.colors.topAppBarContentColor
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackGroundColor,
        actions = {
            IconButton(onClick = { onSearchClicked ()}) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(R.string.search_icon)
                )
            }
        }

    )
}
