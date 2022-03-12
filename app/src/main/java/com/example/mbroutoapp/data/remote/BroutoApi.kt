package com.example.mbroutoapp.data.remote

import com.example.mbroutoapp.domain.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BroutoApi {

    @GET("/brouto/heroes")
    suspend fun getAllHeroes(@Query("page") page:Int=1):ApiResponse

    @GET("/brouto/heroes/search")
    suspend fun searchHeroes(@Query("name") name:String):ApiResponse
}