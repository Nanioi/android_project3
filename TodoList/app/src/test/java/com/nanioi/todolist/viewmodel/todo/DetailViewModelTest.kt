package com.nanioi.todolist.viewmodel.todo

import com.nanioi.todolist.data.entity.ToDoEntity
import com.nanioi.todolist.domain.todo.InsertToDoItemUseCase
import com.nanioi.todolist.presentation.detail.DetailMode
import com.nanioi.todolist.presentation.detail.DetailViewModel
import com.nanioi.todolist.presentation.detail.ToDoDetailState
import com.nanioi.todolist.presentation.list.ListViewModel
import com.nanioi.todolist.presentation.list.ToDoListState
import com.nanioi.todolist.viewmodel.ViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.koin.core.parameter.parametersOf
import org.koin.test.inject

/**
 * [DetailViewModel]을 테스트하기 위한 Unit Test Class
 *
 * 1. initData() - mocking 데이터를 먼저 넣어준 후 데이터가 잘 불러와 지는지 test
 * 2. test viewModel fetch - viewmodel이 fetch한 함수 호출했을 때 데이터가 잘 불러와 지는지
 * 3. test update todo - 불러온 데이터가 업데이트가 잘 되는지
 * 4. test delete todo - 아이템 전체 삭제 시 삭제가 잘되는지
 *
 */

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
internal class DetailViewModelTest : ViewModelTest() {

    private val detailViewModel: DetailViewModel by inject { parametersOf(DetailMode.DETAIL, id) }// mode에따라 ui형태가 바뀌므로 parameter을 만들어줘야함
    private val listViewModel: ListViewModel by inject()
    private val insertToDoItemUseCase: InsertToDoItemUseCase by inject()

    val id = 1L

    private val todo = ToDoEntity(
        id,
        title = "title $id",
        description = "description $id",
        hasCompleted = false
    )

    @Before
    fun init() {
        initData()
    }

    private fun initData() = runBlockingTest {
        insertToDoItemUseCase(todo)
    }

    // 코루틴 블럭에서 실행하기 위해 runBlocking
    @Test
    fun `test viewModel fetch`() = runBlockingTest {
        val testObservable = detailViewModel.toDoDetailLiveData.test()

        detailViewModel.fetchData()

        testObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Loading,
                ToDoDetailState.Success(todo)
            )
        )
    }

    @Test
    fun `test delete todo`() = runBlockingTest {
        val detailTestObservable = detailViewModel.toDoDetailLiveData.test()

        detailViewModel.deleteToDo()

        detailTestObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Loading,
                ToDoDetailState.Delete
            )
        )

        //삭제 후 리스트에도 데이터가 없다는 것을 보여주기 위해
        val listTestObservable = listViewModel.toDoListLiveData.test()

        listViewModel.fetchData()

        listTestObservable.assertValueSequence(
            listOf(
                ToDoListState.UnInitialized,
                ToDoListState.Loading,
                ToDoListState.Suceess(listOf())
            )
        )
    }

    @Test
    fun `test update todo`() = runBlockingTest {
        val detailTestObservable = detailViewModel.toDoDetailLiveData.test()

        val updateTitle = "title 1 update"
        val updateDescription = "description 1 update"

        val updateToDo = todo.copy(
            title = updateTitle,
            description = updateDescription
        )

        detailViewModel.writeToDo(
            title = updateTitle,
            description = updateDescription
        )

        detailTestObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Loading,
                ToDoDetailState.Success(updateToDo)
            )
        )
    }

}
