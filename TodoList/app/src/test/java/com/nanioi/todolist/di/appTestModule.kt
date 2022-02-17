package com.nanioi.todolist.di

import com.nanioi.todolist.data.repository.TestToDoRepository
import com.nanioi.todolist.data.repository.ToDoRepository
import com.nanioi.todolist.domain.todo.GetToDoListUseCase
import com.nanioi.todolist.domain.todo.InsertToDoListUseCase
import com.nanioi.todolist.presentation.list.ListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appTestModule = module {

    //ViewModel
    viewModel{ ListViewModel(get()) }

    //UseCase
    factory{ GetToDoListUseCase(get())}
    factory{ InsertToDoListUseCase(get())}

    //Repository
    single<ToDoRepository> {TestToDoRepository()}
}