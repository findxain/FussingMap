package com.example.map;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.IBinder;
import android.util.Log;

public class SyncService extends Service {

	
	
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
	SharedPreferences sharedPreferences;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		
		Log.d("Service Started", "Mubarik ho ");
		sharedPreferences=getSharedPreferences(TakePic.DatabaseName, MODE_WORLD_WRITEABLE);
		
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
		Startthreadforsendingthecomplaintoserver();
		
		
		return super.onStartCommand(intent, flags, startId);
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d("Service Stoped", "Mubarik ho ");
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
		private void Startthreadforsendingthecomplaintoserver() {
			// TODO Auto-generated method stub
			
			
			
			new Thread()
			{
				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
					Log.d("Thread started", "Yes");

					
					for (int i = 0; i< status.size(); i++) {
						if(status.get(i).equals("0"))
						{
							Sendtoserver(i);
							Log.d("Status inside if condition", i+"");
							
						}
						
					}
					Log.d("Thread Stoped", "Yes");
					
					stopSelf();
					
					
					
					
					
				}
			}.start();
			
			
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
		private void Sendtoserver(int position) {
			
			
			prams=new ArrayList<NameValuePair>();
			prams.add(new BasicNameValuePair("toc", typeofcomplains.get(position)));
			prams.add(new BasicNameValuePair("cordinates", cordinated.get(position)));
			prams.add(new BasicNameValuePair("location", location.get(position)));
			prams.add(new BasicNameValuePair("date", date.get(position)));
			prams.add(new BasicNameValuePair("message", message.get(position)));
			
			prams.add(new BasicNameValuePair("pic1", pic1.get(position)));
			prams.add(new BasicNameValuePair("pic2", pic2.get(position)));
			prams.add(new BasicNameValuePair("pic3", pic3.get(position)));
			prams.add(new BasicNameValuePair("contact", sharedPreferences.getString("contact", "Null")));
			
			Log.d("Namevaluew pairs addes to list", "Yes");
			
			
			//String prammmString=URLEncodedUtils.format(prams, "utf-8");
			
			String ip="http://192.168.0.103/map/index.php?";
			Log.d("pramsss", ip);
			
			
			try {
				DefaultHttpClient defaultHttpClient=new DefaultHttpClient();
				HttpEntity httpEntity=null;
				HttpResponse httpResponse=null;
				HttpPost httppost=new HttpPost(ip);
				httppost.setEntity(new UrlEncodedFormEntity(prams));
				
				
				String responce="";
				httpResponse=defaultHttpClient.execute(httppost);

				httpEntity=httpResponse.getEntity();
				responce=EntityUtils.toString(httpEntity);
				Log.d("pramsss", responce);
				JSONObject jsonObject=new JSONObject(responce);
				
				if(jsonObject.getString("success").toString().equals("1"))
				{
					Log.d("Sucess", jsonObject.get("success")+"");
					Log.d("position", position+"");
					Changethestatudofcurrentcomplain(position);
					
					
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	
	
	
}
