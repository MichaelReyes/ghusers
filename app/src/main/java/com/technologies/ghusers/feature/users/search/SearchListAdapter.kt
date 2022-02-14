package com.technologies.ghusers.feature.users.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.technologies.ghusers.R
import com.technologies.ghusers.core.data.entity.User
import com.technologies.ghusers.core.utils.AutoUpdatableAdapter
import com.technologies.ghusers.databinding.ItemUserBinding
import javax.inject.Inject
import kotlin.properties.Delegates

class SearchListAdapter @Inject constructor() :
    RecyclerView.Adapter<SearchListAdapter.Holder>(),
    AutoUpdatableAdapter {

    internal var collection: List<User> by Delegates.observable(emptyList()) { prop, old, new ->
        autoNotify(old, new) { o, n -> o.id == n.id }
    }

    internal var clickListener: (User) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder.from(
            parent,
            R.layout.item_user
        )

    override fun getItemCount() = collection.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.apply {
            val user = collection[position]
            user.identifier = position + 1

            item = user
            holder.itemView.setOnClickListener {
                clickListener.invoke(user)
            }
            executePendingBindings()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class Holder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup, layout: Int): Holder {
                val inflater = LayoutInflater.from(parent.context)
                val binding =
                    DataBindingUtil.inflate<ItemUserBinding>(
                        inflater,
                        layout,
                        parent,
                        false
                    )
                return Holder(
                    binding
                )
            }
        }
    }
}
