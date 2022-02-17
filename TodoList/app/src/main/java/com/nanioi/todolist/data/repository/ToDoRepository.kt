package com.nanioi.todolist.data.repository

import com.nanioi.todolist.data.entity.ToDoEntity

/**
 * 1. insertTodoList
 * 2. getTodoList
 * 3. updateTodoItem
 */
interface ToDoRepository {

    suspend fun getToDoList(): List<ToDoEntity>

    suspend fun insertToDoItem(toDoEntity: ToDoEntity) : Long

    suspend fun insertToDoList(toDoList: List<ToDoEntity>)

    suspend fun updateTodoItem(toDoEntity: ToDoEntity):Boolean

    suspend fun getToDoItem(id: Long): ToDoEntity?

    suspend fun deleteAll()

    suspend fun deleteToDoItem(id: Long) : Boolean

}
