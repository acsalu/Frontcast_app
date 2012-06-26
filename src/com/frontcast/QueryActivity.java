package com.frontcast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.frontcast.model.Frontcast;
import com.frontcast.model.FrontcastList;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.OverlayItem;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.http.json.JsonHttpParser;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.gson.Gson;




public class QueryActivity extends MapActivity {
	
	private static final String SERVER_URL = "http://frontcast-server.appspot.com/rpc";
	private static final HttpTransport transport = new ApacheHttpTransport();
	
	private AutoCompleteTextView townsName;
	private Button queryButton;
	
	private MapController mapController;
	private MapView mapView;
	private LocationManager locationManager;
	private MyLocationOverlay myLocationOverlay;
	private FrontcastList frontcastlist;
	private FrontcastOverlay sunnyOverlay;
	private FrontcastOverlay cloudyOverlay;
	private FrontcastOverlay rainyOverlay;
<<<<<<< HEAD
	private static final GeoPoint STATION_TAIPEI   = new GeoPoint((int) (25.04192 * 1E6) , (int) (121.516981 * 1E6));
=======
	//private static final GeoPoint STATION_TAIPEI   = new GeoPoint((int) (25.04192 * 1E6) , (int) (121.516981 * 1E6));
	/*private static final String[] sunnyLevelTable  = {"小太陽呵","陽光和煦","烈日當空","汗如雨下"};
	private static final String[] cloudyLevelTable  = {"微風輕拂","太陽探頭","擋住太陽","烏雲密佈"};
	private static final String[] rainyLevelTable  = {"雨如牛毛","綿綿細雨","大雨滂沱","狂風暴雨"};*/
>>>>>>> ded9186f3a0cf48e9212a9f8fce51daad8afae59
	
