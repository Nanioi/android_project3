package com.nanioi.todolist.di

import com.nanioi.todolist.data.repository.TestToDoRepository
import com.nanioi.todolist.data.repository.ToDoRepository
import com.nanioi.todolist.domain.todo.*
import com.nanioi.todolist.presentation.list.ListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appTestModule = module {

    //ViewModel
    viewModel{ ListViewModel(get(), get(),get()) }

    //UseCase
    factory{ GetToDoListUseCase(get())}
    factory{ InsertToDoListUseCase(get())}
    factory { UpdateToDoUseCase(get()) }
    factory { GetToDoItemUseCase(get()) }
    factory { DeleteAllToDoItemUseCase(get()) }

    //Repository
    single<ToDoRepository> {TestToDoRepository()}
}