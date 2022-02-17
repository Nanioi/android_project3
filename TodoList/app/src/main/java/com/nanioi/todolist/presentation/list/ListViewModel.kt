package com.nanioi.todolist.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nanioi.todolist.data.entity.ToDoEntity
import com.nanioi.todolist.domain.todo.GetToDoListUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


/**
 * 필요한 useCase
 * 1. GetTodoListUseCase - todolist 얻어올 수 있는
 * 2. UpdateTodoListUseCase
 * 3. DeleteAllTodoListUseCase
 */
internal class ListViewModel(
    private val getToDoListUseCase: GetToDoListUseCase
) :ViewModel() {

    private var _toDoListLiveData = MutableLiveData<List<ToDoEntity>>()
    val toDoListLiveData: LiveData<List<ToDoEntity>> = _toDoListLiveData

    //viewmodel에서 특정함수에 동작이 마무리 될때까지 코루틴 블록에서 관리를 하다가 끝나 해제를 해줄 수 있도록 viewModelScope 사용
    fun fetchData(): Job = viewModelScope.launch {
        _toDoListLiveData.postValue(getToDoListUseCase()!!)
    }

}