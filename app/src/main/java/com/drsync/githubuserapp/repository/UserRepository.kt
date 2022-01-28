package com.drsync.githubuserapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.drsync.githubuserapp.viewmodels.MainViewModel
import com.drsync.githubuserapp.data.remote.SearchResponse
import com.drsync.githubuserapp.data.local.UserDao
import com.drsync.githubuserapp.data.local.UserEntity
import com.drsync.githubuserapp.data.remote.ApiService
import com.drsync.githubuserapp.data.remote.DetailResponse
import com.drsync.githubuserapp.data.remote.RemoteUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) {

    private val _searchUser = MutableLiveData<ArrayList<RemoteUser>>()
    val searchUser: LiveData<ArrayList<RemoteUser>> = _searchUser

    private val _detailUser = MutableLiveData<DetailResponse>()
    val detailUser: LiveData<DetailResponse> = _detailUser

    private val _follower = MutableLiveData<ArrayList<RemoteUser>>()
    val follower: LiveData<ArrayList<RemoteUser>> = _follower

    private val _following = MutableLiveData<ArrayList<RemoteUser>>()
    val following: LiveData<ArrayList<RemoteUser>> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getSearchUser(username: String) {
        _isLoading.value = true
        val client = apiService.getSearchUser(username)
        client.enqueue(object: Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    _searchUser.value = response.body()?.items
                } else {
                    Log.d(MainViewModel.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.d(MainViewModel.TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getDetailUser(username: String) {
        _isLoading.value = true
        val client = apiService.getDetailUser(username)
        client.enqueue(object: Callback<DetailResponse>{
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    _detailUser.value = response.body()
                } else {
                    Log.d(MainViewModel.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                Log.d(MainViewModel.TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getFollower(username: String) {
        _isLoading.value = true
        val client = apiService.getFollower(username)
        client.enqueue(object: Callback<ArrayList<RemoteUser>>{
            override fun onResponse(
                call: Call<ArrayList<RemoteUser>>,
                response: Response<ArrayList<RemoteUser>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    _follower.value = response.body()
                } else {
                    Log.d(MainViewModel.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<RemoteUser>>, t: Throwable) {
                Log.d(MainViewModel.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowing(username: String) {
        _isLoading.value = true
        val client = apiService.getFollowing(username)
        client.enqueue(object: Callback<ArrayList<RemoteUser>>{
            override fun onResponse(
                call: Call<ArrayList<RemoteUser>>,
                response: Response<ArrayList<RemoteUser>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    _following.value = response.body()
                } else {
                    Log.d(MainViewModel.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<RemoteUser>>, t: Throwable) {
                Log.d(MainViewModel.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    suspend fun insertFavorite(user: UserEntity) {
        val mUser = UserEntity(
            user.login,
            user.avatarUrl,
            true
        )
        userDao.insertFavorite(mUser)
    }

    suspend fun deleteFavorite(user: UserEntity) {
        val mUser = UserEntity(user.login, user.avatarUrl, false)
        userDao.deleteFavorite(mUser)
    }

    fun getAllFavorited(): LiveData<List<UserEntity>> = userDao.getUser()

    suspend fun isUserFavorited(username: String): Boolean {
        return userDao.isUserFavorite(username)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            userDao: UserDao
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userDao)
            }.also { instance = it }
    }

}