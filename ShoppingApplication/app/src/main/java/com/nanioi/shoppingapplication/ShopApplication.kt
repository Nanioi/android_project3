package com.nanioi.shoppingapplication

import android.app.Application
import com.nanioi.shoppingapplication.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ShopApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger(Level.ERROR)
            androidContext(this@ShopApplication)
            modules(appModule)
        }
    }

}
// 실행 시 오버라이드 하도록 Manifest에 application 추가
// application onCreate 실행 시점에 appModule 주입된다.
