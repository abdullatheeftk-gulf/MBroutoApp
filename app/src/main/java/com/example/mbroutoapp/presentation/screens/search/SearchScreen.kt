package com.example.mbroutoapp.presentation.screens.search

import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.example.mbroutoapp.domain.model.Hero
import com.example.mbroutoapp.presentation.common.ListContent


private const val TAG = "SearchScreen"

@ExperimentalCoilApi
@Composable
fun SearchScreen(
    navController: NavHostController,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val searchQuery by searchViewModel.searchQuery
    val searchHeroes: LazyPagingItems<Hero> =
        searchViewModel.searchedHero.collectAsLazyPagingItems()
    val mSearchHeroes =
        searchViewModel.getSearchHeroes(query = searchQuery).collectAsLazyPagingItems()

    Log.i(TAG, "HomeScreen: ${searchHeroes.itemSnapshotList.items}")

    Scaffold(
        topBar = {
            SearchTopAppBar(
                text = searchQuery,
                onCloseClicked = {
                    navController.popBackStack()
                },
                onSearchClicked = {
                    searchViewModel.searchHeroes(query = it)
                },
                onTextChanged = searchViewModel::updateSearchQuery
            )
        },

        ) {
                ListContent(heroes =searchHeroes, navController = navController)
    }
}