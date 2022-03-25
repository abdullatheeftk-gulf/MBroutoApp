package com.example.mbroutoapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.example.mbroutoapp.presentation.screens.home.HomeScreen
import com.example.mbroutoapp.presentation.screens.splash.SplashScreen
import com.example.mbroutoapp.presentation.screens.welcome.WelcomeScreen
import com.example.mbroutoapp.util.Constants.DETAILS_ARGUMENT_KEY
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalCoilApi
@ExperimentalPagerApi
@Composable
fun SetUpNavGraph(
    navController:NavHostController
){
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route
        ){
            composable(route = Screen.Splash.route){
                SplashScreen(navController = navController)
            }
            composable(route = Screen.Welcome.route){
                WelcomeScreen(navController = navController)
            }
            composable(route = Screen.Home.route){
                HomeScreen(navController = navController)
            }
            composable(
                route = Screen.Details.route,
                arguments = listOf(navArgument(name = DETAILS_ARGUMENT_KEY){
                    type = NavType.IntType
                })
            ){

            }
            composable(route = Screen.Search.route){

            }
        }
}