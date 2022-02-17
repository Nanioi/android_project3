package com.nanioi.todolist.data.repository

import com.nanioi.todolist.data.entity.ToDoEntity


class TestToDoRepository: ToDoRepository {

    //안드로이드 로컬캐싱 X - 메모리 캐싱 용도
    private val toDoList: MutableList<ToDoEntity> = mutableListOf()

    override suspend fun getToDoList(): List<ToDoEntity> {
        return toDoList
    }

    override suspend fun insertToDoItem(todoItem: ToDoEntity):Long {
        this.toDoList.add(todoItem)
        return todoItem.id
    }

    override suspend fun insertToDoList(toDoList: List<ToDoEntity>) {
        this.toDoList.addAll(toDoList)
    }

    override suspend fun updateTodoItem(toDoEntity: ToDoEntity) : Boolean{
        val foundTodoEntity = toDoList.find{it.id == toDoEntity.id}
        return if (foundTodoEntity == null) {
            false
        }
        else{
            this.toDoList[toDoList.indexOf(foundTodoEntity)] = toDoEntity
            true
        }
    }

    override suspend fun getToDoItem(id: Long): ToDoEntity? {
        return toDoList.find{ it.id == id}
    }

    override suspend fun deleteAll() {
        toDoList.clear()
    }

    override suspend fun deleteToDoItem(id: Long): Boolean {
        val foundTodoEntity = toDoList.find{it.id == id}
        return if (foundTodoEntity == null) {
            false
        }
        else{
            this.toDoList.removeAt(toDoList.indexOf(foundTodoEntity))
            true
        }
    }

}
