package com.example.mbroutoapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mbroutoapp.domain.model.Hero
import kotlinx.coroutines.flow.Flow

@Dao
interface HeroDao {

    @Query("SELECT * FROM hero_table ORDER BY id ASC")
    fun getAllHeroes():PagingSource<Int,Hero>

    @Query("SELECT * FROM hero_table WHERE id = :id")
    fun getSelectedHero(id:Int):Flow<Hero>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addHeroes(heroes:List<Hero>)


    @Query("DELETE FROM hero_table")
    fun deleteAllHeroes()
}