package com.nanioi.todolist.domain.todo

import com.nanioi.todolist.data.repository.ToDoRepository
import com.nanioi.todolist.domain.UseCase

internal class DeleteToDoItemUseCase(
    private val toDoRepository: ToDoRepository
): UseCase {

    suspend operator fun invoke(id: Long) :Boolean {
        return toDoRepository.deleteToDoItem(id)
    }

}
