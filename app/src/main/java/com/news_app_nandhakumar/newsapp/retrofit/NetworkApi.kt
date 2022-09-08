package com.news_app_nandhakumar.newsapp.retrofit

import com.news_app_nandhakumar.newsapp.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApi {
    @GET("/v2/sources")
    suspend fun getNews(@Query("apiKey") apikey: String): NewsResponse
}