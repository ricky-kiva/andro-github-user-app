package com.rickyslash.githubuserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rickyslash.githubuserapp.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val detailUserViewModel by viewModels<DetailUserViewModel>()

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_detail_1,
            R.string.tab_detail_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        if (username != null) {
            detailUserViewModel.displayUser(username)
        }

        displayUserInfo()

        val sectionsPagerAdapter = SectionsPagerAdapter(this@DetailUserActivity)

        if (username != null) {
            sectionsPagerAdapter.username = username
        }

        val viewPager: ViewPager2 = binding.vpDetail
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabsDetail

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun displayUserInfo() {
        detailUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailUserViewModel.displayname.observe(this) {
            binding.tvDetailName.text = detailUserViewModel.displayname.value
        }

        detailUserViewModel.username.observe(this) {
            binding.tvDetailUsername.text = detailUserViewModel.username.value
        }

        detailUserViewModel.avatar.observe(this) {
            Glide.with(this@DetailUserActivity)
                .load(detailUserViewModel.avatar.value)
                .into(binding.imgDetailPhoto)
        }

        detailUserViewModel.followers.observe(this) {
            binding.tvDetailFollowers.text = getString(R.string.followers, detailUserViewModel.followers.value)
        }

        detailUserViewModel.following.observe(this) {
            binding.tvDetailFollowing.text = getString(R.string.following, detailUserViewModel.following.value)
        }

        detailUserViewModel.isError.observe(this) {
            connectionErrorCheck(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.tabsDetail.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.vpDetail.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
    }

    private fun connectionErrorCheck(isError: Boolean) {
        if (isError) Toast.makeText(this, "There's an error connecting to the server", Toast.LENGTH_SHORT).show()
    }

}