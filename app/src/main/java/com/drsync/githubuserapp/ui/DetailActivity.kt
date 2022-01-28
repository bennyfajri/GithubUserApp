package com.drsync.githubuserapp.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.drsync.githubuserapp.*
import com.drsync.githubuserapp.adapter.SectionPagerAdapter
import com.drsync.githubuserapp.data.local.UserEntity
import com.drsync.githubuserapp.data.remote.DetailResponse
import com.drsync.githubuserapp.data.remote.RemoteUser
import com.drsync.githubuserapp.databinding.ActivityDetailBinding
import com.drsync.githubuserapp.viewmodels.MainViewModel
import com.drsync.githubuserapp.viewmodels.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    lateinit var binding: ActivityDetailBinding
    private lateinit var mainViewModel: MainViewModel
    private var isFavorited: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<RemoteUser>("data")

        val sectionPagerAdapter = SectionPagerAdapter(this, data?.login.toString())
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this@DetailActivity)
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        mainViewModel.getDetailUser(data?.login.toString())

        mainViewModel.detailUser.observe(this, { detail ->
            setDetailUser(detail)
        })

        mainViewModel.isLoading.observe(this, { loading ->
            showLoading(loading)
        })

        mainViewModel.isUserFavorited(data?.login.toString())

        binding.fabFavorite.setOnClickListener {
            val mUser = UserEntity(
                data?.login.toString(),
                data?.avatarUrl.toString(),
                true
            )
            if (!isFavorited) {
                mainViewModel.insertFavorite(mUser)
                Toast.makeText(
                    applicationContext,
                    "Favorited",
                    Toast.LENGTH_SHORT
                ).show()
                mainViewModel.isUserFavorited(data?.login.toString())
            } else {
                mainViewModel.deleteFavorite(mUser)
                Toast.makeText(
                    applicationContext,
                    "Delete Favorite",
                    Toast.LENGTH_SHORT
                ).show()
                mainViewModel.isUserFavorited(data?.login.toString())
            }
        }

        mainViewModel.isFavorited.observe(this, { favorited ->
            isFavorited = if (favorited) {
                binding.fabFavorite.setImageResource(R.drawable.ic_favorited)
                true
            } else {
                binding.fabFavorite.setImageResource(R.drawable.ic_favorite)
                false
            }
        })



        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Detail User"
        supportActionBar?.elevation = 0f

    }

    private fun showLoading(loading: Boolean) {
        binding.progressBar.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun setDetailUser(detail: DetailResponse) {
        binding.tvUserName.text = detail.login
        binding.tvName.text = detail.name
        if (detail.publicRepos != null) {
            binding.tvRepository.text = detail.publicRepos.toString()
        } else {
            binding.icRepository.visibility = View.GONE
            binding.textRepository.visibility = View.GONE
        }

        if (detail.company != null) {
            binding.tvCompany.text = detail.company
        } else {
            binding.icCompany.visibility = View.GONE
        }

        if (detail.location != null) {
            binding.tvLocation.text = detail.location
        } else {
            binding.icLokasi.visibility = View.GONE
        }

        if (detail.followers != null) {
            binding.tvFollower.text = detail.followers.toString()
        } else {
            binding.tvFollower.text = "0"
        }

        if (detail.following != null) {
            binding.tvFollowing.text = detail.following.toString()
        } else {
            binding.tvFollowing.text = "0"
        }

        Glide.with(this)
            .load(detail.avatarUrl)
            .circleCrop()
            .into(binding.imgDetailPhoto)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}