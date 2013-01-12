package com.example.videotest;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.Toast;

public class VideoPlayerFragment extends Fragment {

	private static final String TAG = "VideoPlayer";

	private FitVideoView videoView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_video_player, container, false);

		videoView = (FitVideoView) root.findViewById(R.id.surface);

		return root;
	}

	public void playVideo() {
		Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.test_vid);
		Log.d(TAG, "Uri is: " + uri);
		setVideoLocation(uri);
		if (!videoView.isPlaying()) {
			videoView.start();
		}
	}

	private void setVideoLocation(Uri uri) {
		try {
			videoView.setVideoURI(uri);
		} catch (Exception e) {
			Log.e(TAG, "VideoPlayer uri was invalid", e);
			Toast.makeText(getActivity(), "Not found", Toast.LENGTH_SHORT).show();
		}
	}

	public void pauseVideo() {
		if (videoView.isPlaying()) {
			videoView.pause();
		}
	}
}
