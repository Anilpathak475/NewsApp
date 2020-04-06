package com.anil.newapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.anil.newapp.networking.Resource
import com.anil.newapp.repository.NewsRepository
import kotlinx.coroutines.Dispatchers

class NewsViewModel(
    private val newsRepository: NewsRepository
):ViewModel() {

    val articleResponse by lazy {
        liveData(Dispatchers.Main) {
            emit(Resource.loading(null))
            emit(newsRepository.loadTopHeadlines())
        }
    }
}