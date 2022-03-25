package com.example.mbroutoapp.presentation.screens.home

import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.mbroutoapp.domain.model.Hero
import com.example.mbroutoapp.presentation.common.ListContent

private const val TAG = "HomeScreen"
@Composable
fun HomeScreen(
    navController:NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val allHeroes:LazyPagingItems<Hero> = homeViewModel.getAllHeroes().collectAsLazyPagingItems()
    Log.e(TAG, "HomeScreen: ${allHeroes.itemCount}", )

    Scaffold(topBar = {
        HomeTopBar {

        }
    }, content = {
        ListContent(
            heroes = allHeroes,
            navController = navController
        )
    })
}