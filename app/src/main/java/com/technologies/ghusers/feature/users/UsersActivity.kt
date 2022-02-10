package com.technologies.ghusers.feature.users

import android.os.Bundle
import com.technologies.ghusers.R
import com.technologies.ghusers.core.base.BaseActivity
import com.technologies.ghusers.databinding.ActivityUsersBinding

class UsersActivity : BaseActivity<ActivityUsersBinding>() {
    override val layoutRes: Int
        get() = R.layout.activity_users

    override fun onCreated(instance: Bundle?) {
        setSupportActionBar(binding.toolbar)
    }
}