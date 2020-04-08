package com.anil.newapp.repository

import androidx.lifecycle.MutableLiveData
import com.anil.newapp.datasource.OfflineDataSource
import com.anil.newapp.datasource.OnlineDataSource
import com.anil.newapp.persistance.entitiy.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArticleRepository constructor(
    private val onlineDataSource: OnlineDataSource,
    private val offlineDataSource: OfflineDataSource
) {
    suspend fun getArticles(error: (String) -> Unit) = withContext(Dispatchers.IO) {
        val liveData = MutableLiveData<List<Article>>()
        offlineDataSource.getArticles({
            liveData.apply { postValue(it) }
        }, {
            onlineDataSource.getArticles({
                liveData.postValue(it)
                offlineDataSource.saveArticles(it)
            }, {
                error(it)
            })
        })
        liveData
    }
}
