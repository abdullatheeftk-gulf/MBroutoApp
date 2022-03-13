package com.example.mbroutoapp.data.repository

import androidx.paging.PagingData
import com.example.mbroutoapp.domain.model.Hero
import com.example.mbroutoapp.domain.repository.DataStoreOperations
import com.example.mbroutoapp.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val dataStoreOperations: DataStoreOperations
) {

    fun getAllHeroes():Flow<PagingData<Hero>>{
        return remoteDataSource.getAllHeroes()
    }

    suspend fun saveOnBoardingState(completed:Boolean){
        dataStoreOperations.saveOnBoardingState(completed = completed)
    }

    fun readOnBoardingState():Flow<Boolean>{
        return dataStoreOperations.readOnBoardingState()
    }

}