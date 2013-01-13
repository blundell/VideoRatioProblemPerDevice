package com.example.videotest.mediaplayer;

import android.app.AlertDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;

import com.example.videotest.MainActivity;
import com.example.videotest.R;

public class MediaPlayerFragment extends Fragment implements MediaPlayer.OnPreparedListener, MediaPlayer.OnVideoSizeChangedListener,
		SurfaceHolder.Callback {

	private static final String TAG = "Test";

	private int width = 0;
	private int height = 0;
	private MediaPlayer player;
	private TappableSurfaceView surface;
	private SurfaceHolder holder;
	private boolean mIsVideoReadyToBePlayed = false;
	private boolean mIsVideoSizeKnown = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_media_player, container, false);

		surface = (TappableSurfaceView) root.findViewById(R.id.surface);

		return root;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		holder = surface.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public void playVideo() {
		if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
			startVideoPlayback();
		} else {
			Log.e(TAG, "not ready to playback yet");
		}
	}

	private void startVideoPlayback() {
		holder.setFixedSize(width, height);
		try {
			if (!player.isPlaying()) {
				player.start();
			}
		} catch (Throwable t) {
			goBlooey(t);
		}
	}

	private void goBlooey(Throwable t) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Exception!").setMessage(t.toString()).setPositiveButton("OK", null).show();
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		Log.i(TAG, "prepared");

		if (MainActivity.applyFix) {
			applyFix();
		}

		mIsVideoReadyToBePlayed = true;
		playVideo();
	}

	private void applyFix() {
		int videoWidth = 853;// mp.getVideoWidth();
		int videoHeight = 480; // mp.getVideoHeight();
		Log.d(TAG, "Video w:" + videoWidth + " h:" + videoHeight);

		float videoRatio = (float) videoWidth / (float) videoHeight;
		Log.d(TAG, "Video Proportion: " + videoRatio);

		int fragmentWidth = getView().getWidth();
		int fragmentHeight = getView().getHeight();
		Log.d(TAG, "Fragment w:" + fragmentWidth + " h:" + fragmentHeight);

		float fragmentRatio = (float) fragmentWidth / (float) fragmentHeight;
		Log.d(TAG, "Fragment Proportion: " + fragmentRatio);

		android.view.ViewGroup.LayoutParams lp = surface.getLayoutParams();

		float widthRatio = (float) fragmentWidth / (float) videoWidth;
		float heightRatio = (float) fragmentHeight / (float) videoHeight;

		float minRatio = Math.min(widthRatio, heightRatio);

		Log.d(TAG, "Min ratio: " + minRatio);

		lp.width = (int) (videoWidth * minRatio);
		lp.height = (int) (videoHeight * minRatio);

		Log.d(TAG, "New w:" + lp.width + " h:" + lp.height);
		surface.setLayoutParams(lp);

		Log.d(TAG, "setting Width: " + lp.width + " Height: " + lp.height);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.i(TAG, "surfaceCreated");
		setupMediaPlayer();
	}

	private void setupMediaPlayer() {
		try {
			if (player == null) {
				player = new MediaPlayer();
				player.setScreenOnWhilePlaying(true);
			} else {
				try {
					player.stop();
				} catch (Exception e) {
					Log.i(TAG, "Video not in correct state when stop() called. " + e.getMessage());
				}
				player.reset();
			}
			player.setDataSource(getActivity(), Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.test_vid));
			player.setDisplay(holder);
			player.prepare();
			player.setLooping(true);
			player.setOnPreparedListener(this);
			player.setOnVideoSizeChangedListener(this);
			player.setAudioStreamType(AudioManager.STREAM_MUSIC);

		} catch (Throwable t) {
			Log.e(TAG, "Exception in playVideo", t);
			goBlooey(t);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// nada
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// nada
	}

	@Override
	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		if (width == 0 || height == 0) {
			Log.e(TAG, "invalid video width(" + width + ") or height(" + height + ")");
			return;
		}
		mIsVideoSizeKnown = true;
		this.width = width;
		this.height = height;
		playVideo();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (player != null) {
			player.release();
			player = null;
		}
		width = 0;
		height = 0;
		mIsVideoReadyToBePlayed = false;
		mIsVideoSizeKnown = false;
	}
}
