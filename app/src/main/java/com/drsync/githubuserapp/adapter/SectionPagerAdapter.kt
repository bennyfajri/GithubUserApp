package com.drsync.githubuserapp.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.drsync.githubuserapp.ui.FollowerFragment
import com.drsync.githubuserapp.ui.FollowingFragment


class SectionPagerAdapter(activity: AppCompatActivity,val username: String): FragmentStateAdapter(activity) {

    companion object{
        const val BUNDLE_TAG = "username"
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> {
                fragment = FollowerFragment()
                fragment.arguments = Bundle().apply {
                    putString(BUNDLE_TAG,username)
                }
            }
            1 -> {
                fragment = FollowingFragment()
                fragment.arguments = Bundle().apply {
                    putString(BUNDLE_TAG, username)
                }
            }
        }
        return fragment as Fragment
    }


    override fun getItemCount(): Int = 2


}