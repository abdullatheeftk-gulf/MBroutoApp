package com.example.mbroutoapp.domain.use_cases.get_all_heroes

import androidx.paging.PagingData
import com.example.mbroutoapp.data.repository.Repository
import com.example.mbroutoapp.domain.model.Hero
import kotlinx.coroutines.flow.Flow

class GetAllHeroesUseCase(
    private val repository: Repository
) {

    operator fun invoke():Flow<PagingData<Hero>>{
       return repository.getAllHeroes()
    }
}