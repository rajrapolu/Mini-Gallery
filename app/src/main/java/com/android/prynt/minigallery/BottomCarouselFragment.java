package com.android.prynt.minigallery;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import com.bumptech.glide.Glide;


/**
 * A simple {@link Fragment} subclass.
 */
public class BottomCarouselFragment extends Fragment {

    private ViewPager viewPagerBottom;
    private static final String IMAGE_KEY = "image_key";
    private ImageViewAspectRatio imageView;
    String imageURI;

    public static BottomCarouselFragment newInstance(String imageURL) {

        Bundle args = new Bundle();
        args.putString(IMAGE_KEY, imageURL);

        BottomCarouselFragment fragment = new BottomCarouselFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public BottomCarouselFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_bottom_carousel, container, false);

        Bundle args = getArguments();
        if (args == null) throw new AssertionError();

        imageURI = args.getString(IMAGE_KEY);
        if (imageURI == null) throw new AssertionError();
        imageView = (ImageViewAspectRatio) rootView.findViewById(R.id.image_view);

        Glide.with(getContext()).load(imageURI).into(imageView);

        final Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.zoom_in);

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                imageView.startAnimation(animation);
                return true;
            }
        });

        return rootView;
    }
}
