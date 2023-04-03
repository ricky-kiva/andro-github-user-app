package com.rickyslash.githubuserapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rickyslash.githubuserapp.api.response.ItemsItem
import com.rickyslash.githubuserapp.databinding.ItemUserBinding

class UsersAdapter(private val userList: List<ItemsItem>): RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = userList[position]
        holder.binding.tvName.text = data.login
        Glide.with(holder.itemView.context)
            .load(data.avatarUrl)
            .into(holder.binding.imgDetailPhoto)

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(userList[position]) }
    }

    override fun getItemCount(): Int = userList.size

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem)
    }

}