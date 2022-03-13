package com.example.mbroutoapp.domain.repository

import androidx.paging.PagingData
import com.example.mbroutoapp.domain.model.Hero
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    fun getAllHeroes():Flow<PagingData<Hero>>
    fun searchHeroes(name:String):Flow<PagingData<Hero>>
}