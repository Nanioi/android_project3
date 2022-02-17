package com.nanioi.todolist.di

import com.nanioi.todolist.data.repository.TestToDoRepository
import com.nanioi.todolist.data.repository.ToDoRepository
import com.nanioi.todolist.domain.todo.*
import com.nanioi.todolist.presentation.detail.DetailMode
import com.nanioi.todolist.presentation.detail.DetailViewModel
import com.nanioi.todolist.presentation.list.ListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appTestModule = module {

    //ViewModel
    viewModel{ ListViewModel(get(), get(),get()) }
    viewModel { (detailMode: DetailMode, id: Long) ->
        DetailViewModel(detailMode, id, get(), get(), get(), get()) }

    //UseCase
    factory{ GetToDoListUseCase(get())}
    factory{ InsertToDoListUseCase(get())}
    factory { InsertToDoItemUseCase(get())}
    factory { UpdateToDoUseCase(get()) }
    factory { GetToDoItemUseCase(get()) }
    factory { DeleteToDoItemUseCase(get()) }
    factory { DeleteAllToDoItemUseCase(get()) }

    //Repository
    single<ToDoRepository> {TestToDoRepository()}
}