package com.example.mbroutoapp.data.paging_source

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.mbroutoapp.data.local.BroutoDatabase
import com.example.mbroutoapp.data.remote.BroutoApi
import com.example.mbroutoapp.domain.model.Hero
import com.example.mbroutoapp.domain.model.HeroRemoteKeys
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@ExperimentalPagingApi
class HeroRemoteMediator @Inject constructor(
    private val broutoApi: BroutoApi,
    private val broutoDatabase: BroutoDatabase
) : RemoteMediator<Int, Hero>() {

    private val heroDao = broutoDatabase.heroDao()
    private val heroRemoteKeysDao = broutoDatabase.heroRemoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        val currentTime = System.currentTimeMillis()
        val lastUpdated = heroRemoteKeysDao.getRemoteKeys(heroId = 1)?.lastUpdated ?: 0L
        val cacheTimeOut = 5

        Log.w("RemoteMediator", "Current time: ${parseMillis(currentTime)}", )
        Log.d("RemoteMediator", "Last Updated: ${parseMillis(lastUpdated)}", )


        val diffInMinute = (currentTime - lastUpdated) / 1000 / 60

        return if (diffInMinute.toInt() <= cacheTimeOut) {
            Log.i("RemoteMediator", "Up to date ")
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            Log.e("RemoteMediator", "Refresh ")
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Hero>): MediatorResult {

        return try {

            val page = when (loadType) {

                LoadType.REFRESH -> {
                    val remoteKeys: HeroRemoteKeys? = getRemoteKeyClosetToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys: HeroRemoteKeys? = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys: HeroRemoteKeys? = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage

                }

            }

            val response = broutoApi.getAllHeroes(page = page)
            if (response.heroes.isNotEmpty()) {
                broutoDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        heroDao.deleteAllHeroes()
                        heroRemoteKeysDao.deleteAllFromHeroRemoteKeys()
                    }
                    val prevPage = response.prevPage
                    val nextPage = response.nextPage
                    val keys: List<HeroRemoteKeys> = response.heroes.map { hero ->
                        HeroRemoteKeys(
                            id = hero.id,
                            prevPage = prevPage,
                            nextPage = nextPage,
                            lastUpdated = response.lastUpdated
                        )
                    }
                    heroRemoteKeysDao.addAllRemoteKeys(heroRemoteKeys = keys)
                    heroDao.addHeroes(heroes = response.heroes)
                }
            }
            MediatorResult.Success(endOfPaginationReached = response.nextPage == null)
        } catch (e: Exception) {
            Log.e("myTest", "load: ${e.message}")
            return MediatorResult.Error(e)
        }
    }


    private suspend fun getRemoteKeyClosetToCurrentPosition(
        state: PagingState<Int, Hero>
    ): HeroRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                heroRemoteKeysDao.getRemoteKeys(heroId = id)
            }
        }
    }


    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Hero>
    ): HeroRemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { hero ->
            heroRemoteKeysDao.getRemoteKeys(heroId = hero.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Hero>
    ): HeroRemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { hero ->
            heroRemoteKeysDao.getRemoteKeys(heroId = hero.id)
        }
    }

    private fun parseMillis(millis: Long): String {
        val date = Date(millis)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.ROOT)
        return format.format(date)
    }
}