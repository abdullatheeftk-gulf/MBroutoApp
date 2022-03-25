package com.example.mbroutoapp.presentation.screens.home

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.mbroutoapp.domain.model.Hero
import com.example.mbroutoapp.presentation.components.RatingWidget
import com.example.mbroutoapp.ui.theme.LARGE_PADDING

private const val TAG = "HomeScreen"
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val allHeroes:LazyPagingItems<Hero> = homeViewModel.getAllHeroes().collectAsLazyPagingItems()
    Log.e(TAG, "HomeScreen: ${allHeroes.itemCount}", )

    Scaffold(topBar = {
        HomeTopBar {

        }
    }) {
            RatingWidget(
                modifier = Modifier.padding(all = LARGE_PADDING),
                rating = 0.0
            )
    }
}