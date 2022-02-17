package com.nanioi.todolist.presentation.list

import com.nanioi.todolist.data.entity.ToDoEntity


sealed class ToDoListState {

    object UnInitialized: ToDoListState()

    object Loading: ToDoListState()

    data class Suceess(
        val toDoList: List<ToDoEntity>
    ): ToDoListState()

    object Error: ToDoListState()

}

//sealed 클래스 이용 - todoListState를 상속을 받는 object,data class 를 구성할 수 있도록 만들어줌
// 1. 초기화 되어있지 않을 때
// 2. 로딩중일때
// 3. 성공해서 데이터 불러와질때
// 4. 에러인 케이스