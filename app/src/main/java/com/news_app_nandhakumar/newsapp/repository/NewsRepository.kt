package com.news_app_nandhakumar.newsapp.repository

import com.news_app_nandhakumar.newsapp.constant.Iconstant
import com.news_app_nandhakumar.newsapp.retrofit.NetworkApi
import javax.inject.Inject

class NewsRepository @Inject constructor(private val networkApi: NetworkApi) {
    suspend fun getNews() = networkApi.getNews(Iconstant.apiKey)
}