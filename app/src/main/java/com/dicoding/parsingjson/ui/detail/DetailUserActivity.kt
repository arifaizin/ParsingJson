package com.dicoding.parsingjson.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.dicoding.parsingjson.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private val detailViewModel by viewModels<DetailUserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail Github User"

        if (intent.getStringExtra(EXTRA_USERNAME) != null) {
            val username = intent.getStringExtra(EXTRA_USERNAME).toString()
            detailViewModel.getDetailByUsername(username)
            detailViewModel.detailUser.observe(this) { user ->
                Glide.with(this)
                    .load(user.avatarUrl)
                    .circleCrop()
                    .into(binding.ivAvatar)
                binding.tvName.text = user.name.toString()
                binding.tvUsername.text = user.login.toString()
                binding.tvFollowers.text = "${user.followers.toString()} Followers"
                binding.tvFollowing.text = "${user.following.toString()} Following"
            }
            detailViewModel.isLoading.observe(this) { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }

    companion object {
        const val EXTRA_USERNAME = "username"
//        @StringRes
//        private val TAB_TITLES = intArrayOf(R.string.title_followers, R.string.title_following)
    }
}