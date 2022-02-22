package com.nanioi.shoppingapplication.presentation.list

import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import aop.fastcampus.part5.chapter02.presentation.adapter.ProductListAdapter
import com.nanioi.shoppingapplication.databinding.FragmentProductListBinding
import com.nanioi.shoppingapplication.databinding.FragmentProfileBinding
import com.nanioi.shoppingapplication.extensions.toast
import com.nanioi.shoppingapplication.presentation.BaseActivity
import com.nanioi.shoppingapplication.presentation.BaseFragment
import com.nanioi.shoppingapplication.presentation.main.MainActivity
import com.nanioi.shoppingapplication.presentation.profile.ProfileViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class ProductListFragment : BaseFragment<ProductListViewModel,FragmentProductListBinding>() {

    companion object {
        const val TAG = "ProductListFragment"
    }

    override fun getViewBinding(): FragmentProductListBinding =
        FragmentProductListBinding.inflate(layoutInflater)

    override val viewModel by viewModel<ProductListViewModel>()

    private val adapter = ProductListAdapter()

    private val startProductDetailForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            // todo 성공적으로 처리 완료 이후 동작
            // 주문 완료 후 체크 - 프로필 탭으로 이동 시키고 새로고침해주기 위해서 구현한 것
        }

    private fun initViews(binding: FragmentProductListBinding) = with(binding) {
        recyclerView.adapter = adapter

        refreshLayout.setOnRefreshListener {
            viewModel.fetchData()
        }

    }

    override fun observeData() = viewModel.productListStateLiveData.observe(this) {
        when (it) {
            is ProductListState.UnInitialized -> {
                initViews(binding)
            }
            is ProductListState.Loading -> {
                handleLoadingState()
            }
            is ProductListState.Success -> {
                handleSuccessState(it)
            }
            is ProductListState.Error -> {
                handleErrorState()
            }
        }
    }

    private fun handleLoadingState() = with(binding) {
        refreshLayout.isRefreshing = true
    }

    private fun handleSuccessState(state: ProductListState.Success) = with(binding) {
        refreshLayout.isRefreshing = false

        if (state.productList.isEmpty()) {
            emptyResultTextView.isGone = false
            recyclerView.isGone = true
        } else {
            emptyResultTextView.isGone = true
            recyclerView.isGone = false
            adapter.setProductList(state.productList) {
//                startProductDetailForResult.launch(
//                    ProductDetailActivity.newIntent(requireContext(), it.id)
//                )
                requireContext().toast("Product Entity : $it")
            }
        }
    }

    private fun handleErrorState() {
        Toast.makeText(requireContext(), "에러가 발생했습니다.", Toast.LENGTH_SHORT).show()
    }
}