package com.anil.newapp.repository

import androidx.lifecycle.MutableLiveData
import com.anil.newapp.networking.ArticleClient
import com.anil.newapp.networking.message
import com.anil.newapp.persistance.ArticleDao
import com.anil.newapp.persistance.entitiy.Article
import com.skydoves.disneymotions.network.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArticleRepository constructor(
    private val articleClient: ArticleClient,
    private val articleDao: ArticleDao
) {
    suspend fun getArticles(error: (String) -> Unit) = withContext(Dispatchers.IO) {
        val liveData = MutableLiveData<List<Article>>()
        var articles = articleDao.getCachedArticles()
        if (articles.isEmpty()) {
            articleClient.fetchArticles { response ->
                when (response) {
                    is ApiResponse.Success -> response.data?.let {
                        articles = it.articles
                        liveData.postValue(articles)
                        articleDao.insert(articles)
                    }

                    is ApiResponse.Failure.Error -> error(response.message())
                    is ApiResponse.Failure.Exception -> error(response.message())
                }
            }
        }
        liveData.apply { postValue(articles) }
    }
}
