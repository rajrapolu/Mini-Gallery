package com.android.prynt.minigallery;

import android.content.Context;
import android.util.AttributeSet;

public class ImageViewAspectRatio extends android.support.v7.widget.AppCompatImageView {
    private static float IMAGE_ASPECT_RATIO = 3/2;

    public ImageViewAspectRatio(Context context) {
        super(context);
    }

    public ImageViewAspectRatio(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewAspectRatio(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //Defining the aspect ratio for the image view
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int threeTwoHeight = (int) (MeasureSpec.getSize(widthMeasureSpec) * IMAGE_ASPECT_RATIO);
        int threeTwoHeightSpec = MeasureSpec.makeMeasureSpec(threeTwoHeight, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, threeTwoHeightSpec);

    }
}
