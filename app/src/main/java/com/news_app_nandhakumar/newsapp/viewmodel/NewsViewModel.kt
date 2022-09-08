package com.news_app_nandhakumar.newsapp.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.news_app_nandhakumar.newsapp.R
import com.news_app_nandhakumar.newsapp.repository.NewsRepository
import com.news_app_nandhakumar.newsapp.utlis.NetworkStatus
import com.news_app_nandhakumar.newsapp.utlis.Resource
import kotlinx.coroutines.Dispatchers

class NewsViewModel @ViewModelInject constructor(private val newsRepository: NewsRepository, private val networkStatus: NetworkStatus, private val application: Application) : ViewModel() {

    val progressbar = MutableLiveData<Boolean>()


    fun getNews() = liveData(Dispatchers.IO) {
        try {
            if (!networkStatus.isConnectedInternet())
                emit(Resource.error(data = null, message = application.resources.getString(R.string.no_internet_connection)))
            else {
                progressbar.postValue(true)
                emit(Resource.success(data = newsRepository.getNews()))
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: application.resources.getString(R.string.something_went_wrong)))
        }
    }


}