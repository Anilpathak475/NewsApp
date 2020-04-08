package com.anil.newapp.datasource

import android.content.SharedPreferences
import androidx.paging.DataSource
import com.anil.newapp.persistance.ArticleDao
import com.anil.newapp.persistance.entitiy.Article
import io.reactivex.Completable
import io.reactivex.Single

class LocalDataSource constructor(
    private val articleDao: ArticleDao,
    private val sharedPreferencesEditor: SharedPreferences.Editor
) {
    fun getArticles(): Single<List<Article>> {
        return articleDao.getCachedArticles()
    }

    fun saveArticles(articles: List<Article>): Completable {
        articleDao.insert(articles)
        sharedPreferencesEditor.apply {
            putLong(LAST_SYNC, System.currentTimeMillis())
        }
        return Completable.fromCallable { articleDao.insert(articles) }
    }

    fun getAllArticlesAsDataSourceOffline(): DataSource.Factory<Int, Article> {
        return articleDao.getAllArticlesAsDataSourceOffline()
    }

    fun deleteArticles() {
        articleDao.deleteAllArticles()
    }

    companion object {
        const val LAST_SYNC = "lastSync"
    }
}