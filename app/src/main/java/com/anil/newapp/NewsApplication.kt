package com.anil.newapp

import android.app.Application
import com.anil.newapp.di.networkModule
import com.anil.newapp.di.repositoryModule
import com.anil.newapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NewsApplication)
            modules(networkModule)
            modules(viewModelModule)
            modules(repositoryModule)
        }
    }
}