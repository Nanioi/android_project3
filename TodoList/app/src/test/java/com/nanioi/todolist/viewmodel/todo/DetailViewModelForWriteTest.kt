package com.nanioi.todolist.viewmodel.todo

import com.nanioi.todolist.data.entity.ToDoEntity
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
import org.mockito.Mock

//쓰기용도
/**
 * [DetailViewModel]을 테스트하기 위한 Unit Test Class
 *
 * 1. test viewModel fetch - viewmodel이 fetch한 함수 호출했을 때 데이터가 잘 불러와 지는지
 * 2. test insert todo
 *
 */

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
internal class DetailViewModelForWriteTest : ViewModelTest() {

    private val detailViewModel: DetailViewModel by inject { parametersOf(DetailMode.WRITE, id) }
    private val listViewModel: ListViewModel by inject()

    val id = 0L

    private val todo = ToDoEntity(
        id,
        title = "title 1",
        description = "description 1",
        hasCompleted = false
    )

    @Test
    fun `test viewModel fetch`() = runBlockingTest {
        val testObservable = detailViewModel.toDoDetailLiveData.test()

        detailViewModel.fetchData()

        testObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Write
            )
        )
    }

    @Test
    fun `test insert todo`() = runBlockingTest {
        val detailTestObservable = detailViewModel.toDoDetailLiveData.test()
        val listTestObservable = listViewModel.toDoListLiveData.test()

        detailViewModel.writeToDo(
            title = todo.title,
            description = todo.description
        )

        detailTestObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Loading,
                ToDoDetailState.Success(todo)
            )
        )

        //상세화면으로 바뀌었는지, 잘들어갔는지
        assert(detailViewModel.detailMode == DetailMode.DETAIL)
        assert(detailViewModel.id == id)

        // 추가 후 id같으면 ( 성공 시 ) 뒤로 나가 리스트 보기
        listViewModel.fetchData()
        listTestObservable.assertValueSequence(
            listOf(
                ToDoListState.UnInitialized,
                ToDoListState.Loading,
                ToDoListState.Suceess(listOf(todo))
            )
        )
    }

}
