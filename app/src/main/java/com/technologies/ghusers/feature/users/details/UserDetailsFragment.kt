package com.technologies.ghusers.feature.users.details

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.technologies.ghusers.R
import com.technologies.ghusers.core.base.BaseActivity
import com.technologies.ghusers.core.base.BaseFragment
import com.technologies.ghusers.core.base.BaseViewModel
import com.technologies.ghusers.core.data.entity.Note
import com.technologies.ghusers.core.extensions.observe
import com.technologies.ghusers.core.extensions.setNavigationResult
import com.technologies.ghusers.databinding.FragmentUserDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview

@FlowPreview
@AndroidEntryPoint
class UserDetailsFragment : BaseFragment<FragmentUserDetailsBinding>(), UserDetailsHandler {

    private val viewModel: UserDetailsViewModel by viewModels()
    private var isDataUpdated = false
    private var positionInList = -1

    override val layoutRes: Int
        get() = R.layout.fragment_user_details

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onSaveNotes() {
        viewModel.insertNotes()
    }

    override fun onCreated(savedInstance: Bundle?) {

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                setNavigationResult<Triple<Boolean, Int, Note?>>(
                    result = Triple(isDataUpdated, positionInList, viewModel.currentNotes.value),
                    key = ARGS_DATA_UPDATED
                )
                findNavController().navigateUp()
            }
        })


        (activity as? BaseActivity<*>)?.setToolbar(
            show = true,
            title = getString(R.string.lbl_user_details),
            showBackButton = true
        )

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
                        isDataUpdated = true
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

            if (it.containsKey(ARGS_USER_POSITION)) {
                positionInList = it.getInt(ARGS_USER_POSITION, -1)
            }
        }
    }


    companion object {
        const val ARGS_USER_LOGIN = "_args_user_login"
        const val ARGS_USER_POSITION = "_args_user_position"
        const val ARGS_DATA_UPDATED = "_args_data_updated"
    }
}