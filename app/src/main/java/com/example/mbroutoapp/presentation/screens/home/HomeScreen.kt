package com.example.mbroutoapp.presentation.screens.home

import android.util.Log
import androidx.compose.material.Scaffold

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.mbroutoapp.domain.model.Hero

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

    }
}