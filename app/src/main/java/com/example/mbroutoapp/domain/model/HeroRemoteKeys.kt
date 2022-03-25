package com.example.mbroutoapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hero_remote_keys_table")
data class HeroRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id:Int,
    val prevPage:Int?,
    val nextPage:Int?,
    val lastUpdated: Long?
)
