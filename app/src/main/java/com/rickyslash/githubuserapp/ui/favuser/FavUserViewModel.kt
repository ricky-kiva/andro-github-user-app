package com.rickyslash.githubuserapp.ui.favuser

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rickyslash.githubuserapp.database.FavUser
import com.rickyslash.githubuserapp.repository.FavUserRepository

class FavUserViewModel(application: Application): ViewModel() {
    private val mFavUserRepository: FavUserRepository = FavUserRepository(application)

    fun getAllFavUsers(): LiveData<List<FavUser>> = mFavUserRepository.getAllFavUsers()
}