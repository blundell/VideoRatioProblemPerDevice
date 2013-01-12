package com.example.videotest;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class VideoActivityHalfScreen extends FragmentActivity {

	private VideoPlayerFragment videoPlayerFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_halfscreen);

		videoPlayerFragment = (VideoPlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_video_player);
	}

	@Override
	protected void onResume() {
		super.onResume();
		videoPlayerFragment.playVideo();
	}

	@Override
	protected void onPause() {
		super.onPause();
		videoPlayerFragment.pauseVideo();
	}

}
