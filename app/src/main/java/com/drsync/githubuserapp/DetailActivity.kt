package com.drsync.githubuserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.drsync.githubuserapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<User>("data")

        binding.tvCompany.text = data?.company
        binding.tvFollower.text = data?.follower
        binding.tvFollowing.text = data?.following
        binding.tvLocation.text = data?.location
        binding.tvName.text = data?.name
        binding.tvUserName.text = data?.username
        binding.tvRepository.text = data?.repository
        Glide.with(applicationContext)
            .load(data?.avatar)
            .circleCrop()
            .into(binding.imgDetailPhoto)
    }
}