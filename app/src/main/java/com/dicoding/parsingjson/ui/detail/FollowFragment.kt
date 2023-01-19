package com.dicoding.parsingjson.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.parsingjson.databinding.FragmentFollowBinding
import com.dicoding.parsingjson.data.model.ItemsItem
import com.dicoding.parsingjson.ui.ViewModelFactory
import com.dicoding.parsingjson.ui.UserAdapter


class FollowFragment : Fragment() {
    private var position: Int? = null
    private var username: String? = null
    private lateinit var binding: FragmentFollowBinding
    private val detailViewModel by viewModels<DetailUserViewModel>{
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        if (position == 1){
            detailViewModel.getFollower(username.toString())
            detailViewModel.listFollowers.observe(viewLifecycleOwner) {
                showRecycler(it)
            }
        } else {
            detailViewModel.getFollowing(username.toString())
            detailViewModel.listFollowing.observe(viewLifecycleOwner) {
                showRecycler(it)
            }
        }
        detailViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun showRecycler(listItem: List<ItemsItem>) {
        val adapter = UserAdapter(listItem)
        binding.rvFollow.setHasFixedSize(true)
        binding.rvFollow.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollow.adapter = adapter
    }


    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }
}