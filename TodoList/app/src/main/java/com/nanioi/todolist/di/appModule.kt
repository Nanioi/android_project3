package com.nanioi.todolist.di

import android.content.Context
import androidx.room.Room
import com.nanioi.todolist.data.local.db.ToDoDatabase
import com.nanioi.todolist.data.repository.DefaultToDoRepository
import com.nanioi.todolist.data.repository.ToDoRepository
import com.nanioi.todolist.domain.todo.*
import com.nanioi.todolist.domain.todo.DeleteAllToDoItemUseCase
import com.nanioi.todolist.domain.todo.DeleteToDoItemUseCase
import com.nanioi.todolist.domain.todo.GetToDoItemUseCase
import com.nanioi.todolist.domain.todo.GetToDoListUseCase
import com.nanioi.todolist.domain.todo.InsertToDoItemUseCase
import com.nanioi.todolist.domain.todo.InsertToDoListUseCase
import com.nanioi.todolist.domain.todo.UpdateToDoUseCase
import com.nanioi.todolist.presentation.detail.DetailMode
import com.nanioi.todolist.presentation.detail.DetailViewModel
import com.nanioi.todolist.presentation.list.ListViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appModule = module {

    single { Dispatchers.Main }
    single { Dispatchers.IO }

    //ViewModel
    viewModel{ ListViewModel(get(), get(),get()) }
    viewModel { (detailMode: DetailMode, id: Long) ->
        DetailViewModel(detailMode, id, get(), get(), get(), get()) }

    //UseCase
    factory{ GetToDoListUseCase(get()) }
    factory{ InsertToDoListUseCase(get()) }
    factory { InsertToDoItemUseCase(get()) }
    factory { UpdateToDoUseCase(get()) }
    factory { GetToDoItemUseCase(get()) }
    factory { DeleteToDoItemUseCase(get()) }
    factory { DeleteAllToDoItemUseCase(get()) }

    //Repository
    single<ToDoRepository> { DefaultToDoRepository(get(), get()) }

    single { provideDB(androidApplication()) }
    single { provideToDoDao(get()) }
}

internal fun provideDB(context: Context): ToDoDatabase =
    Room.databaseBuilder(context, ToDoDatabase::class.java, ToDoDatabase.DB_NAME).build()

internal fun provideToDoDao(database: ToDoDatabase) = database.toDoDao()
