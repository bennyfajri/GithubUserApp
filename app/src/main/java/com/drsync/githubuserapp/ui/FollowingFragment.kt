package com.drsync.githubuserapp.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.drsync.githubuserapp.viewmodels.MainViewModel
import com.drsync.githubuserapp.viewmodels.ViewModelFactory
import com.drsync.githubuserapp.adapter.SectionPagerAdapter
import com.drsync.githubuserapp.adapter.UserAdapter
import com.drsync.githubuserapp.data.remote.RemoteUser
import com.drsync.githubuserapp.databinding.FragmentFollowingBinding
import java.util.*

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(SectionPagerAdapter.BUNDLE_TAG)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireContext())
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        mainViewModel.getFollowing(username.toString())

        mainViewModel.following.observe(viewLifecycleOwner, {response ->
            showFollowerData(response)
        })

        mainViewModel.isLoading.observe(this, { loading ->
            showLoading(loading)
        })

    }

    private fun showLoading(loading: Boolean) {
        binding.progressBar.progressBar.visibility = if(loading) View.VISIBLE else View.GONE
    }

    private fun showFollowerData(response: ArrayList<RemoteUser>) {
        if (requireContext().resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvFollowing.layoutManager = GridLayoutManager(requireContext(), 2)
        } else {
            binding.rvFollowing.layoutManager = LinearLayoutManager(requireContext())
        }
        val userAdapter = UserAdapter(response)
        binding.rvFollowing.adapter = userAdapter

        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: RemoteUser) {
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra("data", data)
                startActivity(intent)
            }
        })
    }

}