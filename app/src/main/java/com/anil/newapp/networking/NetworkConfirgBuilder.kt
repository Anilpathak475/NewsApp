package com.anil.newapp.networking

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

sealed class NetworkConfirgBuilder {
    companion object {
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder().baseUrl("https://newsapi.org/").client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(MoshiConverterFactory.create(moshiConfiguration()))
                .build()
        }

        private fun moshiConfiguration(): Moshi {
            return Moshi.Builder()
                .build()
        }

        fun provideOkHttpClient(
            authInterceptor: AuthInterceptor,
            loggingInterceptor: HttpLoggingInterceptor
        ): OkHttpClient {
            return OkHttpClient().newBuilder().addInterceptor(authInterceptor)
                .addInterceptor(loggingInterceptor).build()
        }

        fun provideLoggingInterceptor(): HttpLoggingInterceptor {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC
            return logger
        }

        fun provideNewsApi(retrofit: Retrofit): NewsApi = retrofit.create(NewsApi::class.java)
    }
}