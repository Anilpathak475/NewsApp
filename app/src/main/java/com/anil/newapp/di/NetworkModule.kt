package com.anil.newapp.di

import com.anil.newapp.datasource.OnlineDataSource
import com.anil.newapp.networking.AuthInterceptor
import com.anil.newapp.networking.NewsApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val networkModule = module {
    single { AuthInterceptor() }
    single { provideOkHttpClient(get(), get()) }
    single { provideNewsApi(get()) }
    single { provideLoggingInterceptor() }
    single { provideRetrofit(get()) }
    single { provideNewsClient(get()) }
    single { OnlineDataSource(get()) }

}

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl("https://newsapi.org/").client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

private fun provideOkHttpClient(
    authInterceptor: AuthInterceptor,
    loggingInterceptor: HttpLoggingInterceptor
): OkHttpClient {
    return OkHttpClient().newBuilder().addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor).build()
}

private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val logger = HttpLoggingInterceptor()
    logger.level = HttpLoggingInterceptor.Level.BASIC
    return logger
}

private fun provideNewsApi(retrofit: Retrofit): NewsApi = retrofit.create(NewsApi::class.java)
private fun provideNewsClient(newsApi: NewsApi): ArticleClient = ArticleClient(newsApi)