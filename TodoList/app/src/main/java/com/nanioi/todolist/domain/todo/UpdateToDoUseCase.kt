package com.nanioi.todolist.domain.todo

import com.nanioi.todolist.data.entity.ToDoEntity
import com.nanioi.todolist.data.repository.ToDoRepository
import com.nanioi.todolist.domain.UseCase


internal class UpdateToDoUseCase(
    private val toDoRepository: ToDoRepository
): UseCase {

    suspend operator fun invoke(todoItem: ToDoEntity) : Boolean {
        return toDoRepository.updateTodoItem(todoItem)
    }

}
