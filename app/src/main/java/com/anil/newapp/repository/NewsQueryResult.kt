package com.anil.newapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.anil.newapp.persistance.entitiy.Article

data class NewsQueryResult(
    val data: LiveData<PagedList<Article>>,
    val isRequestInProgressLiveData: LiveData<Boolean>,
    val articleErrors: LiveData<Boolean>,
    val networkErrors: LiveData<Boolean>
)
