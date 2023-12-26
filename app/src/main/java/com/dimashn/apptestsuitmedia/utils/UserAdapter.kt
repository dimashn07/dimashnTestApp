package com.dimashn.apptestsuitmedia.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dimashn.apptestsuitmedia.data.User
import com.dimashn.apptestsuitmedia.databinding.ItemUserBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var list = emptyList<User>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun updateList(newList: List<User>) {
        val userDiff = UserDiffutils(list, newList)
        val result = DiffUtil.calculateDiff(userDiff)
        list = newList
        result.dispatchUpdatesTo(this)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.apply {
                root.setOnClickListener { onItemClickCallback?.onItemClicked(user) }
                tvItemUsername.text = "${user.first_name} ${user.last_name}"
                tvItemUserUrl.text = user.email
                Glide.with(itemView)
                    .load(user.avatar)
                    .centerCrop()
                    .into(ivItemUser)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}
