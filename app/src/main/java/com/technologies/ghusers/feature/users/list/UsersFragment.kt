package com.technologies.ghusers.feature.users.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.technologies.ghusers.R
import com.technologies.ghusers.core.base.BaseFragment
import com.technologies.ghusers.core.base.BaseViewModel
import com.technologies.ghusers.databinding.FragmentUsersBinding

class UsersFragment : BaseFragment<FragmentUsersBinding>() {

    private val viewModel: UsersViewModel by viewModels()

    override val layoutRes: Int
        get() = R.layout.fragment_users

    override fun onCreated(savedInstance: Bundle?) {

    }

    override fun getViewModel(): BaseViewModel = viewModel
}