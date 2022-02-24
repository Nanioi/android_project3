package com.nanioi.shoppingapplication.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.nanioi.shoppingapplication.data.preference.PreferenceManager
import com.nanioi.shoppingapplication.domain.product.DeleteOrderedProductListUseCase
import com.nanioi.shoppingapplication.domain.product.GetOrderedProductListUseCase
import com.nanioi.shoppingapplication.presentation.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class ProfileViewModel(
    private val preferenceManager: PreferenceManager,
    private val getOrderedProductListUseCase: GetOrderedProductListUseCase,
    private val deleteOrderedProductListUseCase: DeleteOrderedProductListUseCase

    ) : BaseViewModel() {

    private var _profileStateLiveData = MutableLiveData<ProfileState>(ProfileState.UnInitialized)
    val profileStateLiveData: LiveData<ProfileState> = _profileStateLiveData

    override fun fetchData(): Job = viewModelScope.launch{
        setState(
            ProfileState.Loading
        )
        preferenceManager.getIdToken()?.let { token->
            setState(
                ProfileState.Login(token)
            )
        } ?: kotlin.run {
            setState(
                ProfileState.Success.NotRegistered
            )
        }
    }

    fun setUserInfo(firebaseUser: FirebaseUser?) = viewModelScope.launch {
        firebaseUser?.let { user ->
            setState(
                ProfileState.Success.Registered(
                    user.displayName ?: "익명",
                    user.photoUrl,
                    getOrderedProductListUseCase()
                )
            )
        } ?: kotlin.run {
            setState(
                ProfileState.Success.NotRegistered
            )
        }
    }

    fun signOut() = viewModelScope.launch {
        preferenceManager.removedToken()
        deleteOrderedProductListUseCase()
        fetchData()
    }


    fun saveToken(idToken: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            preferenceManager.putIdToken(idToken)
            fetchData()
        }
    }

    private fun setState(state: ProfileState) {
        _profileStateLiveData.postValue(state)
    }
}