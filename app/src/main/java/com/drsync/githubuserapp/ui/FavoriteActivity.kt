package com.drsync.githubuserapp.ui

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.drsync.githubuserapp.viewmodels.MainViewModel
import com.drsync.githubuserapp.viewmodels.ViewModelFactory
import com.drsync.githubuserapp.adapter.FavoriteAdapter
import com.drsync.githubuserapp.data.local.UserEntity
import com.drsync.githubuserapp.data.remote.RemoteUser
import com.drsync.githubuserapp.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    lateinit var binding: ActivityFavoriteBinding
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvFavorite.setHasFixedSize(true)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this@FavoriteActivity)
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        mainViewModel.getAllFavorited().observe(this, { favorite ->
            showRecyclerList(favorite)
        })

    }

    private fun showRecyclerList(favorite: List<UserEntity>) {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvFavorite.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        }
        val favoriteAdapter = FavoriteAdapter(favorite)
        binding.rvFavorite.adapter = favoriteAdapter

        favoriteAdapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserEntity) {
                val mRemoteUser = RemoteUser(
                    "", "", "", "", data.login,
                    "", "", "", "", "",
                    data.avatarUrl, "", "", false, 0,
                    "", "", ""
                )
                val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                intent.putExtra("data", mRemoteUser)
                startActivity(intent)
            }
        })
    }
}