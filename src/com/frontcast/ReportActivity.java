package com.frontcast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;

public class ReportActivity extends Activity {
	
	private Button sunnyButton;
	private Button cloudyButton;
	private Button rainyButton;
	
	private SeekBar levelSeekbar;
	private Button reportButton;
	private Button cancelButton;
	
	
	private int weatherSelected;
	
	public static final int NONE = -1;
	public static final int SUNNY = 1;
	public static final int CLOUDY = 2;
	public static final int RAINY = 3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report);
		findViews();
		setListeners();
	}
	
	private void findViews() {
		sunnyButton = (Button) findViewById(R.id.sunny_button);
		cloudyButton = (Button) findViewById(R.id.cloudy_button);
		rainyButton = (Button) findViewById(R.id.rainy_button);
		
		levelSeekbar = (SeekBar) findViewById(R.id.level_seekbar);
		reportButton = (Button) findViewById(R.id.report_button);
		cancelButton = (Button) findViewById(R.id.cancel_button);
	}
	
	private void setListeners() {
		OnClickListener weatherOnClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				Button sender = (Button) v;
				Log.d("report weather", sender.getText().toString());
				if (sender == sunnyButton) {
					weatherSelected = SUNNY;
				} else if (sender == cloudyButton) {
					weatherSelected = CLOUDY;
				} else {
					weatherSelected = RAINY;
				}
				showViews();
				
				/*
				Animation animation = null;
				animation = new RotateAnimation(0.0f, +360.0f);
				animation.setInterpolator(new AccelerateDecelerateInterpolator());;
				animation.setDuration(3000);
				sender.startAnimation(animation);
				*/
				// Change the UI
				// hide other weathers
				// show the seekbar and textarea
                // show the report button
			}
		};
		
		sunnyButton.setOnClickListener(weatherOnClickListener);
		cloudyButton.setOnClickListener(weatherOnClickListener);
		rainyButton.setOnClickListener(weatherOnClickListener);
		
		reportButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(ReportActivity.this, QueryActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("type", weatherSelected);
				bundle.putInt("level", levelSeekbar.getProgress());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				weatherSelected = NONE;
				showViews();
			}
		});
		
	}
	
	private void showViews() {
		if (weatherSelected == NONE) {
			levelSeekbar.setVisibility(View.GONE);
			cancelButton.setVisibility(View.GONE);
			reportButton.setVisibility(View.GONE);
			sunnyButton.setVisibility(View.VISIBLE);
			cloudyButton.setVisibility(View.VISIBLE);
			rainyButton.setVisibility(View.VISIBLE);
		} else {
			levelSeekbar.setVisibility(View.VISIBLE);
			cancelButton.setVisibility(View.VISIBLE);
			reportButton.setVisibility(View.VISIBLE);
			sunnyButton.setVisibility(View.GONE);
			cloudyButton.setVisibility(View.GONE);
			rainyButton.setVisibility(View.GONE);
		}
	}
}
