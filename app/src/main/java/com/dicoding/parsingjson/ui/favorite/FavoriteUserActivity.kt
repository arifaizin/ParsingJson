package com.dicoding.parsingjson.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.parsingjson.data.model.ItemsItem
import com.dicoding.parsingjson.databinding.ActivityFavoriteUserBinding
import com.dicoding.parsingjson.ui.UserAdapter
import com.dicoding.parsingjson.ui.ViewModelFactory
import com.dicoding.parsingjson.ui.detail.DetailUserViewModel

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var adapter: UserAdapter

    private val viewModel by viewModels<FavoriteUserViewModel>(){
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter(arrayListOf())

        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = adapter

        viewModel.getFavoriteUser().observe(this) { users ->
            val items = arrayListOf<ItemsItem>()
            users.map {
                val item = ItemsItem(login = it.username, avatarUrl = it.avatarUrl)
                items.add(item)
            }
            binding.rvUsers.adapter = UserAdapter(items)
        }
    }
}