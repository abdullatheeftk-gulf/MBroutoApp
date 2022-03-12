package com.example.mbroutoapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hero_remote_key_table")
data class HeroRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id:Int,
    val prevPage:Int?,
    val nextPage:Int?
)
