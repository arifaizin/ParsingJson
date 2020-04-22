package com.dicoding.parsingjson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.dicoding.parsingjson.model.DataItem
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
        val queue = Volley.newRequestQueue(this)
        val url = "https://reqres.in/api/users?page=1"
        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                parseJson(response)
            },
            Response.ErrorListener { error ->
                Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_SHORT).show()
                error.printStackTrace()
            })
        queue.add(stringRequest)
    }

    private fun parseJson(response: String) {
        val jsonObject = JSONObject(response);
        val dataArray = jsonObject.getJSONArray("data")

        for (i in 0 until dataArray.length()) {
            val dataObject = dataArray.getJSONObject(i)
            val id = dataObject.getInt("id")
            val firstName = dataObject.getString("first_name")
            val lastName = dataObject.getString("last_name")
            val email = dataObject.getString("email")
            val avatar = dataObject.getString("avatar")

            val data = DataItem(
                id = id,
                firstName = firstName,
                lastName = lastName,
                email = email,
                avatar = avatar
            )

            adapter.addUser(data)
        }
    }
}
