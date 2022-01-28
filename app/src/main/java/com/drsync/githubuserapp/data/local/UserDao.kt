package com.drsync.githubuserapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getUser(): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(user: UserEntity)

    @Delete
    suspend fun deleteFavorite(user: UserEntity)

    @Query("SELECT EXISTS(SELECT * FROM user WHERE login = :username AND favorited = 1)")
    suspend fun isUserFavorite(username: String): Boolean

}