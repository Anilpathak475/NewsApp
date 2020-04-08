package com.anil.newapp.di

import com.anil.newapp.ui.viewmodel.NewsViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { NewsViewModel(get(), get()) }
}
