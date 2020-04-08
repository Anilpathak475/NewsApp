package com.anil.newapp.datasource

import com.anil.newapp.persistance.ArticleDao
import com.anil.newapp.persistance.entitiy.Article

class OfflineDataSource constructor(private val articleDao: ArticleDao) : DataSource {
    override fun getArticles(
        response: (articles: List<Article>) -> Unit,
        error: (error: String) -> Unit
    ) {
        val articles = articleDao.getCachedArticles()
        when {
            articles.isEmpty() -> {
                error("No Cached Articles")
            }
            else -> {
                response(articles)
            }
        }
    }

    fun saveArticles(articles: List<Article>) = articleDao.insert(articles)
}