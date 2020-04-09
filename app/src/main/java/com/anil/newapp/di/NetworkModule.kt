package com.anil.newapp.di

import com.anil.newapp.datasource.RemoteDataSource
import com.anil.newapp.networking.AuthInterceptor
import com.anil.newapp.networking.NetworkConfirgBuilder.Companion.provideLoggingInterceptor
import com.anil.newapp.networking.NetworkConfirgBuilder.Companion.provideNewsApi
import com.anil.newapp.networking.NetworkConfirgBuilder.Companion.provideOkHttpClient
import com.anil.newapp.networking.NetworkConfirgBuilder.Companion.provideRetrofit
import org.koin.dsl.module


val networkModule = module {
    single { AuthInterceptor() }
    single { provideOkHttpClient(get(), get()) }
    single { provideNewsApi(get()) }
    single { provideLoggingInterceptor() }
    single { provideRetrofit(get()) }
    single { RemoteDataSource(get()) }
}

