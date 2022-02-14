package com.technologies.ghusers.feature.users

import android.os.Bundle
import androidx.activity.viewModels
import com.technologies.ghusers.R
import com.technologies.ghusers.core.base.BaseActivity
import com.technologies.ghusers.core.base.BaseViewModel
import com.technologies.ghusers.databinding.ActivityUsersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersActivity : BaseActivity<ActivityUsersBinding>() {

    private val viewModel: UsersMainViewModel by viewModels()

    override val layoutRes: Int
        get() = R.layout.activity_users

    override fun onCreated(instance: Bundle?) {
        setSupportActionBar(binding.toolbar)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    override fun getViewModel(): BaseViewModel = viewModel
}