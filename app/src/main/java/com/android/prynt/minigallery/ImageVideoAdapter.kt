package com.android.prynt.minigallery

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.Log
import com.android.prynt.minigallery.api.Items

class ImageVideoAdapter(fm: FragmentManager, galleryList: List<Items>?) : FragmentStatePagerAdapter(fm) {

    var gallery: List<Items> = galleryList!!
    var NUM_ITEMS: Int = gallery.size

    override fun getItem(position: Int): Fragment? {
        Log.i("yes", gallery[position].videoUrl)
        return ViewPagerFragment.newInstance(gallery[position].videoUrl)
    }

    override fun getCount(): Int {
        return NUM_ITEMS
    }
}