package com.nanioi.shoppingapplication.presentation.profile

import android.os.Parcel
import android.os.Parcelable
import com.nanioi.shoppingapplication.databinding.FragmentProfileBinding
import com.nanioi.shoppingapplication.presentation.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

internal class ProfileFragment() :BaseFragment<ProfileViewModel,FragmentProfileBinding>() {

    companion object {
        const val TAG = "ProfileFragment"
    }

    override fun getViewBinding(): FragmentProfileBinding =
        FragmentProfileBinding.inflate(layoutInflater)

    override val viewModel by viewModel<ProfileViewModel>()

    override fun observeData() {
        TODO("Not yet implemented")
    }
}