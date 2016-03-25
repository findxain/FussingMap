package com.example.map;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Second extends MainActivity {
ImageView iv,iv2,iv3;
Button b1;
Bitmap bmp;
ArrayList<Uri> uris;
TextView typeofcompalin;
EditText message,location;
double lantitude;
double longnitude;
String image1String,image2String,image3String;

 String addressfromthred="";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		iv=(ImageView) findViewById(R.id.ImageVieww1);
		iv2=(ImageView) findViewById(R.id.ImageVieww2);
		iv3=(ImageView) findViewById(R.id.imageVieww3);
		typeofcompalin=(TextView)findViewById(R.id.textViewTypeofcomplain);
		location=(EditText)findViewById(R.id.editTextlocation);
		
		message=(EditText)findViewById(R.id.editTextMessage);
		
	
		lantitude=getIntent().getDoubleExtra(letitude, 71.5833);
		longnitude=getIntent().getDoubleExtra(Long, 34.0167);
		String toc=""+getIntent().getStringExtra(MainActivity.TypeOfC);
		message.setText(getIntent().getExtras().getString("khani"));
		
		
		typeofcompalin.setText(""+toc);
		
		
		
		
		uris=new ArrayList<Uri>();
		
//		b1=(Button) findViewById(R.id.button1);
//		b1.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public voi	d onClick(View v) {
//				Sendemail();
//				startService(new Intent(Second.this,SyncService.class));
//				
//			}
//		});
		
		iv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(i, 1);
				
			}
		});
		iv2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(i, 2);
				
			}
		});
		iv3.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
					startActivityForResult(i, 3);
		
			}
		});
		
		final ProgressDialog pd = new ProgressDialog(Second.this);
		pd.setMessage("Fetching Address ");
		pd.setCancelable(false);
		pd.show();
		
		Thread t=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			 
				
					try {
						addressfromthred=getaddress(lantitude,longnitude);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						runOnUiThread(new Runnable() {
							public void run() {
								location.setText("Sorry no location found.");
								pd.setMessage("Address Fetching complete");
								
								
							}
						});
					}
					
				
					runOnUiThread(new Runnable() {
						public void run() {
							location.setText(addressfromthred);
							pd.setMessage("Address Fetching complete");
							
							
						}
					});
				
				
				
				
				
				
				
				
				pd.cancel();
			}
		});
		t.start();
		
		

		
	}

	
	@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
			super.onActivityResult(requestCode, resultCode, data);
			
			
			if(resultCode==RESULT_OK){
				
				Bundle extra=data.getExtras();
				bmp=(Bitmap) extra.get("data");
				if(requestCode==1){
					
					iv.setImageBitmap(bmp);
					saveimage(bmp,requestCode);
					image1String=encodeimagetosting(bmp);
					
				}
				if(requestCode==2){
					iv2.setImageBitmap(bmp);
					saveimage(bmp,requestCode);
					image2String=encodeimagetosting(bmp);
				}
				if(requestCode==3){
					iv3.setImageBitmap(bmp);
					saveimage(bmp,requestCode);
					image3String=encodeimagetosting(bmp);
				}
				
				
				
			
				
				
			}
		}

	private void saveimage(Bitmap bmp, int requestCode) {
		// TODO Auto-generated method stub
		
		
		File sdcard=new File("/sdcard/");
		File f=new File(sdcard,requestCode+".png");
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(f);

	        bmp.compress(Bitmap.CompressFormat.PNG, 85, out);
			out.flush();
			out.close();
			uris.add(Uri.fromFile(f));
			//Uri.parse(sdcard+"/"+requestCode+".png")
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
	}

	
	private void Sendemail()
	{
		
		
		for (int i = 0; i < uris.size(); i++) {
			Log.d("Uri", uris.get(i).toString());
		}
		
		Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
		Log.d("Emailll", getIntent().getExtras().getString(MainActivity.Email));
		
		String email=getIntent().getExtras().getString(MainActivity.Email);
		String[] emailss={email};  
		
		emailIntent.putExtra(Intent.EXTRA_EMAIL,emailss);
		  emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Complain About "+typeofcompalin.getText());
		  Log.d("msg", message.getText()+"");
		  Log.d("location", location.getText()+"");
		  
		  
		  Date date=new Date();
		  android.text.format.DateFormat df = new android.text.format.DateFormat();
		String datestring=(String) df.format("yyyy-MM-dd hh:mm:ss", new java.util.Date());
		  String extra_text="\t"+message.getText()+" \n\n Location \t "+location.getText()+"\n \nCordinates \t"+lantitude+","+longnitude+"\n\nTime of Incident \t "+datestring;;
		  
		  emailIntent.putExtra(Intent.EXTRA_TEXT,extra_text  );
		
		
		 emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
		 // emailIntent.putExtra(Intent.EXTRA_STREAM, uris.get(0));
		  emailIntent.setType("text/plain");
		  
		  String locationnnn=location.getText().toString();
		  if(location.getText().toString().equals(""))
		  {
			  locationnnn="Location unavailible";
		  }
		  
		 
		  
		  String[] data={ typeofcompalin.getText()+"",
		  			lantitude+"+"+longnitude+"" ,
		  		locationnnn+"" ,
		  		datestring+"" ,
		  		message.getText()+"" ,
		  		image1String ,
		  		image2String ,
		  		image3String ,
		  		"0"};
		  
		  	SQLiteDatabase db;
			db=openOrCreateDatabase(TakePic.DatabaseName,  MODE_WORLD_WRITEABLE, null);
			
			
			TakePic.CreateDatabaseforMycomplains(data, db);
			
		  
		  
			startActivity(Intent.createChooser(emailIntent, "Select an Email Client:"));
		  
	}
	

	private String encodeimagetosting(Bitmap bitmap) {
		// TODO Auto-generated method stub
		
		
		Bitmap bm =bitmap;
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
	    bm.compress(CompressFormat.PNG, 70, stream);
	    byte[] byteFormat = stream.toByteArray();
	    
	    String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
	    
		Log.d("Bitmap", imgString);
		return imgString;
	}


	protected String getaddress(double latitude,double longnitude) {
		// TODO Auto-generated method stub
		
	
		
		
		String city=null;
		Geocoder gcd=new Geocoder(Second.this);
		ArrayList<Address> add=new ArrayList<Address>();
		try {
			add.addAll(gcd.getFromLocation(latitude, longnitude, 1));
			
			//Toast.makeText(MainActivity.this, add.get(0)+"", Toast.LENGTH_SHORT).show();
			
			if (add != null && add.size() > 0) {
		           Address address = add.get(0);
		            StringBuilder sb = new StringBuilder();
		            for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
		                sb.append(address.getAddressLine(i)).append(" ");
		            }
		            	sb.append(address.getSubLocality());
		            	sb.append(" "+ address.getLocality());
		            	sb.append(" "+address.getSubAdminArea());
		            	sb.append(" " + address.getAdminArea());
		            	sb.append(" "+address.getCountryName());
		           city = sb.toString();
		           city=city.replace(",", " ");
		           city=city.replace("null", " ");
		           return city;
		        }
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Toast.makeText(getApplicationContext(), e.getMessage()+"", Toast.LENGTH_SHORT).show();
		}
		return city;
		
		
		
		
	}
	
	@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.second, menu);
		
			return super.onCreateOptionsMenu(menu);
		}
	@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// TODO Auto-generated method stub
		int id=item.getItemId();
		
		
		if(id==R.id.send)
		{
			Sendemail();
			//startService(new Intent(Second.this,SyncService.class));
		}
		
			return super.onOptionsItemSelected(item);
		}
	
	

}
