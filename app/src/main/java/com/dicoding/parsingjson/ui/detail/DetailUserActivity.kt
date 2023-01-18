package com.dicoding.parsingjson.ui.detail

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.widget.SearchView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.parsingjson.R
import com.dicoding.parsingjson.data.model.DetailUserResponse
import com.dicoding.parsingjson.databinding.ActivityDetailUserBinding
import com.dicoding.parsingjson.ui.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private var detailUser: DetailUserResponse? = null
    private lateinit var binding: ActivityDetailUserBinding
    private val detailViewModel by viewModels<DetailUserViewModel>(){
        ViewModelFactory.getInstance(application)
    }

    private var favoriteStatus: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail Github User"



        if (intent.getStringExtra(EXTRA_USERNAME) != null) {
            val username = intent.getStringExtra(EXTRA_USERNAME).toString()
            detailViewModel.getDetailByUsername(username)
            detailViewModel.detailUser.observe(this) { user ->
                detailUser = user
                Glide.with(this)
                    .load(user.avatarUrl)
                    .circleCrop()
                    .into(binding.ivAvatar)
                binding.tvName.text = user.name.toString()
                binding.tvUsername.text = username
                binding.tvFollowers.text = "${user.followers.toString()} Followers"
                binding.tvFollowing.text = "${user.following.toString()} Following"
            }
            detailViewModel.isLoading.observe(this) { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
            val sectionsPagerAdapter = SectionsPagerAdapter(this)
            sectionsPagerAdapter.username = username
            binding.viewPager.adapter = sectionsPagerAdapter
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()

            binding.fab.setOnClickListener {
                if (favoriteStatus){
                    detailViewModel.deleteFavoriteUser(detailUser)
                } else {
                    detailViewModel.addFavoriteUser(detailUser)
                }
            }

            detailViewModel.getFavoriteUserByUsername(username).observe(this){ favUser ->
                if (favUser != null){
                    binding.fab.setImageResource(R.drawable.ic_baseline_favorite_24)
                    favoriteStatus = true
                } else {
                    binding.fab.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    favoriteStatus = false
                }
            }
        }
    }

    companion object {
        const val EXTRA_USERNAME = "username"
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.title_followers, R.string.title_following)
    }
}