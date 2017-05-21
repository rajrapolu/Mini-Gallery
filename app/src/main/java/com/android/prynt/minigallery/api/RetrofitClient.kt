package com.android.prynt.minigallery.api

import com.android.prynt.minigallery.models.Gallery
import com.android.prynt.minigallery.models.GalleryList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    private val service : ApiService

    init {
        val Retrofit = Retrofit.Builder()
                .baseUrl("http://private-04a55-videoplayer1.apiary-mock.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        service = Retrofit.create(ApiService :: class.java)
    }

    fun getImagesAndVideos(): Call<List<Items>> {
        //callback: Callback<List<Gallery>>
        val call = service.getItems()
        return call
 //       call.enqueue(callback)
    }

    fun getImagesAndVideos(callback: Callback<List<Items>>) {
        val call = service.getItems()
        call.enqueue(callback)
    }

}