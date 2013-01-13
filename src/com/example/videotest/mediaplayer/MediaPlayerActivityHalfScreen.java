package com.example.videotest.mediaplayer;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.videotest.R;

public class MediaPlayerActivityHalfScreen extends FragmentActivity {

	private MediaPlayerFragment videoPlayerFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mediaplayer_halfscreen);

		videoPlayerFragment = (MediaPlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_video_player);
	}

	@Override
	protected void onResume() {
		super.onResume();
		videoPlayerFragment.playVideo();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// videoPlayerFragment.pauseVideo();
	}

}
