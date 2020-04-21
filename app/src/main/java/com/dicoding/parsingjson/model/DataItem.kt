package com.dicoding.parsingjson.model

import com.google.gson.annotations.SerializedName

    data class DataItem(
        val id: Int? = null,
        val email: String? = null,
        val firstName: String? = null,
        val lastName: String? = null,
        val avatar: String? = null
    )