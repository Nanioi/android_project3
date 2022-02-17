package com.nanioi.todolist.data.repository

import com.nanioi.todolist.data.entity.ToDoEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultToDoRepository: ToDoRepository {

    override suspend fun getToDoList(): List<ToDoEntity> {
        TODO()
    }


    override suspend fun insertToDoList(toDoList: List<ToDoEntity>) {
        TODO()
    }


}
