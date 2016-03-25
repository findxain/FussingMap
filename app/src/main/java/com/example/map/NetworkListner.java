package com.example.map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class NetworkListner extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub

		
		
		//Toast.makeText(context, "recieved", 3000).show();
		
		context.stopService(new Intent(context,SyncService.class));
		 ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
		    NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		    
		    NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(     ConnectivityManager.TYPE_MOBILE );
		    if ( activeNetInfo != null )
		    {
		      Toast.makeText( context, "Active Network Type : " + activeNetInfo.getTypeName(), Toast.LENGTH_SHORT ).show();
		      context.startService(new Intent(context,SyncService.class));

		    }
		    if( mobNetInfo != null )
		    {
		      Toast.makeText( context, "Mobile Network Type : " + mobNetInfo.getTypeName(), Toast.LENGTH_SHORT ).show();
		      context.startService(new Intent(context,SyncService.class));

		    }
		
		
		
		
	}

}
