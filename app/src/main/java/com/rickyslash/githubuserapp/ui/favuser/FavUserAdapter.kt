package com.rickyslash.githubuserapp.ui.favuser

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rickyslash.githubuserapp.database.FavUser
import com.rickyslash.githubuserapp.databinding.ItemUserBinding
import com.rickyslash.githubuserapp.helper.FavUserDiffCallback
import com.rickyslash.githubuserapp.ui.detailuser.DetailUserActivity

class FavUserAdapter: RecyclerView.Adapter<FavUserAdapter.FavUserViewHolder>() {

    private val listFavUser = ArrayList<FavUser>()

    fun setListFavUser(listFavUser: List<FavUser>) {
        val diffCallback = FavUserDiffCallback(this.listFavUser, listFavUser)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavUser.clear()
        this.listFavUser.addAll(listFavUser)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class FavUserViewHolder(private val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(favUser: FavUser) {
            with(binding) {
                tvName.text = favUser.username
                Glide.with(root.context)
                    .load(favUser.avatarUrl)
                    .into(imgDetailPhoto)
                clItemUser.setOnClickListener {
                    val intent = Intent(it.context, DetailUserActivity::class.java)
                    intent.putExtra(DetailUserActivity.EXTRA_USERNAME, favUser.username)
                    it.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavUserAdapter.FavUserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavUserAdapter.FavUserViewHolder, position: Int) {
        holder.bind(listFavUser[position])
    }

    override fun getItemCount(): Int = listFavUser.size
}