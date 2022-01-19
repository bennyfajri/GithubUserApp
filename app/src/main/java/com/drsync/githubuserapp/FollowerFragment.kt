package com.drsync.githubuserapp

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.drsync.githubuserapp.SectionPagerAdapter.Companion.BUNDLE_TAG
import com.drsync.githubuserapp.databinding.FragmentFollowerBinding
import java.util.ArrayList

class FollowerFragment : Fragment() {

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel by viewModels<MainViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(BUNDLE_TAG)

        mainViewModel.getFollower(username.toString())

        mainViewModel.follower.observe(viewLifecycleOwner, {response ->
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
            binding.rvFollower.layoutManager = GridLayoutManager(requireContext(), 2)
        } else {
            binding.rvFollower.layoutManager = LinearLayoutManager(requireContext())
        }
        val userAdapter = UserAdapter(response)
        binding.rvFollower.adapter = userAdapter

        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: RemoteUser) {
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra("data", data)
                startActivity(intent)
            }
        })
    }
}