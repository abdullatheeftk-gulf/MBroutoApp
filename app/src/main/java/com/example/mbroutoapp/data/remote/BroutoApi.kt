package com.example.mbroutoapp.data.remote

import com.example.mbroutoapp.domain.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BroutoApi {

    @GET("/")
    suspend fun testFun():String

    @GET("/boruto/heroes")
    suspend fun getAllHeroes(@Query("page") page:Int=1):ApiResponse

    @GET("/boruto/heroes/search")
    suspend fun searchHeroes(@Query("name") name:String):ApiResponse
}