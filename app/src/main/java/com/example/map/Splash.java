package com.example.map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

public class Splash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
	
		
		Thread timer = new Thread() {
			@Override
			public void run() {
				try {

					sleep(6000);

				} catch (InterruptedException d) {
				}

				finally {

					Intent n = new Intent(getApplicationContext(),
						TakePic.class);
					startActivity(n);
					Splash.this.finish();
				}

			}
		};
		timer.start();

		
	}


}
