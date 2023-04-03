package com.rickyslash.githubuserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rickyslash.githubuserapp.databinding.ActivityDetailUserBinding
import com.rickyslash.githubuserapp.helper.ViewModelFactory

class DetailUserActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var detailUserViewModel: DetailUserViewModel

    private lateinit var username: String
    private lateinit var avatarUrl: String
    private var isFavorite: Boolean = false

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

        detailUserViewModel = obtainViewModel(this@DetailUserActivity)

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

        binding.btnDetailAddFav.setOnClickListener(this)

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

    private fun checkFavUser(username: String) {
        detailUserViewModel.getFavUserByUsername(username).observe(this) {
            isFavorite = it != null
            val drawableRes = if (it == null) R.drawable.ic_favorite_border_24 else R.drawable.ic_favorite_24
            val drawable = ResourcesCompat.getDrawable(resources, drawableRes, null)
            binding.btnDetailAddFav.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null)
        }
    }

    private fun displayUserInfo() {
        detailUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailUserViewModel.displayname.observe(this) {
            binding.tvDetailName.text = it
        }

        detailUserViewModel.username.observe(this) {
            username = it
            binding.tvDetailUsername.text = it
            checkFavUser(it)
        }

        detailUserViewModel.avatar.observe(this) {
            avatarUrl = it
            Glide.with(this@DetailUserActivity)
                .load(it)
                .into(binding.imgDetailPhoto)
        }

        detailUserViewModel.followers.observe(this) {
            binding.tvDetailFollowers.text = getString(R.string.followers, it)
        }

        detailUserViewModel.following.observe(this) {
            binding.tvDetailFollowing.text = getString(R.string.following, it)
        }

        detailUserViewModel.isError.observe(this) {
            connectionErrorCheck(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.tabsDetail.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.constraintDetailInfo.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
        binding.vpDetail.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailUserViewModel::class.java)
    }

    private fun connectionErrorCheck(isError: Boolean) {
        if (isError) Toast.makeText(this, "There's an error connecting to the server", Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_detail_add_fav -> {
                if (isFavorite) {
                    detailUserViewModel.deleteFavUserByUsername(username)
                } else {
                    detailUserViewModel.insertFavUser(username, avatarUrl)
                }
            }
        }
    }

}