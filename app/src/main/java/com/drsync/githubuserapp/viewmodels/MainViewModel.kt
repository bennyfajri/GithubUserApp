package com.drsync.githubuserapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drsync.githubuserapp.data.local.UserEntity
import com.drsync.githubuserapp.repository.UserRepository
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {

    companion object {
        const val TAG = "githupuserapp"
    }

    val isLoading = userRepository.isLoading
    val searchUser = userRepository.searchUser
    val detailUser = userRepository.detailUser
    val follower = userRepository.follower
    val following = userRepository.following
    private val _isFavorited = MutableLiveData<Boolean>()
    val isFavorited: LiveData<Boolean> = _isFavorited

    fun getSearchUser(username: String) = userRepository.getSearchUser(username)
    fun getDetailUser(username: String) = userRepository.getDetailUser(username)
    fun getFollower(username: String) = userRepository.getFollower(username)
    fun getFollowing(username: String) = userRepository.getFollowing(username)

    fun insertFavorite(user: UserEntity) {
        viewModelScope.launch {
            userRepository.insertFavorite(user)
        }
    }

    fun deleteFavorite(user: UserEntity) {
        viewModelScope.launch {
            userRepository.deleteFavorite(user)
        }
    }

    fun getAllFavorited(): LiveData<List<UserEntity>> = userRepository.getAllFavorited()

    fun isUserFavorited(username: String){
        _isFavorited.value = false
        viewModelScope.launch {
            _isFavorited.value = userRepository.isUserFavorited(username)
        }
    }
}