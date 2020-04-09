package com.anil.newapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anil.newapp.persistance.AppDatabase
import com.anil.newapp.persistance.ArticleDao
import com.anil.newapp.persistance.entitiy.Article
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@RunWith(AndroidJUnit4::class)
class LocalDataSourceTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var articleDao: ArticleDao
    private lateinit var db: AppDatabase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        clearAllMocks()
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }

        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        articleDao = db.articleDao()
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }

    @After
    fun tearDown() {
        db.close()
        RxJavaPlugins.reset()
    }

    @Test
    fun `cached article retrieve successfully `() {
        val id = UUID.randomUUID()
        val title = "Dummy Article"
        articleDao.insert(
            Article(
                title = ""
            )
        )
        val testObserver = articleDao.getCachedArticles().test().await()
        testObserver.assertNoErrors()
        testObserver.assertComplete()
    }
}