package com.anil.newapp.repository

import androidx.paging.DataSource
import com.anil.newapp.datasource.LocalDataSource
import com.anil.newapp.datasource.RemoteDataSource
import com.anil.newapp.persistance.entitiy.Article
import io.reactivex.Completable
import io.reactivex.Single

class ArticleRepositoryImpl constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : ArticleRepository {

    override fun getArticlesOnline(page: Int): Single<List<Article>> {
        return remoteDataSource.getArticles(page).map { it.articles }
    }

    override fun getArticlesOffline(): Single<List<Article>> {
        return localDataSource.getArticles()
    }

    override fun saveOffline(articles: List<Article>): Completable {
        return Completable.fromCallable { localDataSource.saveArticles(articles) }
    }

    override fun getAllArticlesAsDataSourceOffline(): DataSource.Factory<Int, Article> {
        return localDataSource.getAllArticlesAsDataSourceOffline()
    }

    override fun clearAllData() {
        localDataSource.deleteArticles()
    }
}
