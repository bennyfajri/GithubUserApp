package com.drsync.githubuserapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
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

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Detail User"

        binding.btnShare.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Hey, ${data?.name} has ${data?.follower} on GitHub, check this link to get in touch \n" +
                        " www.github.com/${data?.username}")
            shareIntent.type = "text/plain";
            startActivity(shareIntent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}