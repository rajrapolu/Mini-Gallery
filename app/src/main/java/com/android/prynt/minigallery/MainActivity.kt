package com.android.prynt.minigallery
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.animation.AnimationUtils
import com.android.prynt.minigallery.models.Items
import com.android.prynt.minigallery.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    companion object {
        val VIDEO_ADAPTER: Int = 1
        val IMAGE_ADAPTER: Int = 2
        val PAGE_MARGIN: Int = 5
        val OFF_SCREEN_LIMIT: Int = 3
        val TAG: String = MainActivity::class.java.simpleName
    }

    private lateinit var mViewPager: ViewPager
    private lateinit var mViewPagerBottom: ViewPager
    private var galleryList: List<Items>? = null
    private var mImageVideoAdapter: ImageVideoAdapter? = null
    private var mImageAdapter: ImageVideoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewPager = findViewById(R.id.view_pager) as ViewPager

        mViewPagerBottom = findViewById(R.id.view_carousel) as ViewPager
        mViewPagerBottom.clipChildren = false
        mViewPagerBottom.pageMargin = PAGE_MARGIN

        mViewPagerBottom.offscreenPageLimit = OFF_SCREEN_LIMIT

    }

    override fun onStart() {
        super.onStart()
        updateActivity();
    }

    //To update both the fragments
    private fun updateActivity() {
        val retrofitClient = RetrofitClient()

        val callback = object : Callback<List<Items>> {
            override fun onFailure(call: Call<List<Items>>?, t: Throwable?) {
                Log.e(TAG, getString(R.string.network_call_fail) + t)
            }

            override fun onResponse(call: Call<List<Items>>?, response: Response<List<Items>>?) {
                response?.isSuccessful.let {
                    galleryList = response?.body()

                    //Attaching adapter for the top view pager
                    mImageVideoAdapter = ImageVideoAdapter(supportFragmentManager, galleryList,
                            VIDEO_ADAPTER)
                    mViewPager.adapter = mImageVideoAdapter

                    //Attaching adapter for the bottom carousel
                    mImageAdapter = ImageVideoAdapter(supportFragmentManager, galleryList,
                            IMAGE_ADAPTER)
                    mViewPagerBottom.adapter = mImageAdapter
                }
            }
        }

        retrofitClient.getImagesAndVideos(callback)

        //On page chnage listener for the top view pager
        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            private var index = 0

            override fun onPageSelected(position: Int) {
                index = position

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
               mViewPagerBottom.scrollBy((positionOffset).toInt(), 0)
            }

            override fun onPageScrollStateChanged(state: Int) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    mViewPagerBottom.currentItem = index
                }
            }
        })

        //On page listener for the bottom view pager
        mViewPagerBottom.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            private var index = 0

            override fun onPageSelected(position: Int) {
                index = position
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                val width: Int = mViewPagerBottom.width
                mViewPager.scrollTo(((width*(position + positionOffset)).toInt()), 0)
            }

            override fun onPageScrollStateChanged(state: Int) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    mViewPager.currentItem = index
                }
            }
        })
    }

    //To make sure that we move to the previous item in the view pager we ovverride this method
    override fun onBackPressed() {
        if (mViewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            mViewPager.setCurrentItem(mViewPager.currentItem - 1)
            mViewPagerBottom.setCurrentItem(mViewPagerBottom.currentItem - 1)
        }
    }
}
