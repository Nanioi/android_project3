package com.nanioi.shoppingapplication.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.Job

// 제너릭으로 baseViewModel, ViewBinding을 상속받는, 구현하는 애들의 타입을 넘겨받아 처리
internal abstract class BaseActivity<VM: BaseViewModel, VB: ViewBinding>: AppCompatActivity() {

    abstract val viewModel: VM

    protected lateinit var binding: VB

    abstract fun getViewBinding(): VB

    private lateinit var fetchJob: Job
    // 코루틴 기반으로 동작을 하게 fetchdata 구현
    // viewModelScope에서 코루틴블럭을 실행을 시키면 job인스턴스를 받게됨
    // fetchjob이라는 이름으로 화면 종료 시 해당 코루틴을 종료 시킬 수 잇게 구현

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)

        fetchJob = viewModel.fetchData()
        observeData()
    }

    abstract fun observeData()

    override fun onDestroy() {
        if (fetchJob.isActive) {
            fetchJob.cancel()
        }
        super.onDestroy()
    }

}
