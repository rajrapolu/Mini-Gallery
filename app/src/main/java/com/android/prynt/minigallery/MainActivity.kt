package com.android.prynt.minigallery

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.android.prynt.minigallery.api.Items
import com.android.prynt.minigallery.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    lateinit var mViewPager: ViewPager
    var galleryList: List<Items>? = null
    var mImageVideoAdapter: ImageVideoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewPager = findViewById(R.id.view_pager) as ViewPager

        var retrofitClient = RetrofitClient()

        val callback = object : Callback<List<Items>> {
            override fun onFailure(call: Call<List<Items>>?, t: Throwable?) {
                Log.i("yes", "failure")
            }

            override fun onResponse(call: Call<List<Items>>?, response: Response<List<Items>>?) {
                response?.isSuccessful.let {
                    galleryList = response?.body()
                    mImageVideoAdapter = ImageVideoAdapter(supportFragmentManager, galleryList)
                    mViewPager.adapter = mImageVideoAdapter
                }
            }

        }

        retrofitClient.getImagesAndVideos(callback)


    }

    override fun onBackPressed() {
        if (mViewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
        }
    }


}
