package com.dicoding.parsingjson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.parsingjson.model.DataItem
import com.dicoding.parsingjson.model.ResponseUser
import com.google.gson.Gson
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = UserAdapter(mutableListOf())

        rv_users.setHasFixedSize(true)
        rv_users.layoutManager = LinearLayoutManager(this)
        rv_users.adapter = adapter

        getUser()
    }

    private fun getUser() {
        val client = AsyncHttpClient()
        val url = "https://reqres.in/api/users?page=1"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                val response = String(responseBody)
                parseJson(response)
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_SHORT).show()
                error.printStackTrace()
            }
        })
    }

    private fun parseJson(response: String) {
        val gson = Gson()
        val responseUser = gson.fromJson(response, ResponseUser::class.java)
        val dataArray = responseUser.data as List<DataItem>
        for (data in dataArray) {
            adapter.addUser(data)
        }
    }
}
