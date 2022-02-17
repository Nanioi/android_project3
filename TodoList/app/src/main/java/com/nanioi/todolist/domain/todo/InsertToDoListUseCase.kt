package com.nanioi.todolist.domain.todo

import com.nanioi.todolist.data.entity.ToDoEntity
import com.nanioi.todolist.data.repository.ToDoRepository
import com.nanioi.todolist.domain.UseCase

internal class InsertToDoListUseCase(
    private val toDoRepository: ToDoRepository
): UseCase {

    //함수호출 가능하도록 operator제공
    suspend operator fun invoke(toDoList: List<ToDoEntity>) {
        return toDoRepository.insertToDoList(toDoList)
    }

}
