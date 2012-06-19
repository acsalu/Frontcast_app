package com.frontcast;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ReportActivity extends Activity {
	
	private Button sunnyButton;
	private Button cloudyButton;
	private Button rainyButton;
	
	
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
	}
	
	private void setListeners() {
		OnClickListener onClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				Button sender = (Button) v;
				Log.d("report weather", sender.getText().toString());
				// Change the UI
				// hide other weathers
				// show the seekbar and textarea
                // show the report button
			}
		};
		
		sunnyButton.setOnClickListener(onClickListener);
		cloudyButton.setOnClickListener(onClickListener);
		rainyButton.setOnClickListener(onClickListener);
	}
}
