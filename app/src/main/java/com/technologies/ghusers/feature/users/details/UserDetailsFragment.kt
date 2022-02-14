package com.technologies.ghusers.feature.users.details

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.technologies.ghusers.R
import com.technologies.ghusers.core.base.BaseFragment
import com.technologies.ghusers.core.base.BaseViewModel
import com.technologies.ghusers.core.extensions.observe
import com.technologies.ghusers.databinding.FragmentUserDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview

@FlowPreview
@AndroidEntryPoint
class UserDetailsFragment : BaseFragment<FragmentUserDetailsBinding>(), UserDetailsHandler {

    private val viewModel: UserDetailsViewModel by viewModels()

    override val layoutRes: Int
        get() = R.layout.fragment_user_details

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onSaveNotes() {
        viewModel.insertNotes()
    }

    override fun onCreated(savedInstance: Bundle?) {
        initBinding()
        initObservers()
        checkArgs()
    }

    private fun initObservers() {
        viewModel.apply {
            observe(formState) {
                it?.let {
                    if (it.onNotesSaved) {
                        showMessage(
                            message = "Notes saved successfully",
                            positive = true
                        )
                        resetNotesSaved()
                    }
                }
            }
        }
    }

    private fun initBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.handler = this
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


    companion object {
        const val ARGS_USER_LOGIN = "_args_user_login"
    }
}