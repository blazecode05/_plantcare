package com.example.plantcare.Api

import com.example.plantcare.DataModel.NewsData
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    //https://newsapi.org/v2/everything?q=plant&apiKey=your_api_key

    @GET("v2/everything")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String
    ): NewsData
}