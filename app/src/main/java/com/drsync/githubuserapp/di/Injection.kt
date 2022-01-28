package com.drsync.githubuserapp.di

import android.content.Context
import com.drsync.githubuserapp.data.local.UserDatabase
import com.drsync.githubuserapp.data.remote.ApiConfig
import com.drsync.githubuserapp.repository.UserRepository

object Injection {
    fun provideRepository(context: Context) : UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = UserDatabase.getInstance(context)
        val dao = database.userDao()
        return UserRepository.getInstance(apiService, dao)
    }
}