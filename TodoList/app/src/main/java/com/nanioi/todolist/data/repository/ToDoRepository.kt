package com.nanioi.todolist.data.repository

import com.nanioi.todolist.data.entity.ToDoEntity

/**
 * 1. insertTodoList
 * 2. getTodoList
 *
 */
interface ToDoRepository {

    suspend fun getToDoList(): List<ToDoEntity>

    suspend fun insertToDoList(toDoList: List<ToDoEntity>)

}
