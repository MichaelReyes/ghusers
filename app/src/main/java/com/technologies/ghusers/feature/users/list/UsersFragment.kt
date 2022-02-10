package com.technologies.ghusers.feature.users.list

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.technologies.ghusers.R
import com.technologies.ghusers.core.base.BaseFragment
import com.technologies.ghusers.core.base.BaseViewModel
import com.technologies.ghusers.core.extensions.observe
import com.technologies.ghusers.databinding.FragmentUsersBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

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

    private fun initViews() {
        adapter = UsersPagedListAdapter(UsersPagedListAdapter.diffUtil)
        binding.usersRvData.adapter = adapter

        listSkeleton = binding.usersRvData.applySkeleton(R.layout.item_user, 5)

        binding.usersSrlData.setOnRefreshListener {
            binding.usersSrlData.isRefreshing = false
            viewModel.reload()
        }
    }

    private fun initObservers() {
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