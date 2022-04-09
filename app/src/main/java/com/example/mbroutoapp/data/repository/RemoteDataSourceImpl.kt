package com.example.mbroutoapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mbroutoapp.data.local.BroutoDatabase
import com.example.mbroutoapp.data.paging_source.HeroRemoteMediator
import com.example.mbroutoapp.data.paging_source.SearchHeroesSource
import com.example.mbroutoapp.data.remote.BroutoApi
import com.example.mbroutoapp.domain.model.Hero
import com.example.mbroutoapp.domain.repository.RemoteDataSource
import com.example.mbroutoapp.util.Constants.ITEM_PER_PAGE
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class RemoteDataSourceImpl(
    private val broutoApi: BroutoApi,
    private val broutoDatabase: BroutoDatabase
):RemoteDataSource {

    private val heroDao = broutoDatabase.heroDao()

    override fun getAllHeroes(): Flow<PagingData<Hero>> {
            val pagingSourceFactory = {
                heroDao.getAllHeroes()
            }
        return Pager(
            config = PagingConfig(pageSize = ITEM_PER_PAGE),
            remoteMediator = HeroRemoteMediator(
                broutoApi = broutoApi,
                broutoDatabase = broutoDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun searchHeroes(query: String): Flow<PagingData<Hero>> {
           return  Pager(
               config = PagingConfig(pageSize = ITEM_PER_PAGE),
               pagingSourceFactory ={
                   SearchHeroesSource(
                       broutoApi = broutoApi,
                       query = query
                   )
               }

           ).flow
    }
}