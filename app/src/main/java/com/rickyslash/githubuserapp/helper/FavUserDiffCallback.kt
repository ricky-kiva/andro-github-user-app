package com.rickyslash.githubuserapp.helper

import androidx.recyclerview.widget.DiffUtil
import com.rickyslash.githubuserapp.database.FavUser

class FavUserDiffCallback(private val mOldFavUserList: List<FavUser>, private val mNewFavUserList: List<FavUser>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldFavUserList.size
    }

    override fun getNewListSize(): Int {
        return mNewFavUserList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavUserList[oldItemPosition].id == mNewFavUserList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldFavUserList[oldItemPosition]
        val newEmployee = mNewFavUserList[newItemPosition]
        return oldEmployee.username == newEmployee.username && oldEmployee.avatarUrl == newEmployee.avatarUrl
    }

}