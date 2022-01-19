package com.drsync.githubuserapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.drsync.githubuserapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUser.setHasFixedSize(true)

        mainViewModel.searchUser.observe(this@MainActivity, { response ->
            showRecyclerList(response)
        })

        mainViewModel.isLoading.observe(this, { loading ->
            showLoading(loading)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.getSearchUser(query)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    private fun showLoading(loading: Boolean) {
        binding.progress.progressBar.visibility = if(loading) View.VISIBLE else View.GONE
    }

    private fun showRecyclerList(response: ArrayList<RemoteUser>) {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvUser.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvUser.layoutManager = LinearLayoutManager(this)
        }
        val userAdapter = UserAdapter(response)
        binding.rvUser.adapter = userAdapter

        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: RemoteUser) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("data", data)
                startActivity(intent)
            }
        })
    }
}