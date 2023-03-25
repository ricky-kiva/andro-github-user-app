package com.rickyslash.githubuserapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: DetailUserActivity): FragmentStateAdapter(activity) {
    var username = ""

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = DetailFollowsFragment()
        fragment.arguments = Bundle().apply {
            putInt(DetailFollowsFragment.ARG_SECTION_NUMBER, position+1)
            putString(DetailFollowsFragment.ARG_USERNAME, username)
        }
        return fragment
    }

}