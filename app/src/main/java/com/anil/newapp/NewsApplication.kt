package com.anil.newapp

import android.app.Application
import com.anil.newapp.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NewsApplication)
            modules(networkModule)
            modules(repositoryModule)
            modules(persistenceModule)
            modules(paginationModule)
            modules(viewModelModule)

        }
    }
}