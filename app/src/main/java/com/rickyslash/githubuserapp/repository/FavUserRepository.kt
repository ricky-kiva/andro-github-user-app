package com.rickyslash.githubuserapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.rickyslash.githubuserapp.database.FavUser
import com.rickyslash.githubuserapp.database.FavUserDao
import com.rickyslash.githubuserapp.database.FavUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavUserRepository(application: Application) {
    private val mFavUserDao: FavUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavUserRoomDatabase.getDatabase(application)
        mFavUserDao = db.favUserDao()
    }

    fun getAllFavUsers(): LiveData<List<FavUser>> = mFavUserDao.getAllFavUsers()

    fun getFavUserByUsername(username: String): LiveData<FavUser> = mFavUserDao.getFavUserByUsername(username)

    fun insertFavUser(username: String, avatarUrl: String) {
        executorService.execute { mFavUserDao.insertFavUser(username, avatarUrl) }
    }

    fun deleteFavUserByUsername(username: String) {
        executorService.execute { mFavUserDao.deleteFavUserByUsername(username) }
    }
}