package com.example.mbroutoapp.di

import android.content.Context
import androidx.room.Room
import com.example.mbroutoapp.data.local.BroutoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataBase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        BroutoDatabase::class.java,
        "brouto_database"
    ).build()
}