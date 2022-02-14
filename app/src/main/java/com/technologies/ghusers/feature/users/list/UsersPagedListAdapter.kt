package com.technologies.ghusers.feature.users.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.technologies.ghusers.R
import com.technologies.ghusers.core.data.dao.NoteDao
import com.technologies.ghusers.core.data.entity.User
import com.technologies.ghusers.databinding.ItemUserBinding

class UsersPagedListAdapter constructor(
    @NonNull val diffCallback: DiffUtil.ItemCallback<User>
) : PagedListAdapter<User, RecyclerView.ViewHolder>(diffCallback) {

    internal var clickListener: (User) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemUserBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_user, parent, false
        )
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as Holder
        getItem(position)?.let {
            viewHolder.bind(it)
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding: ItemUserBinding? = DataBindingUtil.bind(itemView)

        internal fun bind(user: User) {
            binding?.apply {
                user.identifier = absoluteAdapterPosition + 1
                this.item = user
                itemView.setOnClickListener { clickListener(user) }
                executePendingBindings()
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.login == newItem.login
            }
        }
    }
}

