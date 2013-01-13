package com.example.videotest.videoview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.VideoView;

import com.example.videotest.MainActivity;

public class FitVideoView extends VideoView {

	private final int mVideoWidth = 853;
	private final int mVideoHeight = 480;

	public FitVideoView(Context context) {
		super(context);
	}

	public FitVideoView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FitVideoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (MainActivity.applyFix) {
			applyFix(widthMeasureSpec, heightMeasureSpec);
		} else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}

	private void applyFix(int widthMeasureSpec, int heightMeasureSpec) {
		Log.d("TAG", "onMeasure");
		int width = getDefaultSize(mVideoWidth, widthMeasureSpec);
		int height = getDefaultSize(mVideoHeight, heightMeasureSpec);
		if (mVideoWidth > 0 && mVideoHeight > 0) {
			if (mVideoWidth * height > width * mVideoHeight) {
				Log.d("TAG", "image too tall, correcting");
				height = width * mVideoHeight / mVideoWidth;
			} else if (mVideoWidth * height < width * mVideoHeight) {
				Log.d("TAG", "image too wide, correcting");
				width = height * mVideoWidth / mVideoHeight;
			} else {
				Log.d("TAG", "aspect ratio is correct: " + width + "/" + height + "=" + mVideoWidth + "/" + mVideoHeight);
			}
		}
		Log.d("TAG", "setting size: " + width + 'x' + height);
		setMeasuredDimension(width, height);
	}
}
