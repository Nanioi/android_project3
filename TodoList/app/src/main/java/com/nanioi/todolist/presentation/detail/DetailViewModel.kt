package com.nanioi.todolist.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nanioi.todolist.data.entity.ToDoEntity
import com.nanioi.todolist.domain.todo.DeleteToDoItemUseCase
import com.nanioi.todolist.domain.todo.GetToDoItemUseCase
import com.nanioi.todolist.domain.todo.InsertToDoItemUseCase
import com.nanioi.todolist.domain.todo.UpdateToDoUseCase
import com.nanioi.todolist.presentation.BaseViewModel
import kotlinx.coroutines.launch
import java.lang.Exception

internal class DetailViewModel(
    var detailMode: DetailMode,
    var id: Long = -1, //잘못된 같이 들어갈 수 있으니 -1로 초기화
    private val getToDoItemUseCase: GetToDoItemUseCase,
    private val deleteToDoItemUseCase: DeleteToDoItemUseCase,
    private val updateToDoUseCase: UpdateToDoUseCase,
    private val insertToDoItemUseCase: InsertToDoItemUseCase
) : BaseViewModel() {

    private var _toDoDetailLiveData = MutableLiveData<ToDoDetailState>(ToDoDetailState.UnInitialized)
    val toDoDetailLiveData: LiveData<ToDoDetailState> = _toDoDetailLiveData

    override fun fetchData() = viewModelScope.launch {
        when (detailMode) {
            DetailMode.WRITE -> { //작성모드
                _toDoDetailLiveData.postValue(ToDoDetailState.Write)
            }
            DetailMode.DETAIL -> {
                _toDoDetailLiveData.postValue(ToDoDetailState.Loading)
                try {
                    getToDoItemUseCase(id)?.let {
                        _toDoDetailLiveData.postValue(ToDoDetailState.Success(it))
                    } ?: kotlin.run {
                        _toDoDetailLiveData.postValue(ToDoDetailState.Error)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    _toDoDetailLiveData.postValue(ToDoDetailState.Error)
                }
            }
        }
    }

    fun deleteToDo() = viewModelScope.launch {
        _toDoDetailLiveData.postValue(ToDoDetailState.Loading)
        try {
//            deleteToDoItemUseCase(id)
//            _toDoDetailLiveData.postValue(ToDoDetailState.Delete)

            if(deleteToDoItemUseCase(id)){
                _toDoDetailLiveData.postValue(ToDoDetailState.Delete)
            }else{
                _toDoDetailLiveData.postValue(ToDoDetailState.Error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _toDoDetailLiveData.postValue(ToDoDetailState.Error)
        }
    }

    fun setModifyMode() = viewModelScope.launch {
        _toDoDetailLiveData.postValue(ToDoDetailState.Modify)
    }

    // 쓰는 것과 update가 동일한 로직을 사용, mode에 따라 다르게 처리
    fun writeToDo(title: String, description: String) = viewModelScope.launch {
        _toDoDetailLiveData.postValue(ToDoDetailState.Loading)
        when (detailMode) {
            DetailMode.WRITE -> {
                try {
                    val toDoEntity = ToDoEntity(title = title, description =  description)
                    id = insertToDoItemUseCase(toDoEntity) // 추가된 id로 바꾸기
                    _toDoDetailLiveData.postValue(ToDoDetailState.Success(toDoEntity))
                    detailMode = DetailMode.DETAIL
                } catch (e: Exception) {
                    e.printStackTrace()
                    _toDoDetailLiveData.postValue(ToDoDetailState.Error)
                }
            }
            DetailMode.DETAIL -> { // update로직
                try {
                    getToDoItemUseCase(id)?.let { // 기존에 데이터가 있는 경우 copy후 넣어줌
                        val updateToDoEntity = it.copy(title = title, description = description)
                        updateToDoUseCase(updateToDoEntity)
                        _toDoDetailLiveData.postValue(ToDoDetailState.Success(updateToDoEntity))
                    } ?: kotlin.run { // id가 없는 경우
                        _toDoDetailLiveData.postValue(ToDoDetailState.Error)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    _toDoDetailLiveData.postValue(ToDoDetailState.Error)
                }
            }
        }
    }

}
