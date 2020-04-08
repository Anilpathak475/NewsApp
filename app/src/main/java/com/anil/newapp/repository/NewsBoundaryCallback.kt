package com.anil.newapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.anil.newapp.persistance.ErrorType
import com.anil.newapp.persistance.InternalErrorConverter
import com.anil.newapp.persistance.entitiy.Article
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class NewsBoundaryCallback constructor(
    private val repository: ArticleRepository,
    private val internalErrorConverter: InternalErrorConverter
) : PagedList.BoundaryCallback<Article>() {

    private val compositeDisposable = CompositeDisposable()

    private var lastRequestedPage = 1

    private val _newsError = MutableLiveData<Boolean>()
    val newsError: LiveData<Boolean> = _newsError

    private val _networkError = MutableLiveData<Boolean>()
    val networkError: LiveData<Boolean> = _networkError

    private var isRequestInProgress = false

    private val _isRequestInProgress = MutableLiveData<Boolean>()
    val isRequestInProgressLiveData: LiveData<Boolean> = _isRequestInProgress
    /**
     * Database returned 0 items. We should query the backend for more items.
     */
    override fun onZeroItemsLoaded() {
        Timber.d("RepoBoundaryCallback: onZeroItemsLoaded")
        requestAndSaveData()
    }

    /**s
     * When all items in the database were loaded, we need to query the backend for more items.
     */
    override fun onItemAtEndLoaded(itemAtEnd: Article) {
        Timber.d("RepoBoundaryCallback: onItemAtEndLoaded")
        requestAndSaveData()
    }

    private fun requestAndSaveData() {
        if (isRequestInProgress) return

        isRequestInProgress = true
        _isRequestInProgress.value = true

        val disposable = repository.getArticlesOnline(
            lastRequestedPage
        )
            .flatMapCompletable { articles ->
                Timber.d("Article Saver. Number of Article saved offline : ${articles.count()}")
                repository.saveOffline(articles)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    lastRequestedPage++
                    isRequestInProgress = false
                    _isRequestInProgress.value = false
                },
                { throwable ->
                    when (internalErrorConverter.convertToGeneralErrorType(throwable)) {
                        ErrorType.SERVER_CONNECTION -> {
                            _networkError.value = true
                        }
                        else -> {
                            _newsError.value = true
                        }
                    }
                    isRequestInProgress = false
                    _isRequestInProgress.value = false
                    Timber.d("Repo Saver. Error : ${throwable.message}")
                }
            )

        compositeDisposable.add(disposable)
    }

    fun onCleared() {
        compositeDisposable.clear()
    }
}
