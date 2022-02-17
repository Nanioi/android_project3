package com.nanioi.todolist

import android.app.Application
import com.nanioi.todolist.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class TodoApplication :Application(){

    override fun onCreate() {
        super.onCreate()
        
        //todo koin trigger
        startKoin { 
            androidLogger(Level.ERROR) //loading
            androidContext(this@TodoApplication)
            modules(appModule)
        }
    }
}