package com.example.mbroutoapp.data.paging_source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.mbroutoapp.data.local.BroutoDatabase
import com.example.mbroutoapp.data.remote.BroutoApi
import com.example.mbroutoapp.domain.model.Hero
import com.example.mbroutoapp.domain.model.HeroRemoteKeys
import javax.inject.Inject

@ExperimentalPagingApi
class HeroRemoteMediator @Inject constructor(
    private val broutoApi: BroutoApi,
    private val broutoDatabase: BroutoDatabase
):RemoteMediator<Int,Hero>() {

    private val heroDao = broutoDatabase.heroDao()
    private val heroRemoteKeyDao = broutoDatabase.heroRemoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Hero>): MediatorResult {
      return try {
          val page:Int = when(loadType){
              LoadType.REFRESH->{
                  val remoteKeys:HeroRemoteKeys? = getRemoteKeyClosetToCurrentPosition(state)
                  remoteKeys?.nextPage?.minus(1) ?: 1
              }
              LoadType.PREPEND->{
                  val remoteKeys:HeroRemoteKeys? = getRemoteKeyForFirst(state)
                  val prevPage = remoteKeys?.prevPage
                      ?: return MediatorResult.Success(
                          endOfPaginationReached = remoteKeys != null
                      )
                  prevPage
              }
              LoadType.APPEND->{
                  val remoteKeys:HeroRemoteKeys? = getRemoteKeyForLastItem(state)
                  val nextPage = remoteKeys?.nextPage
                      ?: return MediatorResult.Success(
                          endOfPaginationReached = remoteKeys!=null
                      )
                  nextPage

              }

          }

           val response = broutoApi.getAllHeroes(page = page)
           if (response.heroes.isEmpty()){
               broutoDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH){
                        heroDao.deleteAllHeroes()
                        heroRemoteKeyDao.deleteAllFromHeroRemoteKeys()
                    }
                   val prevPage = response.prevPage
                   val nextPage = response.nextPage
                   val keys:List<HeroRemoteKeys> = response.heroes.map {hero->
                       HeroRemoteKeys(
                           id = hero.id,
                           prevPage = prevPage,
                           nextPage = nextPage
                       )
                   }
                   heroRemoteKeyDao.addAllRemoteKeys(heroRemoteKeys = keys)
                   heroDao.addHeroes(heroes = response.heroes)
               }
           }
          MediatorResult.Success(endOfPaginationReached = response.nextPage == null)
       }catch (e:Exception){
           return MediatorResult.Error(e)
       }
    }




    private suspend fun getRemoteKeyClosetToCurrentPosition(
        state: PagingState<Int, Hero>
    ): HeroRemoteKeys?{
       return state.anchorPosition?.let {position->
           state.closestItemToPosition(position)?.id?.let {id->
                heroRemoteKeyDao.getHeroRemoteKeys(heroId = id)
           }
       }
    }


    private suspend fun getRemoteKeyForFirst(
        state: PagingState<Int, Hero>
    ): HeroRemoteKeys? {
            return state.pages.firstOrNull{
                it.data.isNotEmpty()
            }?.data?.firstOrNull()?.let {hero ->  
               heroRemoteKeyDao.getHeroRemoteKeys(heroId = hero.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Hero>
    ): HeroRemoteKeys? {
        return state.pages.lastOrNull{
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let {hero ->
            heroRemoteKeyDao.getHeroRemoteKeys(heroId = hero.id)
        }
    }
}