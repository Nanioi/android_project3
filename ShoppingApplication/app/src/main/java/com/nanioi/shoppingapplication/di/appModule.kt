package com.nanioi.shoppingapplication.di

import com.nanioi.shoppingapplication.data.network.buildOkHttpClient
import com.nanioi.shoppingapplication.data.network.provideGsonConverterFactory
import com.nanioi.shoppingapplication.data.network.provideProductApiService
import com.nanioi.shoppingapplication.data.network.provideProductRetrofit
import org.koin.dsl.module

internal val appModule = module {


    // Repository
    single { provideGsonConverterFactory() }

    single { buildOkHttpClient() }

    single { provideProductRetrofit(get(), get()) }

    single { provideProductApiService(get()) }

}