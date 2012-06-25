package com.frontcast;

import java.io.IOException;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.http.json.JsonHttpParser;
import com.google.api.client.json.jackson.JacksonFactory;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
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
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class ReportActivity extends Activity implements LocationListener {
	
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
	
	private LocationManager lMgr;
	private String best;
	public static final String SERVER_URL = "http://frontcast-server.appspot.com/rpc";
	private static final HttpTransport transport = new ApacheHttpTransport();
	
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
					levelSeekbar.setProgress(40);
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
				new ReportFrontcastTask().execute();
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
					if (progress < 25) {
						descriptionText.setText(getString(R.string.sunny_1));
					} else if (progress < 50) {
						descriptionText.setText(getString(R.string.sunny_2));
					} else if (progress < 75) {
						descriptionText.setText(getString(R.string.sunny_3));
					} else {
						descriptionText.setText(getString(R.string.sunny_4));
					}
					break;
				case CLOUDY:
					if (progress < 25) {
						descriptionText.setText(getString(R.string.cloudy_1));
					} else if (progress < 50) {
						descriptionText.setText(getString(R.string.cloudy_2));
					} else if (progress < 75) {
						descriptionText.setText(getString(R.string.cloudy_3));
					} else {
						descriptionText.setText(getString(R.string.cloudy_4));
					}
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
	
	private class ReportFrontcastTask extends AsyncTask<Void, Void, Void> {
    	private ProgressDialog Dialog = new ProgressDialog(ReportActivity.this);
    	String fb_id;
    	double lat;
    	double lng;
    	String type;
    	int level;
    	
    	@Override
    	protected void onPreExecute() {
    		super.onPreExecute();
    		fb_id = "1091551108";
    		switch(weatherSelected) {
    		case SUNNY:
    			type = "sunny"; break;
    		case CLOUDY:
    			type = "cloudy"; break;
    		case RAINY:
    			type = "rainy"; break;
    		}
    		lat = 25.1789;
    		lng = 121.5252;
    		level = levelSeekbar.getProgress();
    		Dialog.setMessage("Sending your message...");
    		Dialog.show();
    	}
    	
    	
		@Override
		protected Void doInBackground(Void... arg0) {
			String[] data = {"ReportFrontcast", fb_id, String.valueOf(lat), String.valueOf(lng), type, String.valueOf(level)};
			JsonHttpContent json = new JsonHttpContent(new JacksonFactory(), data);
			
			HttpRequestFactory httpRequestFactory = createRequestFactory(transport);
			HttpRequest request;
			try {
				request = httpRequestFactory.buildPostRequest(
						new GenericUrl(SERVER_URL), json);
			System.out.println("request = " + request.getUrl());
			System.out.println("content = " + json.getData().toString());
			String result = request.execute().parseAsString();
			System.out.println("status: " + result);
			return null;
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			return null;
		}
    	
		@Override
		protected void onPostExecute(Void unused) {
			Dialog.dismiss();
			Toast.makeText(ReportActivity.this, "done!", Toast.LENGTH_LONG).show();
		}
    }
	
	public static HttpRequestFactory createRequestFactory(final HttpTransport transport) {
		   
		return transport.createRequestFactory(new HttpRequestInitializer() {
			public void initialize(HttpRequest request) {
			    JsonHttpParser parser = new JsonHttpParser(new JacksonFactory());
				request.addParser(parser);
			}
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

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}
