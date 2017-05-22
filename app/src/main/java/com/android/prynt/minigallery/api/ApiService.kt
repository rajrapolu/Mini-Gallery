package com.android.prynt.minigallery.api

import com.android.prynt.minigallery.models.Items
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("/pictures")
    fun getItems(): Call<List<Items>>
}