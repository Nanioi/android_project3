package com.nanioi.todolist.presentation.list

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.nanioi.todolist.R
import com.nanioi.todolist.databinding.ActivityListBinding
import com.nanioi.todolist.presentation.BaseActivity
import com.nanioi.todolist.presentation.detail.DetailActivity
import com.nanioi.todolist.presentation.detail.DetailMode
import com.nanioi.todolist.presentation.view.ToDoAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
import org.koin.android.viewmodel.ext.android.viewModel

internal class ListActivity : BaseActivity<ListViewModel>(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    private lateinit var binding: ActivityListBinding

    private val adapter = ToDoAdapter()

    override val viewModel: ListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initViews(binding: ActivityListBinding) = with(binding) {
        recyclerView.layoutManager = LinearLayoutManager(this@ListActivity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        refreshLayout.setOnRefreshListener {
            viewModel.fetchData()
        }

        addToDoButton.setOnClickListener {
            //todoItem 추가할때
            startActivityForResult(
                DetailActivity.getIntent(this@ListActivity, DetailMode.WRITE),
                DetailActivity.FETCH_REQUEST_CODE
            )
        }
    }

    //상태값이 변경될 때마다 상태값에 따라 분기처리
    override fun observeData() {
        viewModel.toDoListLiveData.observe(this) {
            when (it) {
                is ToDoListState.UnInitialized -> {
                    initViews(binding)
                }
                is ToDoListState.Loading -> {
                    handleLoadingState()
                }
                is ToDoListState.Suceess -> {
                    handleSuccessState(it)
                }
                is ToDoListState.Error -> {
                    handleErrorState()
                }
            }
        }
    }

    private fun handleLoadingState() = with(binding) {
        refreshLayout.isRefreshing = true
    }

    private fun handleSuccessState(state: ToDoListState.Suceess) = with(binding) {
        refreshLayout.isEnabled = state.toDoList.isNotEmpty()
        refreshLayout.isRefreshing = false

        if (state.toDoList.isEmpty()) {
            emptyResultTextView.isGone = false
            recyclerView.isGone = true
        } else {
            emptyResultTextView.isGone = true
            recyclerView.isGone = false
            adapter.setToDoList(
                state.toDoList,
                toDoItemClickListener = { // 일반 상세화면에서 들어갈 때
                    startActivityForResult(
                        DetailActivity.getIntent(this@ListActivity, it.id, DetailMode.DETAIL),
                        DetailActivity.FETCH_REQUEST_CODE
                    )
                }, toDoCheckListener = {
                    viewModel.updateEntity(it)
                }
            )
        }
    }

    private fun handleErrorState(){
        Toast.makeText(this, "에러가 발생했습니다.",Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == DetailActivity.FETCH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            viewModel.fetchData()
        }
    }

    // 메뉴 - delete all 클릭시
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_all -> {
                viewModel.deleteAll()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    // 메뉴 클릭시
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_menu, menu)
        return true
    }
}