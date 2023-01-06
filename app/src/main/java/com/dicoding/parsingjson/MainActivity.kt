package com.dicoding.parsingjson

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.parsingjson.databinding.ActivityMainBinding
import com.dicoding.parsingjson.model.GithubResponse
import com.dicoding.parsingjson.model.ItemsItem
import com.dicoding.parsingjson.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter(arrayListOf())

        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = adapter

        getUser()
    }

    private fun getUser() {
        val client = ApiConfig.getApiService().getListUsers("arif")
        binding.progressBar.visibility = View.VISIBLE

        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(call: Call<GithubResponse>, response: Response<GithubResponse>) {
                if (response.isSuccessful) {
                    val dataArray = response.body()?.items as List<ItemsItem>
                    binding.rvUsers.adapter = UserAdapter(dataArray)
                    binding.progressBar.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }
}
