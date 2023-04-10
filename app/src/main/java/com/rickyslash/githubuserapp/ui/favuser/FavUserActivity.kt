package com.rickyslash.githubuserapp.ui.favuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rickyslash.githubuserapp.databinding.ActivityFavUserBinding
import com.rickyslash.githubuserapp.helper.ViewModelFactory
import com.rickyslash.githubuserapp.R
import com.rickyslash.githubuserapp.ui.settings.SettingsActivity

class FavUserActivity : AppCompatActivity() {

    private var _activityFavUserBinding: ActivityFavUserBinding? = null
    private val binding get() = _activityFavUserBinding

    private lateinit var adapter: FavUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityFavUserBinding = ActivityFavUserBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.apply {
            title = getString(R.string.fav_user_activity_title)
            setDisplayHomeAsUpEnabled(true)
        }

        val favUserViewModel = obtainViewModel(this@FavUserActivity)
        favUserViewModel.getAllFavUsers().observe(this) {
            if (it != null) {
                adapter.setListFavUser(it)
            }
        }

        adapter = FavUserAdapter()

        binding?.rvFavUsers?.layoutManager = LinearLayoutManager(this)
        binding?.rvFavUsers?.setHasFixedSize(true)
        binding?.rvFavUsers?.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.dropdown_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.menu_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavUserBinding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavUserViewModel::class.java]
    }
}