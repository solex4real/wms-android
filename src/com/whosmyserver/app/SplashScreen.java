package com.whosmyserver.app;

import java.util.List;

import com.whosmyserver.helper.sqliteData;
import com.whosmyserver.model.userData;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashScreen extends Activity {

	private final int SPLASH_DISPLAY_LENGHT = 1000;

	Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		
		sqliteData db = new sqliteData(this);
		intent = new Intent(SplashScreen.this,
				SigninActivity.class);

		try {
			List<userData> userinfo = db.getAllUserData();
			userData userdata = userinfo.get(0);
			String status = userdata.getStatus();
			if (status.equals("1")) {
				// start runnable
				intent = new Intent(SplashScreen.this,
						MainActivity.class);
			}else{
				intent = new Intent(SplashScreen.this,
						SigninActivity.class);
			}

		} catch (Exception e) {
			Log.e("Error from data base", e.toString());
			intent = new Intent(SplashScreen.this,
					SigninActivity.class);

		}
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				
				startActivity(intent);
				// close this activity
				finish();
			}
		}, SPLASH_DISPLAY_LENGHT);

	}

}
