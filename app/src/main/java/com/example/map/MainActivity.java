package com.example.map;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity  {

	
	


	GoogleMap map;
	
	
	String lan,lon;
	public static String letitude="letitude";
	public static String Long="Long";
	CharSequence[] items={"Ditch","Garbage","Accident","Food","Restaurant","Hospital","Theft","Corruption","Flood","Incident"};
	String[] emails={"findxain@gmail.com","findxain@gmail.com","findxain@gmail.com","findxain@gmail.com","findxain@gmail.com","findxain@gmail.com","findxain@gmail.com","findxain@gmail.com"};
	
	String[] khaniyan={"There is a Ditch on this location which is disturbing people. Kindly take some serious action to repair it.",
	"There is some Garbage on the given location which is effecting the environment. Please take some serious action to remove it.",
	"There is an Accident happend on the given location. There are some casualties. Please send rescue team ",
	"There are some unhygienic food sellers on the given location please take some serious action.",
	"There is a resturent on the given location and they are not facilitating their customers well. Kindly keep check on the resturent.",
	"There is a hospital on the given location and the staff of the hspital are not fulfilling there duties. Kindly keep an eye on them because the people of the city are distubing.",
	"An insident of theft has been occur on the given location. Kindly its a request to the police to take some action.",
	"There is some curruption going on an officer is doing curruption on the given location. Kindly take some serious action against the officer."
	
	};
	
	
	public static String TypeOfC="typeofcomplain";
	public static final String Email = "Email";


	public static final String Khani = "khani";
	RelativeLayout rl;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//rl=(RelativeLayout)findViewById(R.id.layout);
			
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.titlebarbackground));
		
		
		
        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		
		LocationManager lm=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
		{
			buildAlertMessageNoGps();
		}
		
		
		
		
		
			try {
				map.setMyLocationEnabled(true);
				map.getUiSettings().setCompassEnabled(true);
				map.getUiSettings().setMyLocationButtonEnabled(true);
				map.getUiSettings().setMapToolbarEnabled(true);
				map.getUiSettings().setZoomControlsEnabled(true);
				map.setOnMapClickListener(new OnMapClickListener() {

					@Override
					public void onMapClick(final LatLng arg0) {
						// TODO Auto-generated method stub
						ComplainList(arg0);

					}

				});
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		
		
	}

	

	protected void Startnewactivityforsendingemail(LatLng arg0, int typeofcoplain) {
		// TODO Auto-generated method stub
		
		lan=arg0.latitude+"";
		lon=arg0.longitude+"";
		//Log.d("reached", "1");
		MarkerOptions mo=new MarkerOptions();
		//Log.d("reached", "2");
		LatLng lll=new LatLng(Double.parseDouble(lan), Double.parseDouble(lon));
		mo.position(lll);
		//Log.d("reached", "3");
		//Marker marker=map.addMarker(mo);
		
		
		Intent i=new Intent(MainActivity.this,Second.class);
		i.putExtra(letitude,arg0.latitude);
		i.putExtra(Long,arg0.longitude);
		i.putExtra(TypeOfC, items[typeofcoplain]);
		i.putExtra(Email, emails[typeofcoplain]);
		i.putExtra(Khani, khaniyan[typeofcoplain]);
		//Log.d("reached", "4");
		startActivity(i);
		//Log.d("reached", "huraa");
	}

	
	private void buildAlertMessageNoGps() {
	    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
	    builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
	           .setCancelable(false)
	           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	               public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
	                   startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
	               }
	           })
	           .setNegativeButton("No", new DialogInterface.OnClickListener() {
	               public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
	                    dialog.cancel();
	               }
	           });
	    final AlertDialog alert = builder.create();
	    alert.show();
	}
	
	
	
	
	private void ComplainList(final LatLng arg0)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(
				MainActivity.this);

		builder.setTitle("Select Type of Complain")
				.setItems(items,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(
									DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								Startnewactivityforsendingemail(
										arg0, which);

							}
						})

				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialog, int id) {
								// User cancelled the dialog
							}
						});

		builder.show();

	}
	
	
}
