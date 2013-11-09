package edu.wcu.cs.cs495.capstonecardgame.activities;

import edu.wcu.cs.cs495.capstonecardgame.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class CreateAccount extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_account);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_account, menu);
		return true;
	}
	
	public void submit_clicked(View b) {
		//TODO: Check field data and submit to credential storage on server. 
		this.finish();
	}
}
