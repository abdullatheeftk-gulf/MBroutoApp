package com.example.mbroutoapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mbroutoapp.data.local.dao.HeroDao
import com.example.mbroutoapp.data.local.dao.HeroRemoteKeyDao
import com.example.mbroutoapp.domain.model.Hero
import com.example.mbroutoapp.domain.model.HeroRemoteKey


@Database(entities = [Hero::class,HeroRemoteKey::class], version = 1)
@TypeConverters(DataBaseConverter::class)
abstract class BroutoDatabase:RoomDatabase() {
    abstract fun heroDao():HeroDao
    abstract fun heroRemoteKeyDao():HeroRemoteKeyDao
}