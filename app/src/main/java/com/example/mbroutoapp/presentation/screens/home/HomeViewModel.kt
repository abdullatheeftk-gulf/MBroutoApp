package com.example.mbroutoapp.presentation.screens.home


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbroutoapp.data.remote.BroutoApi
import com.example.mbroutoapp.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: UseCases,

) : ViewModel() {


    fun getAllHeroes() = useCases.getAllHeroesUseCase()
}