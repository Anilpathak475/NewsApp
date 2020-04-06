package com.anil.newapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.anil.newapp.base.LiveCoroutinesViewModel
import com.anil.newapp.repository.ArticleRepository

class NewsViewModel(
    private val articleRepository: ArticleRepository
) : LiveCoroutinesViewModel() {

    val error = MutableLiveData<String>()
    val articleResponse by lazy {
        launchOnViewModelScope {
            articleRepository.getArticles { error.postValue(it) }
        }
    }
}