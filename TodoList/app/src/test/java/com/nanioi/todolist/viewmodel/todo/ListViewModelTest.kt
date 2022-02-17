package com.nanioi.todolist.viewmodel.todo

import com.nanioi.todolist.data.entity.ToDoEntity
import com.nanioi.todolist.domain.todo.InsertToDoListUseCase
import com.nanioi.todolist.presentation.list.ListViewModel
import com.nanioi.todolist.viewmodel.ViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.koin.test.inject

/**
 * [ListViewModel]을 테스트하기 위한 Unit Test Class
 *
 * 1. initData() - mocking 데이터를 먼저 넣어준 후 데이터가 잘 불러와 지는지 test
 * 2. test viewModel fetch - viewmodel이 fetch한 함수 호출했을 때 데이터가 잘 불러와 지는지
 * 3. test Item update - 불러온 데이터가 업데이트가 잘 되는지
 * 4. test Item Delete All - 아이템 전체 삭제 시 삭제가 잘되는지
 *
 */
@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
internal class ListViewModelTest : ViewModelTest() {

    /**
     * 필요한 Usecase들
     * 1. InsertTodoListUseCase
     * 2. GetTodoItemUseCase
     */

    private val viewModel: ListViewModel by inject()

    private val insertToDoListUseCase: InsertToDoListUseCase by inject()

    //todo에 들어갈 임의의 mock데이터
    private val list = (0 until 10).map {
        ToDoEntity(
            id = it.toLong(),
            title = "title $it",
            description = "description $it",
            hasCompleted = false // 체크하는 로직
        )
    }

    @Before
    fun init() {
        initData()
    }

    private fun initData() = runBlockingTest {
        insertToDoListUseCase(list)
    }

    //데이터 불러오기 위한 test - 입력된 데이터를 불러와서 검증한다.
    @Test
    fun `test viewModel fetch`(): Unit = runBlockingTest {
        val testObservable = viewModel.toDoListLiveData.test()

        viewModel.fetchData()

        testObservable.assertValueSequence(
            listOf(
                list
            )
        )

    }


}
