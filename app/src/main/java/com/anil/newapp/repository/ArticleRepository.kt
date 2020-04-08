package com.anil.newapp.repository

import androidx.paging.DataSource
import com.anil.newapp.persistance.entitiy.Article
import io.reactivex.Completable
import io.reactivex.Single

interface ArticleRepository {
    fun getArticlesOnline(page: Int): Single<List<Article>>
    fun getArticlesOffline(): Single<List<Article>>
    fun saveOffline(articles: List<Article>): Completable
    fun getAllArticlesAsDataSourceOffline(): DataSource.Factory<Int, Article>
    fun clearAllData()
}