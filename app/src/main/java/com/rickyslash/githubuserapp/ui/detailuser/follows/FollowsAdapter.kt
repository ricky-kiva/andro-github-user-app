package com.rickyslash.githubuserapp.ui.detailuser.follows

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rickyslash.githubuserapp.api.response.DetailFollowsResponseItem
import com.rickyslash.githubuserapp.databinding.ItemFollowsBinding

class FollowsAdapter(private val followsList: List<DetailFollowsResponseItem>):  RecyclerView.Adapter<FollowsAdapter.ViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(var binding: ItemFollowsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFollowsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = followsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = followsList[position]
        holder.binding.tvFollowsName.text = data.login
        Glide.with(holder.itemView.context)
            .load(data.avatarUrl)
            .into(holder.binding.imgFollowsPhoto)

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(followsList[position]) }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DetailFollowsResponseItem)
    }
}