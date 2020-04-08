package com.anil.newapp.datasource

import androidx.lifecycle.MutableLiveData
import com.anil.newapp.persistance.entitiy.Article
import kotlinx.coroutines.Job

interface DataSource {
    fun getArticles(
        response: (articles: MutableLiveData<List<Article>>) -> Unit,
        error: (error: String) -> Unit
    ): Job
}

enum class SOURCE {
    LOCAL,
    REMOTE
}