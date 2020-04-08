package com.anil.newapp.ui.viewmodel

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.anil.newapp.persistance.entitiy.Article
import com.anil.newapp.repository.ArticleRepository
import com.anil.newapp.repository.NewsBoundaryCallback
import com.anil.newapp.repository.NewsQueryResult
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers

class NewsViewModel(
    private val articleRepository: ArticleRepository,
    private val newsBoundaryCallback: NewsBoundaryCallback
) : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }
    /*  private val connectivityDisposable by lazy {
          ReactiveNetwork
              .observeInternetConnectivity()
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe({ networkAvailability ->
                  networkState.postValue(networkAvailability)
                  Timber.d("Connectivity state: ON")
              },
                  {
                      networkState.postValue(false)
                      Timber.d("Connectivity state: ERROR")
                  })
      }*/
    private val queryReposLiveData = MutableLiveData<Boolean>()

    private val articleQueryResult by lazy {
        queryReposLiveData.switchMap {
            liveData(Dispatchers.IO) {
                emit(callPagedListQuery())
            }
        }
    }

    val articles: LiveData<PagedList<Article>> by lazy {
        articleQueryResult.switchMap { it.data }
    }
    val articleErrors: LiveData<Boolean> by lazy { articleQueryResult.switchMap { it.articleErrors } }
    val networkError: LiveData<Boolean> by lazy {
        articleQueryResult.switchMap { it.networkErrors }
    }

    private fun callPagedListQuery(): NewsQueryResult {
        val dataSourceFactory = articleRepository.getAllArticlesAsDataSourceOffline()
        val githubErrors = newsBoundaryCallback.newsError
        val networkErrors = newsBoundaryCallback.networkError
        val isRequestInProgressLiveData = newsBoundaryCallback.isRequestInProgressLiveData
        val data = LivePagedListBuilder(
            dataSourceFactory,
            DATABASE_PAGE_SIZE
        ).setBoundaryCallback(newsBoundaryCallback)
            .build()
        return NewsQueryResult(
            data,
            isRequestInProgressLiveData,
            githubErrors,
            networkErrors
        )
    }

    val networkState by lazy { MutableLiveData<Boolean>() }

    init {
        queryReposLiveData.postValue(true)
    }


    override fun onCleared() {
        super.onCleared()
        newsBoundaryCallback.onCleared()
    }

    companion object {
        private const val DATABASE_PAGE_SIZE = 20
    }
}