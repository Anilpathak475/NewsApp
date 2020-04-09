package com.anil.newapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anil.newapp.datasource.LocalDataSource
import com.anil.newapp.datasource.RemoteDataSource
import com.anil.newapp.networking.NewsApi
import com.anil.newapp.persistance.AppDatabase
import com.anil.newapp.persistance.ArticleDao
import com.anil.newapp.persistance.InternalErrorConverter
import com.anil.newapp.persistance.entitiy.Article
import com.anil.newapp.repository.ArticleRepositoryImpl
import com.anil.newapp.repository.NewsBoundaryCallback
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewsBoundaryCallbackTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var articleDao: ArticleDao
    private lateinit var db: AppDatabase

    private lateinit var articleRepository: ArticleRepositoryImpl
    private lateinit var localDataSource: LocalDataSource
    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var newsApi: NewsApi
    private lateinit var internalErrorConverter: InternalErrorConverter

    private lateinit var reposBoundaryCallback: NewsBoundaryCallback

    @Before
    fun setUp() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }

        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        articleDao = db.articleDao()
        newsApi = mockk()
        internalErrorConverter = mockk()
        localDataSource = LocalDataSource(articleDao, mockk())
        remoteDataSource = RemoteDataSource(newsApi)
        articleRepository = ArticleRepositoryImpl(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource
        )
        reposBoundaryCallback = NewsBoundaryCallback(articleRepository, internalErrorConverter)
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }

    @After
    fun tearDown() {
        db.close()
        RxJavaPlugins.reset()
    }

    @Test
    fun `retrieved data from online api should be properly saved offline`() {
        val newListOfArticles = mutableListOf(
            Article(
                id = 1,
                title = "first",
                description = "first description"
            ),
            Article(
                id = 2,
                title = "second",
                description = "second description"
            )
        )

        every { articleRepository.getArticlesOffline() } returns Single.just(
            newListOfArticles
        )
        reposBoundaryCallback.onZeroItemsLoaded()
        val testObserver = articleDao.getCachedArticles().test().await()
        testObserver.assertNoErrors()
        testObserver.assertComplete()
        val values = testObserver.values()[0]
        testObserver.assertValues(newListOfArticles)
        Truth.assertThat(values).hasSize(2)
        Truth.assertThat(values).containsExactlyElementsIn(
            newListOfArticles
        )
    }
}