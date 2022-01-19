package com.drsync.githubuserapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    companion object {
        const val TAG = "githupuserapp"
    }

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
        val client = ApiConfig.getApiService().getSearchUser(username)
        client.enqueue(object: Callback<SearchResponse>{
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    _searchUser.value = response.body()?.items
                } else {
                    Log.d(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getDetailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object: Callback<DetailResponse>{
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    _detailUser.value = response.body()
                } else {
                    Log.d(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getFollower(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollower(username)
        client.enqueue(object: Callback<ArrayList<RemoteUser>>{
            override fun onResponse(
                call: Call<ArrayList<RemoteUser>>,
                response: Response<ArrayList<RemoteUser>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    _follower.value = response.body()
                } else {
                    Log.d(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<RemoteUser>>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object: Callback<ArrayList<RemoteUser>>{
            override fun onResponse(
                call: Call<ArrayList<RemoteUser>>,
                response: Response<ArrayList<RemoteUser>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    _following.value = response.body()
                } else {
                    Log.d(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<RemoteUser>>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}