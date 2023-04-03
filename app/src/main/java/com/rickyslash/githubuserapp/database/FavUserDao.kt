package com.rickyslash.githubuserapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favUser: FavUser)

    @Update()
    fun update(favUser: FavUser)

    @Delete()
    fun delete(favUser: FavUser)

    @Query("SELECT * from favUser ORDER BY id ASC")
    fun getAllFavUsers(): LiveData<List<FavUser>>

    @Query("SELECT * from favUser where username = :username")
    fun getFavUserByUsername(username: String): LiveData<FavUser>

    @Query("INSERT INTO FavUser(username, avatarUrl) VALUES(:username, :avatarUrl)")
    fun insertFavUser(username: String, avatarUrl: String)

    @Query("DELETE FROM FavUser WHERE username = :username")
    fun deleteFavUserByUsername(username: String)
}