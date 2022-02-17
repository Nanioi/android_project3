package com.nanioi.todolist.domain.todo

import com.nanioi.todolist.data.repository.ToDoRepository
import com.nanioi.todolist.domain.UseCase

internal class DeleteAllToDoItemUseCase(
    private val toDoRepository: ToDoRepository
): UseCase {

    suspend operator fun invoke() {
        return toDoRepository.deleteAll()
    }

}
