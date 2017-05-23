package com.android.prynt.minigallery;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


public class ViewPagerFragment extends Fragment {

    private static final String VIDEO_KEY = "video_key";
    private String videoURI;
    private SimpleExoPlayer player;
    private SimpleExoPlayerView playerView;
    private boolean playWhenReady;
    private int currentWindow;
    private long playbackPosition;
    private FrameLayout frameLayout;

    //To create a new instance of the fragment
    public static ViewPagerFragment newInstance(String videoURL) {

        Bundle args = new Bundle();
        args.putString(VIDEO_KEY, videoURL);

        ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_view_pager, container, false);

        Bundle args = getArguments();
        if (args == null) throw new AssertionError();

        videoURI = args.getString(VIDEO_KEY);
        if (videoURI == null) throw new AssertionError();

        playerView = (SimpleExoPlayerView) rootView.findViewById(R.id.video_view);
        frameLayout = (FrameLayout) rootView.findViewById(R.id.aspect_ratio);

        //Masking the view using a rectangle on all the sides
        View rectangle = new RectView(getContext());
        frameLayout.addView(rectangle);

        return rootView;
    }

    //Initializing the player in on start or on resume based on the api level
    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    //Method to initialize the player
    private void initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        playerView.setPlayer(player);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        playerView.setUseController(true);

        Uri uri = Uri.parse(videoURI);
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);

    }



    //Method to get a media source
    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri,
                new DefaultHttpDataSourceFactory("ua"),
                new DefaultExtractorsFactory(), null, null);
    }

    //We release the player based on the api level
    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    //Method to release the player
    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }


    //Class used to create rectangles
    private class RectView extends View {

        public RectView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            int x = getWidth();
            int y = getHeight();

            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            canvas.drawRect(0f, 0f, x, y/5, paint);
            canvas.drawRect(0f, 0f, x/(10/3), y, paint);
            canvas.drawRect((7*x)/10, 0f, x, y, paint);
            canvas.drawRect(0f, (y*4)/5, x, y, paint);
        }
    }
}
