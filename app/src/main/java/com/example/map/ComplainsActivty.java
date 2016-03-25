package com.example.map;

import java.util.ArrayList;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ComplainsActivty extends Activity {

	
	ListView lv;
	ArrayList<String> id;
	ArrayList<String> typeofcomplains;
	ArrayList<String> cordinated;
	ArrayList<String> location;
	ArrayList<String> message;
	ArrayList<String> date;
	ArrayList<String> pic1;
	ArrayList<String> pic2;
	ArrayList<String> pic3;
	ArrayList<String> status;
	ArrayList<NameValuePair> prams;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_complains_activty);
		
		
			getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.back21withoutlogo));
			lv=(ListView)findViewById(R.id.complainlist);
			id=new ArrayList<String>();
			typeofcomplains=new ArrayList<String>();
			cordinated=new ArrayList<String>();
			location=new ArrayList<String>();
			message=new ArrayList<String>();
			date=new ArrayList<String>();
			pic1=new ArrayList<String>();
			pic2=new ArrayList<String>();
			pic3=new ArrayList<String>();
			status=new ArrayList<String>();
			
			
			Getcomplainsfromdatabase();
			
			ArrayAdapter<String> ad=new ArrayAdapter<String>(ComplainsActivty.this, android.R.layout.simple_list_item_1, typeofcomplains);
			lv.setAdapter(ad);
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					ShowalertforThiscomplain(position);
					
				}
			});
			
			//Startthreadforsendingthecomplaintoserver();
			startService(new Intent(ComplainsActivty.this,SyncService.class));
		
		
	}

	private void Changethestatudofcurrentcomplain(int position) {
		// TODO Auto-generated method stub
		position++;
		Log.d("Change the status of current complain number	", position+"");
		String sql="update comlains set status=1 " +
				" where id=" +position+";";
		Log.d("Sql	", sql+"");

		TakePic.db.execSQL(sql);
	}

	

	protected void ShowalertforThiscomplain(int position) {
		// TODO Auto-generated method stub
		Dialog alert=new Dialog(ComplainsActivty.this);
		alert.setTitle("Complain Details");
		alert.setContentView(R.layout.alerdialogview);
		alert.show();
		TextView toc=(TextView)alert.findViewById(R.id.textViewalertTypeofcomplain);
		TextView cordinates=(TextView)alert.findViewById(R.id.textViewalertcordinate);
		TextView location=(TextView)alert.findViewById(R.id.textViewalertlocation);
		TextView date=(TextView)alert.findViewById(R.id.textViewalertdate);
		TextView message=(TextView)alert.findViewById(R.id.textViewalertmessage);
		TextView status=(TextView)alert.findViewById(R.id.textViewalertStatus);
		ImageView image1=(ImageView)alert.findViewById(R.id.ImageView1dialog);
		ImageView image2=(ImageView)alert.findViewById(R.id.imageView2dialog);
		ImageView image3=(ImageView)alert.findViewById(R.id.ImageView3dialog);
		
		
		toc.setText(typeofcomplains.get(position));
		cordinates.setText(cordinated.get(position));
		location.setText(this.location.get(position));
		date.setText(this.date.get(position));
		message.setText(this.message.get(position));
		if(this.status.get(position).equals("0"))
		{
			status.setText("Not Syncronised");
		}else {
			status.setText("Syncronised");
			
		}
		
		image1.setImageBitmap(decodeBase64(this.pic1.get(position)));
		image2.setImageBitmap(decodeBase64(this.pic2.get(position)));
		image3.setImageBitmap(decodeBase64(this.pic3.get(position)));

		//Log.d("Pic 1", this.pic1.get(position));
		
		
	}

	private void Getcomplainsfromdatabase() {
		// TODO Auto-generated method stub
		
		
		
		Cursor c=TakePic.db.rawQuery("select * from "+TakePic.Tablename+";", null);
		while (c.moveToNext()) {
			id.add(c.getString(0));
			typeofcomplains.add(c.getString(1));
			cordinated.add(c.getString(2));
			location.add(c.getString(3));
			date.add(c.getString(4));
			message.add(c.getString(5));
			pic1.add(c.getString(6));
			pic2.add(c.getString(7));
			pic3.add(c.getString(8));
			status.add(c.getString(9));
			
			
		}
		
		
	}
	
	private static Bitmap decodeBase64(String input) 
	{
	    byte[] decodedByte = Base64.decode(input, 0);
	    return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length); 
	}

	
}