	@Override
    protected boolean isRouteDisplayed() {
        return false;
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query);
		findViews();
		setListeners();
		townNameAutoCompelete();
		configureMap();
	}
	

	private void configureMap() {
		mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        mapView.setSatellite(false);
        mapView.setTraffic(true);
		mapController = mapView.getController();
		mapController.setZoom(17); // Zoon 1 is world view
		//mapController.animateTo(STATION_TAIPEI);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		myLocationOverlay = new MyLocationOverlay(this, mapView);
		mapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.runOnFirstFix(new Runnable() {
			public void run() {
				mapView.getController().animateTo(myLocationOverlay.getMyLocation());
			}
		});
	}
	
	private void configureOverlays() {
		Drawable sun = getResources().getDrawable(R.drawable.sunny);
		Log.d("sunny_layer", "sun");
		sun.setBounds(0, 0, sun.getMinimumWidth(), sun.getMinimumHeight());
	
		sunnyOverlay = new FrontcastOverlay(sun, QueryActivity.this);
		mapView.getOverlays().add(sunnyOverlay);
		
		Drawable cloud = getResources().getDrawable(R.drawable.cloudy);
		Log.d("cloudy_layer", "cloud");
		cloud.setBounds(0, 0, cloud.getMinimumWidth(), cloud.getMinimumHeight());

		cloudyOverlay = new FrontcastOverlay(cloud, QueryActivity.this);

		
		Drawable rain = getResources().getDrawable(R.drawable.rainy);
		Log.d("rainy_layer", "rain");
		rain.setBounds(0, 0, rain.getMinimumWidth(), rain.getMinimumHeight());

		rainyOverlay = new FrontcastOverlay(rain, QueryActivity.this);
		
		setFrontcasts();
		
		mapView.getOverlays().add(sunnyOverlay);
		mapView.getOverlays().add(cloudyOverlay);
		mapView.getOverlays().add(rainyOverlay);
		mapView.postInvalidate();   //used for non-UI thread to refresh the mapView
	}
	
	private void setFrontcasts() {
		/*Frontcast frontcast0 = frontcastlist.results.get(0);
		GeoPoint location = new GeoPoint((int) (frontcast0.latitude * 1E6), (int) (frontcast0.longitude * 1E6));
		mapController.animateTo(location);*/
		for (Frontcast frontcast : frontcastlist.results) {
			Log.d("frontcast_type", frontcast.type.toString());
			GeoPoint loc = new GeoPoint((int) (frontcast.latitude * 1E6), (int) (frontcast.longitude * 1E6));
			mapController.animateTo(loc);
			int progress = frontcast.level;
			String weatherLevel;
			String rainystring = new String("rainy");
			String sunnystring = new String("sunny");
			String cloudystring = new String("cloudy");
			if(frontcast.type.toString().equals(rainystring)) {
				if (progress < 25) {
					weatherLevel = new String(getString(R.string.rainy_1));
				} else if (progress < 50) {
					weatherLevel = new String(getString(R.string.rainy_2));
				} else if (progress < 75) {
					weatherLevel = new String(getString(R.string.rainy_3));
				} else {
					weatherLevel = new String(getString(R.string.rainy_4));
				}
				rainyOverlay.addOverlay(new OverlayItem(loc, "Rainy", weatherLevel));
    		}
			else if(frontcast.type.toString().equals(cloudystring)) {
				if (progress < 25) {
					weatherLevel = new String(getString(R.string.cloudy_1));
				} else if (progress < 50) {
					weatherLevel = new String(getString(R.string.cloudy_2));
				} else if (progress < 75) {
					weatherLevel = new String(getString(R.string.cloudy_3));
				} else {
					weatherLevel = new String(getString(R.string.cloudy_4));
				}
				cloudyOverlay.addOverlay(new OverlayItem(loc, "Cloudy", weatherLevel));
    		}
			else if(frontcast.type.toString().equals(sunnystring)) {
				if (progress < 25) {
					weatherLevel = new String(getString(R.string.sunny_1));
				} else if (progress < 50) {
					weatherLevel = new String(getString(R.string.sunny_2));
				} else if (progress < 75) {
					weatherLevel = new String(getString(R.string.sunny_3));
				} else {
					weatherLevel = new String(getString(R.string.sunny_4));
				}
				sunnyOverlay.addOverlay(new OverlayItem(loc, "Sunny", weatherLevel));
    		}
			else { Log.d("frontcast_type", "no type match");}
		}
		Log.d("Sunny size", "is " + sunnyOverlay.size());
		Log.d("Cloudy size", "is " + cloudyOverlay.size());
		Log.d("Rainy size", "is " + rainyOverlay.size());
	}
	
	private void setListeners() {
		queryButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (townsName.getText().toString().isEmpty()){
					AlertDialog.Builder builder = new AlertDialog.Builder(QueryActivity.this);
					builder.setMessage("You have to type in something!")
					       .setCancelable(false)
					       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					        	   dialog.cancel();
					           }
					       });
					AlertDialog alert = builder.create();
					alert.show();
				}
				else {
					Log.d("Send towns name", townsName.getText().toString());
					new GetFrontcastsTask().execute();
				}
			}
    	});
	}
	
	private void findViews() {
		townsName = (AutoCompleteTextView) findViewById(R.id.location_input);
		queryButton = (Button) findViewById(R.id.location_query_button);
	}

	
	private void townNameAutoCompelete() {
		String[] towns = getResources().getStringArray(R.array.towns_array);
		ArrayAdapter<String> adapter = 
		        new ArrayAdapter<String>(this, R.layout.list_item, towns);
		townsName.setAdapter(adapter);
		townsName.setThreshold(1);
	}
	
	private class GetFrontcastsTask extends AsyncTask<Void, Void, Void> {
    	private ProgressDialog Dialog = new ProgressDialog(QueryActivity.this);
    	String locationName;
    	FrontcastList locallist;
    	@Override
    	protected void onPreExecute() {
    		super.onPreExecute();
    		Dialog.setMessage("Sending your query...");
    		Dialog.show();
    		
    		locationName = townsName.getText().toString();
    		townsName.setText("");
    	}

		@Override
		protected Void doInBackground(Void... arg0) {
			String[] data = {"GetTestJson"};
			JsonHttpContent json = new JsonHttpContent(new JacksonFactory(), data);
			
			HttpRequestFactory httpRequestFactory = createRequestFactory(transport);
			HttpRequest request;
			try {
<<<<<<< HEAD
				request = httpRequestFactory.buildPostRequest(new GenericUrl(SERVER_URL), json);

				String result = request.execute().parseAsString();
				Log.d("query_weather", result);
				//frontcastlist = request.execute().parseAs(FrontcastList.class);
				//setFrontcasts();
				//Person person = request.execute().parseAs(Person.class);
				//Log.d("query_result", person.get("name").toString());
=======
				request = httpRequestFactory.buildPostRequest(
						new GenericUrl(SERVER_URL), json);
				Gson gson = new Gson();
				InputStream source = request.execute().getContent();
				Reader reader = new InputStreamReader(source);
				locallist = gson.fromJson(reader, FrontcastList.class);
				//locallist = request.execute().parseAs(FrontcastList.class);
				//Log.d("parse results " , result);
>>>>>>> ded9186f3a0cf48e9212a9f8fce51daad8afae59
				return null;
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			return null;
		}
    	
		@Override
		protected void onPostExecute(Void unused) {
			frontcastlist = locallist;
			configureOverlays();
			Dialog.dismiss();
			Toast.makeText(QueryActivity.this, "done!", Toast.LENGTH_SHORT).show();
		}
    }
	
	public class FrontcastOverlay extends ItemizedOverlay<OverlayItem> {
		
		private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
		Context mContext;
		
		public FrontcastOverlay(Drawable defaultMarker, Context context) {
			  super(boundCenterBottom(defaultMarker));
			  populate();
			  mContext = context;
		}

		@Override
		protected OverlayItem createItem(int i) {
		  return mOverlays.get(i);
		}

		@Override
		public int size() {
		  return mOverlays.size();
		}
		
		public void addOverlay(OverlayItem overlay) {
		    mOverlays.add(overlay);
		    populate();
		}
		
		@Override
		protected boolean onTap(int index) {
		  OverlayItem item = mOverlays.get(index);
		  Toast.makeText(QueryActivity.this, item.getTitle() + "\n" + 
				                             item.getSnippet() 
				                             , Toast.LENGTH_LONG).show();
		  
		  return true;
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
	
	
}
