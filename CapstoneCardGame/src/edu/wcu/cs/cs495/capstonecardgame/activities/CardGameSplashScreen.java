package edu.wcu.cs.cs495.capstonecardgame.activities;

import java.io.IOException;
import edu.wcu.cs.cs495.capstonecardgame.database.DataBaseHelper;
import edu.wcu.cs.cs495.capstonecardgame.database.DatabaseInterface;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

public class CardGameSplashScreen extends Activity implements DatabaseInterface {

	public final int MAX_DELAY = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(edu.wcu.cs.cs495.capstonecardgame.R.layout.splash_screen_layout);

		DataBaseHelper myDbHelper = new DataBaseHelper(this);

		Handler handler = new Handler();
		final Intent i = new Intent(this, LoginScreen.class);

		long startTime = System.currentTimeMillis();

		try {

			myDbHelper.createDataBase();

		} catch (IOException ioe) {

			throw new Error("Unable to create database");

		}

		myDbHelper.close();

		long time = System.currentTimeMillis() - startTime;
		long delayMillis;
		if (time < MAX_DELAY) {
			delayMillis = MAX_DELAY - time;
		} else {
			delayMillis = 0;
		}
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(i);
				finish();
			}	        	
		}, delayMillis);
	}

	/**
	 * Not implemented yet.  Will hold options. such as change options ect.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public void onStop() {
		super.onStop();

	}
}
