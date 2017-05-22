package com.android.prynt.minigallery

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

class AspectRatioFrameLayout: FrameLayout {
    val ASPECT_RATIO_DEFORMATION_FRACTION: Float = 0.01f
    private var videoAspectRatio: Float = 0f

    //Constructors for the class
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    //Method to set aspect ratio and request layout
    fun setAspectRatio(widthheightRatio: Float): Unit {
            if (this@AspectRatioFrameLayout.videoAspectRatio != widthheightRatio) {
                this@AspectRatioFrameLayout.videoAspectRatio = widthheightRatio
                requestLayout()
            }
    }

    //Method to prepare a frame layout with an aspect ratio
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (videoAspectRatio == 0f) {
            return
        }

        var videoWidth: Int = measuredWidth
        var videoHeight: Int = measuredHeight
        val modifiedAspectRatio: Float = (width/height).toFloat()
        val aspectRatioDeformation: Float = (videoAspectRatio / modifiedAspectRatio) - 1
        if (aspectRatioDeformation <= ASPECT_RATIO_DEFORMATION_FRACTION) {
            return
        }

        if (aspectRatioDeformation > 0) {
            videoHeight = (width / videoAspectRatio).toInt()
        } else {
            videoWidth = (height * videoAspectRatio).toInt()
        }

        super.onMeasure(View.MeasureSpec.makeMeasureSpec(videoWidth, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(videoHeight, View.MeasureSpec.EXACTLY))
    }
}