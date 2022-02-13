package com.technologies.ghusers.feature.users.details

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.technologies.ghusers.R
import com.technologies.ghusers.core.base.BaseFragment
import com.technologies.ghusers.core.base.BaseViewModel
import com.technologies.ghusers.databinding.FragmentUserDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsFragment : BaseFragment<FragmentUserDetailsBinding>() {

    private val viewModel: UserDetailsViewModel by viewModels()

    override val layoutRes: Int
        get() = R.layout.fragment_user_details

    override fun onCreated(savedInstance: Bundle?) {
        initBinding()
        checkArgs()
    }

    private fun initBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun checkArgs() {
        arguments?.let {
            if (it.containsKey(ARGS_USER_LOGIN)) {
                viewModel.getUserDetails(it.getString(ARGS_USER_LOGIN, ""))
            } else {
                viewModel.setError(getString(R.string.error_missing_user_login))
                findNavController().navigateUp()
            }
        }
    }

    override fun getViewModel(): BaseViewModel = viewModel


    companion object {
        const val ARGS_USER_LOGIN = "_args_user_login"
    }
}