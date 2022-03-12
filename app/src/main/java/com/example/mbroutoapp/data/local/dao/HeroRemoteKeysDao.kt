package com.example.mbroutoapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mbroutoapp.domain.model.HeroRemoteKeys

@Dao
interface HeroRemoteKeysDao {

    @Query("SELECT * FROM hero_remote_key_table WHERE id = :id")
    suspend fun getHeroRemoteKeys(heroId:Int):HeroRemoteKeys?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(heroRemoteKeys:List<HeroRemoteKeys>)

    @Query("DELETE FROM hero_remote_key_table")
    suspend fun deleteAllFromHeroRemoteKeys()
}