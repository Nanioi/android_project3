package com.nanioi.shoppingapplication.di

import com.nanioi.shoppingapplication.data.db.provideDB
import com.nanioi.shoppingapplication.data.db.provideToDoDao
import com.nanioi.shoppingapplication.data.network.buildOkHttpClient
import com.nanioi.shoppingapplication.data.network.provideGsonConverterFactory
import com.nanioi.shoppingapplication.data.network.provideProductApiService
import com.nanioi.shoppingapplication.data.network.provideProductRetrofit
import com.nanioi.shoppingapplication.data.preference.PreferenceManager
import com.nanioi.shoppingapplication.presentation.detail.ProductDetailViewModel
import com.nanioi.shoppingapplication.presentation.list.ProductListViewModel
import com.nanioi.shoppingapplication.presentation.main.MainViewModel
import com.nanioi.shoppingapplication.presentation.profile.ProfileViewModel
import com.nanioi.shoppingapplication.data.repository.DefaultProductRepository
import com.nanioi.shoppingapplication.data.repository.ProductRepository
import com.nanioi.shoppingapplication.domain.product.*
import com.nanioi.shoppingapplication.domain.product.DeleteOrderedProductListUseCase
import com.nanioi.shoppingapplication.domain.product.GetOrderedProductListUseCase
import com.nanioi.shoppingapplication.domain.product.GetProductItemUseCase
import com.nanioi.shoppingapplication.domain.product.GetProductListUseCase
import com.nanioi.shoppingapplication.domain.product.OrderProductItemUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appModule = module {

    // Coroutines Dispatcher
    single { Dispatchers.Main }
    single { Dispatchers.IO }

    // ViewModel
    viewModel { MainViewModel() }
    viewModel { ProductListViewModel(get()) }
    viewModel { ProfileViewModel(get(), get(), get()) }
    viewModel { (productId: Long) -> ProductDetailViewModel(productId, get(), get()) }


    // UseCase
    factory { GetProductListUseCase(get()) }
    factory { GetOrderedProductListUseCase(get()) }
    factory { GetProductItemUseCase(get()) }
    factory { OrderProductItemUseCase(get()) }
    factory { DeleteOrderedProductListUseCase(get()) }

    //Repositories

    // 해당 repository가 인터페이스 타입으로 주입, defaultproductRepository가 인스턴스로 주입
    // 서로의 의존성에 대해 디커플링 구현
    single<ProductRepository> { DefaultProductRepository(get(), get(), get()) }

    single { provideGsonConverterFactory() }

    single { buildOkHttpClient() }

    single { provideProductRetrofit(get(), get()) }

    single { provideProductApiService(get()) }

    single { PreferenceManager(androidContext()) }


    // Database
    single { provideDB(androidApplication()) }
    single { provideToDoDao(get()) }
}