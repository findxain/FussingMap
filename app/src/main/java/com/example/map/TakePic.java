	package com.example.map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TakePic extends Activity {
Button b1,b2,mycomplains;
public static String DatabaseName="complains";
public static String Tablename="comlains";
public static String Type_op_complain="type_op_complain";
public static String Cordinates="cordinates";
public static String Location="location";
public static String Date="date";
public static String Message="message";
public static String Pic1="pic1";
public static String Pic2="pic2";
public static String Pic3="pic3";
public static SQLiteDatabase db;
public static String Status="status";
private boolean ContactAdded;

SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_pic);
		getActionBar().setTitle("Welcome");
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.titlebarbackground));
		
		sharedPreferences=getSharedPreferences(DatabaseName, MODE_WORLD_WRITEABLE);
		
		ContactAdded=sharedPreferences.getBoolean("key", false);
		//Toast.makeText(getApplicationContext(), sharedPreferences.getString("contact", "nothing"), 3000).show();
		if(!ContactAdded)
		{
			AddContact();
			
		}
		
		
		b1=(Button) findViewById(R.id.map);
		mycomplains=(Button)findViewById(R.id.mycomplains);
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent n = new Intent(getApplicationContext(),
						MainActivity.class);
					startActivity(n);
					
				
			}
		});
		b2=(Button)findViewById(R.id.exit);
		b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TakePic.this.finish();
			}
		});
		mycomplains.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(TakePic.this,ComplainsActivty.class));
			}
		});
		db=openOrCreateDatabase(DatabaseName,  MODE_WORLD_WRITEABLE, null);
		initializetable();
		String[] complaindadada={"1","1","1","1","1","1","1","1","0"};
		
		
		
		
	}
	

	private void AddContact() {
		// TODO Auto-generated method stub
		
		AlertDialog.Builder alert=new AlertDialog.Builder(TakePic.this);
		alert.setTitle("Please Enter Your Contact.");
		final EditText ed=new EditText(TakePic.this);
		ed.setInputType(InputType.TYPE_CLASS_NUMBER);
		
		
		
		alert.setView(ed);
		alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				// TODO Auto-generated method stub
				if(!(ed.getText().length()>11))
				{
				SharedPreferences.Editor editor=sharedPreferences.edit();
				editor.putBoolean("key",true);
				editor.putString("contact", ed.getText().toString());
				editor.commit();
				dialog.dismiss();
				}else {
					Toast.makeText(getApplicationContext(), "Invelid Number", 3000).show();
				}
			}
		});
		
		alert.show();
		alert.setCancelable(false);
		
	}


	private void initializetable() {
		// TODO Auto-generated method stub
		db.execSQL("Create table if not exists "+Tablename+" " +
				"(" +
				"id INTEGER PRIMARY KEY AUTOINCREMENT," +
				Type_op_complain+" varchar," +
				Cordinates+" varchar," +
				Location+" varchar," +
				Date+" varchar," +
				Message+" varchar," +
				Pic1+" varchar," +
				Pic2+" varchar," +
				Pic3+" varchar," +
				Status+" varchar" +
				"); ");
	}

	public static void CreateDatabaseforMycomplains(String[] complaindata, SQLiteDatabase db ) {
		// TODO Auto-generated method stub
		
		
		db.execSQL("insert into "+Tablename+" (" +
				Type_op_complain +","+
				Cordinates+"," +
				Location+"," +
				Date+"," +
				Message+"," +
				Pic1+"," +
				Pic2+"," +
				Pic3+"," +
				Status+"" +
				") values('" +
				complaindata[0]+"','" +
				complaindata[1]+"','" +
				complaindata[2]+"','" +
				complaindata[3]+"','" +
				complaindata[4]+"','" +
				complaindata[5]+"','" +
				complaindata[6]+"','" +
				complaindata[7]+"','" +
				complaindata[8]+"'" +
				");");
		
	}
}
