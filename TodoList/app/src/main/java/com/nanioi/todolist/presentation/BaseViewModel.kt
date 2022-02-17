package com.nanioi.todolist.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job

internal abstract class BaseViewModel: ViewModel() {

    abstract fun fetchData(): Job

}
// 공통으로 쓸 수 있는 fetchData 함수 빼기