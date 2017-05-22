package com.android.prynt.minigallery

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.android.prynt.minigallery.models.Items

class ImageVideoAdapter(fm: FragmentManager, galleryList: List<Items>?, adapter: Int) :
        FragmentStatePagerAdapter(fm) {

    var gallery: List<Items> = galleryList!!
    var NUM_ITEMS: Int = gallery.size
    var adapterValue: Int = adapter

    //Conditional statements to decide which fragments to return
    override fun getItem(position: Int): Fragment? {
        if (adapterValue == MainActivity.VIDEO_ADAPTER) {
            return ViewPagerFragment.newInstance(gallery[position].videoUrl)
        } else if (adapterValue == MainActivity.IMAGE_ADAPTER) {
            return BottomCarouselFragment.newInstance(gallery[position].imageUrl)
        } else {
            return null
        }
    }

    override fun getCount(): Int {
        return NUM_ITEMS
    }

}