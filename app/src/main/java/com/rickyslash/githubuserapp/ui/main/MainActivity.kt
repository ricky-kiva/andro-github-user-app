package com.rickyslash.githubuserapp.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.rickyslash.githubuserapp.*
import com.rickyslash.githubuserapp.api.response.ItemsItem
import com.rickyslash.githubuserapp.databinding.ActivityMainBinding
import com.rickyslash.githubuserapp.ui.detailuser.DetailUserActivity
import com.rickyslash.githubuserapp.ui.favuser.FavUserActivity
import com.rickyslash.githubuserapp.ui.settings.SettingsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    private fun showUserDetails(data: ItemsItem) {
        val moveUserDetailsIntent = Intent(this@MainActivity, DetailUserActivity::class.java)
        moveUserDetailsIntent.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
        startActivity(moveUserDetailsIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager

        mainViewModel.listUser.observe(this) { userData ->
            setUserData(userData)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.isError.observe(this) {
            connectionErrorCheck(it)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView  as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.searchUsers(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_fav_user -> {
                val intent = Intent(this, FavUserActivity::class.java)
                startActivity(intent)
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

    private fun setUserData(userData: List<ItemsItem>) {
        val usersAdapter = UsersAdapter(userData)
        binding.rvUsers.adapter = usersAdapter

        usersAdapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                showUserDetails(data)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) binding.progressBar.visibility = View.VISIBLE else binding.progressBar.visibility = View.GONE
    }

    private fun connectionErrorCheck(isError: Boolean) {
        if (isError) Toast.makeText(this, "There's an error connecting to the server", Toast.LENGTH_SHORT).show()
    }

}