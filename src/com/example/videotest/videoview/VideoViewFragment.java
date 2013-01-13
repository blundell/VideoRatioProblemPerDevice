package com.example.videotest.videoview;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.MediaController;
import android.widget.Toast;

import com.example.videotest.R;

public class VideoViewFragment extends Fragment {

	private static final String TAG = "VideoPlayer";

	private FitVideoView videoView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_video_view, container, false);

		videoView = (FitVideoView) root.findViewById(R.id.surface);

		return root;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		videoView.setMediaController(new MediaController(getActivity()));
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
		videoView.pause();
	}
}
