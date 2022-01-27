package com.drsync.githubuserapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.drsync.githubuserapp.data.remote.ApiConfig
import com.drsync.githubuserapp.data.remote.DetailResponse
import com.drsync.githubuserapp.data.remote.RemoteUser
import com.drsync.githubuserapp.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {

    companion object {
        const val TAG = "githupuserapp"
    }

    val isLoading = userRepository.isLoading
    val searchUser = userRepository.searchUser
    val detailUser = userRepository.detailUser
    val follower = userRepository.follower
    val following = userRepository.following

    fun getSearchUser(username: String) = userRepository.getSearchUser(username)
    fun getDetailUser(username: String) = userRepository.getDetailUser(username)
    fun getFollower(username: String) = userRepository.getFollower(username)
    fun getFollowing(username: String) = userRepository.getFollowing(username)
}