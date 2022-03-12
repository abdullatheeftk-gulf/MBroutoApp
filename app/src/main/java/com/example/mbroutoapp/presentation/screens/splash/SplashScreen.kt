package com.example.mbroutoapp.presentation.screens.splash

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.mbroutoapp.R
import com.example.mbroutoapp.ui.theme.Purple500
import com.example.mbroutoapp.ui.theme.Purple700
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.ui.draw.rotate
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mbroutoapp.navigation.Screen

@Composable
fun SplashScreen(
    navController: NavHostController,
    splashViewModel:SplashViewModel = hiltViewModel()
) {

    val degree = remember {
        Animatable(0f)
    }
    val onBoardingCompleted by splashViewModel.onBoardingCompleted.collectAsState()

    LaunchedEffect(key1 = true) {
        degree.animateTo(
            targetValue = 360f,
            animationSpec = tween(
                durationMillis = 1000,
                delayMillis = 200
            )
        )
        navController.popBackStack()
        if (onBoardingCompleted){
            navController.navigate(Screen.Home.route)
        }else{
            navController.navigate(Screen.Welcome.route)
        }

    }
    Splash(degrees = degree.value)
}

@Composable
fun Splash(degrees: Float) {
    if (isSystemInDarkTheme()) {
        Box(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.rotate(degrees),
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = stringResource(
                    R.string.app_logo
                )
            )
        }
    } else {
        Box(
            modifier = Modifier
                .background(Brush.verticalGradient(listOf(Purple700, Purple500)))
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.rotate(degrees),
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = stringResource(
                    R.string.app_logo
                )
            )
        }
    }


}

/*
@Preview
@Composable
fun SplashScreenPreview() {
    Splash()
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SplashScreenPreviewNight() {
    Splash()
}*/
