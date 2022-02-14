package com.technologies.ghusers.feature.users.list

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.technologies.ghusers.R
import com.technologies.ghusers.core.base.BaseFragment
import com.technologies.ghusers.core.base.BaseViewModel
import com.technologies.ghusers.core.extensions.getNavigationResult
import com.technologies.ghusers.core.extensions.observe
import com.technologies.ghusers.databinding.FragmentUsersBinding
import com.technologies.ghusers.feature.users.details.UserDetailsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import okhttp3.internal.notifyAll

@FlowPreview
@AndroidEntryPoint
class UsersFragment : BaseFragment<FragmentUsersBinding>() {

    private val viewModel: UsersViewModel by viewModels()

    lateinit var adapter: UsersPagedListAdapter

    private var listSkeleton: Skeleton? = null

    override val layoutRes: Int
        get() = R.layout.fragment_users

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCreated(savedInstance: Bundle?) {
        initViews()
        initObservers()
    }

    override fun onResume() {
        super.onResume()
        getNavigationResult<Pair<Boolean, Int>>(UserDetailsFragment.ARGS_DATA_UPDATED)?.let {
            viewLifecycleOwner.observe(it) {
                if (it?.first == true) {
                    adapter.setHasNotes(it.second)
                }
            }
        }
    }

    private fun initViews() {
        adapter = UsersPagedListAdapter(UsersPagedListAdapter.diffUtil)
        binding.usersRvData.adapter = adapter

        adapter.clickListener = { user, position ->
            findNavController().navigate(
                R.id.action_navTo_userDetails,
                bundleOf(
                    UserDetailsFragment.ARGS_USER_LOGIN to user.login,
                    UserDetailsFragment.ARGS_USER_POSITION to position
                )
            )
        }

        listSkeleton = binding.usersRvData.applySkeleton(R.layout.item_user, 5)

        binding.usersSrlData.setOnRefreshListener {
            binding.usersSrlData.isRefreshing = false
            viewModel.reload()
        }
    }

    private fun initObservers() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.apply {
            getUsers()
            viewLifecycleOwner.observe(users) { adapter.submitList(it) }
            viewLifecycleOwner.observe(loading, ::handleLoading)
        }
    }

    private fun handleLoading(isLoading: Boolean?) {
        isLoading?.let { loading ->
            if (loading && adapter.itemCount == 0 && listSkeleton?.isSkeleton() != true) {
                listSkeleton?.showSkeleton()
            } else {
                listSkeleton?.apply {
                    if (isSkeleton()) {
                        showOriginal()
                    }
                }
            }
        }
    }
}