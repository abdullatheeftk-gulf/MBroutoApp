package com.example.mbroutoapp.di

import androidx.paging.ExperimentalPagingApi
import com.example.mbroutoapp.data.local.BroutoDatabase
import com.example.mbroutoapp.data.remote.BroutoApi
import com.example.mbroutoapp.data.repository.RemoteDataSourceImpl
import com.example.mbroutoapp.domain.repository.RemoteDataSource
import com.example.mbroutoapp.util.Constants.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@ExperimentalPagingApi
@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object NetWorkModule {

    @Provides
    @Singleton
    fun provideHttpClient():OkHttpClient{
        return OkHttpClient.Builder()
            .readTimeout(15,TimeUnit.SECONDS)
            .connectTimeout(15,TimeUnit.SECONDS)
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient):Retrofit{
        val contentType = MediaType.get("application/json")
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }


    @Provides
    @Singleton
    fun provideBroutoApi(retrofit: Retrofit):BroutoApi{
      return retrofit.create(BroutoApi::class.java)
    }


    @Provides
    @Singleton
    fun provideRemoteDataSource(
        broutoApi: BroutoApi,
        broutoDatabase: BroutoDatabase
    ):RemoteDataSource{
        return RemoteDataSourceImpl(
            broutoDatabase = broutoDatabase,
            broutoApi = broutoApi
        )
    }
}