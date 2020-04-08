package com.anil.newapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.anil.newapp.base.LiveCoroutinesViewModel
import com.anil.newapp.persistance.entitiy.Article
import com.anil.newapp.repository.ArticleRepository
import com.anil.newapp.repository.NewsBoundaryCallback
import com.anil.newapp.repository.NewsQueryResult

class NewsViewModel(
    private val articleRepository: ArticleRepository,
    private val newsBoundaryCallback: NewsBoundaryCallback
) : LiveCoroutinesViewModel() {

    private val _queryReposLiveData = MutableLiveData<Boolean>()

    private val _repoResult by lazy {
        Transformations.map(_queryReposLiveData) {
            callPagedListQuery()
        }
    }

    val repos: LiveData<PagedList<Article>> by lazy {
        Transformations.switchMap(_repoResult) { it.data }
    }
    val githubError: LiveData<Boolean> by lazy { Transformations.switchMap(_repoResult) { it.articleErrors } }
    val networkError: LiveData<Boolean> =
        Transformations.switchMap(_repoResult) { it.networkErrors }


    private fun callPagedListQuery(): NewsQueryResult {
        val dataSourceFactory = articleRepository.getAllArticlesAsDataSourceOffline()
        val githubErrors = newsBoundaryCallback.newsError
        val networkErrors = newsBoundaryCallback.networkError
        val isRequestInProgressLiveData = newsBoundaryCallback.isRequestInProgressLiveData
        val data = LivePagedListBuilder(
            dataSourceFactory,
            DATABASE_PAGE_SIZE
        )
            .setBoundaryCallback(newsBoundaryCallback)
            .build()
        return NewsQueryResult(
            data,
            isRequestInProgressLiveData,
            githubErrors,
            networkErrors
        )
    }

    override fun onCleared() {
        super.onCleared()
        newsBoundaryCallback.onCleared()
    }

    companion object {
        private const val DATABASE_PAGE_SIZE = 20
    }
}