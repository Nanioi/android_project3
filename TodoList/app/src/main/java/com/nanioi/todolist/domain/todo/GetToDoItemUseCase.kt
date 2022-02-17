package com.nanioi.todolist.domain.todo

import com.nanioi.todolist.data.entity.ToDoEntity
import com.nanioi.todolist.data.repository.ToDoRepository
import com.nanioi.todolist.domain.UseCase


internal class GetToDoItemUseCase(
    private val toDoRepository: ToDoRepository
): UseCase {

    suspend operator fun invoke(id: Long): ToDoEntity? {
        return toDoRepository.getToDoItem(id)
    }

}
