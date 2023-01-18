package com.dicoding.parsingjson.ui.list

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.parsingjson.databinding.ItemListUserBinding
import com.dicoding.parsingjson.data.model.ItemsItem
import com.dicoding.parsingjson.ui.detail.DetailUserActivity

class UserAdapter(private val users: List<ItemsItem>) :
    RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(users[position])
    }

    inner class ListViewHolder(private val binding: ItemListUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .circleCrop()
                    .into(binding.itemAvatar)

                itemName.text = user.login
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_USERNAME, user.login)
                itemView.context.startActivity(intent)
            }
        }
    }
}