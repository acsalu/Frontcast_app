package com.frontcast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class ReportActivity extends Activity {
	
	private TextView promptText;
	private Button sunnyButton;
	private Button cloudyButton;
	private Button rainyButton;
	
	private SeekBar levelSeekbar;
	private Button reportButton;
	private Button cancelButton;
	
	private ImageView weatherSelectedImage;
	private TextView descriptionText;
	
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
		promptText = (TextView) findViewById(R.id.reportPrompt_text);
		sunnyButton = (Button) findViewById(R.id.sunny_button);
		cloudyButton = (Button) findViewById(R.id.cloudy_button);
		rainyButton = (Button) findViewById(R.id.rainy_button);
		
		weatherSelectedImage = (ImageView) findViewById(R.id.weather_image);
		descriptionText = (TextView) findViewById(R.id.descrpition_text);
		levelSeekbar = (SeekBar) findViewById(R.id.level_seekbar);
		reportButton = (Button) findViewById(R.id.report_button);
		cancelButton = (Button) findViewById(R.id.cancel_button);
	}
	
	private void setListeners() {
		OnClickListener weatherOnClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v instanceof Button) {
					Button sender = (Button) v;
					Log.d("report weather", sender.getText().toString());
					if (sender == sunnyButton) {
						weatherSelected = SUNNY;
						weatherSelectedImage.setImageDrawable(getResources().getDrawable(R.drawable.sunny));
					} else if (sender == cloudyButton) {
						weatherSelected = CLOUDY;
						weatherSelectedImage.setImageDrawable(getResources().getDrawable(R.drawable.cloudy));
					} else {
						weatherSelected = RAINY;
						weatherSelectedImage.setImageDrawable(getResources().getDrawable(R.drawable.rainy));
					}
					AnimationSet animation = new AnimationSet(true);
					animation.setInterpolator(new AccelerateDecelerateInterpolator());
					animation.setDuration(500);
					animation.setFillEnabled(true);
					animation.setFillAfter(true);
					
					Display display = getWindowManager().getDefaultDisplay();
					
					showViews();
				}
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
				AnimationSet animation = new AnimationSet(true);
				animation.setInterpolator(new AccelerateDecelerateInterpolator());
				animation.setFillAfter(true);
				animation.setFillEnabled(true);
				TranslateAnimation translateAnimation = null;
				float scale = 1.0f/1.5f;
				ScaleAnimation scaleAnimation = new ScaleAnimation(1, scale, 1, scale);
				
				
				weatherSelected = NONE;
				showViews();
			}
		});
		levelSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				switch(weatherSelected) {
				case SUNNY:
					break;
				case CLOUDY:
					break;
				case RAINY:
					if (progress < 25) {
						descriptionText.setText(getString(R.string.rainy_1));
					} else if (progress < 50) {
						descriptionText.setText(getString(R.string.rainy_2));
					} else if (progress < 75) {
						descriptionText.setText(getString(R.string.rainy_3));
					} else {
						descriptionText.setText(getString(R.string.rainy_4));
					}
					break;
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
			
		});
	}
	

	
	private void showViews() {
		Animation fadeIn = new AlphaAnimation(0, 1);
		fadeIn.setInterpolator(new DecelerateInterpolator());
		fadeIn.setDuration(700);
		
		Animation fadeOut = new AlphaAnimation(1, 0);
		fadeOut.setInterpolator(new AccelerateInterpolator());
		fadeOut.setDuration(150);
		
		if (weatherSelected == NONE) {
			weatherSelectedImage.startAnimation(fadeOut); weatherSelectedImage.setVisibility(View.GONE);
			descriptionText.startAnimation(fadeOut); descriptionText.setVisibility(View.GONE);
			levelSeekbar.startAnimation(fadeOut); levelSeekbar.setVisibility(View.GONE);
			cancelButton.startAnimation(fadeOut); cancelButton.setVisibility(View.GONE);
			reportButton.startAnimation(fadeOut); reportButton.setVisibility(View.GONE);
			promptText.setVisibility(View.VISIBLE); promptText.startAnimation(fadeIn);
			sunnyButton.setVisibility(View.VISIBLE); sunnyButton.startAnimation(fadeIn);
			cloudyButton.setVisibility(View.VISIBLE); cloudyButton.startAnimation(fadeIn);
			rainyButton.setVisibility(View.VISIBLE); rainyButton.startAnimation(fadeIn);
		} else {
			weatherSelectedImage.setVisibility(View.VISIBLE); weatherSelectedImage.startAnimation(fadeIn);
			descriptionText.setVisibility(View.VISIBLE); descriptionText.startAnimation(fadeIn);
			levelSeekbar.setVisibility(View.VISIBLE); levelSeekbar.startAnimation(fadeIn);
			cancelButton.setVisibility(View.VISIBLE); cancelButton.startAnimation(fadeIn);
			reportButton.setVisibility(View.VISIBLE); reportButton.startAnimation(fadeIn);
			promptText.startAnimation(fadeOut); promptText.setVisibility(View.GONE);
			sunnyButton.startAnimation(fadeOut); sunnyButton.setVisibility(View.GONE);
			cloudyButton.startAnimation(fadeOut); cloudyButton.setVisibility(View.GONE);
			rainyButton.startAnimation(fadeOut); rainyButton.setVisibility(View.GONE);
		}
	}
}
