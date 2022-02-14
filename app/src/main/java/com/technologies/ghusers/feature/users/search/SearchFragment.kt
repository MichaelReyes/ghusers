package com.technologies.ghusers.feature.users.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.technologies.ghusers.R
import com.technologies.ghusers.core.base.BaseActivity
import com.technologies.ghusers.core.base.BaseFragment
import com.technologies.ghusers.core.base.BaseViewModel
import com.technologies.ghusers.core.extensions.observe
import com.technologies.ghusers.databinding.FragmentSearchBinding
import com.technologies.ghusers.feature.users.details.UserDetailsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@FlowPreview
@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private val viewModel: SearchViewModel by viewModels()

    @Inject
    lateinit var adapter: SearchListAdapter

    override val layoutRes: Int
        get() = R.layout.fragment_search

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCreated(savedInstance: Bundle?) {
        initViews()
        initObservers()
    }

    private fun initObservers() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.apply {
            observe(users) {
                it?.let { adapter.collection = it }
            }
        }
    }

    private fun initViews() {

        (activity as? BaseActivity<*>)?.setToolbar(
            show = true,
            title = getString(R.string.lbl_search),
            showBackButton = true
        )

        binding.searchRvUsers.adapter = adapter
        adapter.clickListener = {
            findNavController().navigate(
                R.id.action_navSearchTo_userDetails,
                bundleOf(
                    UserDetailsFragment.ARGS_USER_LOGIN to it.login
                )
            )
        }
    }
}