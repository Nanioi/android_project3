package com.nanioi.todolist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Job

internal abstract class BaseActivity<VM: BaseViewModel>: AppCompatActivity() {

    abstract val viewModel: VM // baseViewModel을 제너릭으로 받아서 타입을 그대로 지정해준다 .

    private lateinit var fetchJob: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fetchJob = viewModel.fetchData()
        observeData()
    }
    // oncreate가 될때 fetchJob을 해줌 -> 진입 할때마다 데이터를 불러온다.

    abstract fun observeData()
    // 추상함수로 observeData를 호출하고 호출되는 시점에 구독한 상태값을 가지고 UI가 변경되도록 구현

    override fun onDestroy() {
        if (fetchJob.isActive) {
            fetchJob.cancel()
        }
        super.onDestroy()
    }
    //화면 나간 시점 비동기 처리를 취소

}