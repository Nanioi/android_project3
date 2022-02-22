package com.nanioi.shoppingapplication.di

import com.nanioi.shoppingapplication.data.network.buildOkHttpClient
import com.nanioi.shoppingapplication.data.network.provideGsonConverterFactory
import com.nanioi.shoppingapplication.data.network.provideProductApiService
import com.nanioi.shoppingapplication.data.network.provideProductRetrofit
import com.nanioi.shoppingapplication.domain.product.GetProductItemUseCase
import com.nanioi.shoppingapplication.domain.product.GetProductListUseCase
import com.nanioi.shoppingapplication.presentation.list.ProductListViewModel
import com.nanioi.shoppingapplication.presentation.main.MainViewModel
import com.nanioi.shoppingapplication.presentation.profile.ProfileViewModel
import com.nanioi.shoppingapplication.repository.DefaultProductRepository
import com.nanioi.shoppingapplication.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appModule = module {

    // Coroutines Dispatcher
    single { Dispatchers.Main }
    single { Dispatchers.IO }

    // ViewModel
    viewModel { MainViewModel() }
    viewModel { ProductListViewModel(get()) }
    viewModel { ProfileViewModel() }


    // UseCase
    factory { GetProductItemUseCase(get()) }
    factory { GetProductListUseCase(get()) }

    //Repositories
    single<ProductRepository> { DefaultProductRepository(get(), get()) }
    // 해당 repository가 인터페이스 타입으로 주입, defaultproductRepository가 인스턴스로 주입
    // 서로의 의존성에 대해 디커플링 구현

    single { provideGsonConverterFactory() }

    single { buildOkHttpClient() }

    single { provideProductRetrofit(get(), get()) }

    single { provideProductApiService(get()) }

}