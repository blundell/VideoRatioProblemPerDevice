package com.example.videotest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class MainActivity extends FragmentActivity {

	public static boolean overrideOnMeasure = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViewById(R.id.button_video_player_fullscreen).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, VideoActivityFullScreen.class);
				startActivity(intent);
			}
		});

		findViewById(R.id.button_video_player_halfscreen).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, VideoActivityHalfScreen.class);
				startActivity(intent);
			}
		});

		CheckBox checkboxToggleFix = (CheckBox) findViewById(R.id.checkbox_toggle_fix);
		checkboxToggleFix.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				overrideOnMeasure = isChecked;
			}
		});
	}
}
