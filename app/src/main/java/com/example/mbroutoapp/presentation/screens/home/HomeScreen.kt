package com.example.mbroutoapp.presentation.screens.home

import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.example.mbroutoapp.domain.model.Hero
import com.example.mbroutoapp.navigation.Screen
import com.example.mbroutoapp.presentation.common.ListContent

private const val TAG = "HomeScreen"

@ExperimentalCoilApi
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val allHeroes: LazyPagingItems<Hero> = homeViewModel.getAllHeroes().collectAsLazyPagingItems()

    Scaffold(topBar = {
        HomeTopBar(onSearchClicked = {
            navController.navigate(Screen.Search.route)
        }
        )
    }, content = {
        ListContent(
            heroes = allHeroes,
            navController = navController
        )
    })
}