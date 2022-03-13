package com.example.mbroutoapp.presentation.screens.home

import androidx.lifecycle.ViewModel
import com.example.mbroutoapp.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    fun getAllHeroes() = useCases.getAllHeroesUseCase()
}