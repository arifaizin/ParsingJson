package com.dicoding.parsingjson.ui.list

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.parsingjson.R
import com.dicoding.parsingjson.databinding.ActivityMainBinding
import com.dicoding.parsingjson.ui.UserAdapter
import com.dicoding.parsingjson.ui.ViewModelFactory
import com.dicoding.parsingjson.ui.detail.DetailUserViewModel
import com.dicoding.parsingjson.ui.favorite.FavoriteUserActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter

    private val mainViewModel by viewModels<MainViewModel>(){
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter(arrayListOf())

        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = adapter

        mainViewModel.uiState.observe(this) { uiState ->
            when(uiState){
                is UserUiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is UserUiState.Success -> {
                    binding.rvUsers.adapter = UserAdapter(uiState.listUser)
                    binding.progressBar.visibility = View.GONE
                    binding.errorText.visibility = View.GONE
                }
                is UserUiState.Error -> {
                    binding.errorText.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.searchUser(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.favorite){
            startActivity(Intent(this@MainActivity, FavoriteUserActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

}
