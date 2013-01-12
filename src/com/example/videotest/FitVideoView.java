package com.example.videotest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.VideoView;

public class FitVideoView extends VideoView {

	private static final String TAG = "TestVideo";

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
		if (MainActivity.overrideOnMeasure) {
			forceToSizeOfParentView(widthMeasureSpec, heightMeasureSpec);
		} else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}

	private void forceToSizeOfParentView(int widthMeasureSpec, int heightMeasureSpec) {
		int width = getDefaultSize(0, widthMeasureSpec);
		int height = getDefaultSize(0, heightMeasureSpec);

		Log.d(TAG, "setting size: " + width + 'x' + height);
		setMeasuredDimension(width, height);
	}
}
