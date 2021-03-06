package edu.wcu.cs.cs495.capstonecardgame.activities;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import edu.wcu.cs.cs495.capstonecardgame.R;
import edu.wcu.cs.cs495.capstonecardgame.network.CardGameClient;

public class LoginScreen extends Activity {

	private static final Object BAD = "bad";

	/** TextView for the user to enter their username. */
	private TextView usernameTextView;
	
	/** TextView for the user to enter their password. */
	private TextView passwordTextView;

	/** Checkbox indicating whether or not the user wants to stay logged in. */
	private CheckBox persistantLogin;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_screen);
		
		usernameTextView = (TextView) findViewById(R.id.username);
		passwordTextView = (TextView) findViewById(R.id.password);
		persistantLogin  = (CheckBox) findViewById(R.id.persistant_check_box);
		
		/*SharedPreferences prefs = this.getPreferences(MODE_PRIVATE);
		if (prefs.getBoolean("persistant", false)) {
			usernameTextView.setText(prefs.getString("username", ""));
			passwordTextView.setText(this.dencrypt(prefs.getString("password", "")));
			this.submit_clicked(null);
		}*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_screen, menu);
		return true;
	}
	
	private int validateLogin(String username, String password) {
		Log.d("LOGIN", "Validating...");
		CardGameClient client = null;
		try {
			client = new CardGameClient();
			Log.d("LOGIN", "Validating......");
			String result = client.verifyUser(username, password);
			Log.d("LOGIN", "Password is " + result);
			if (!result.equals(BAD))
				return Integer.parseInt(result);
			else
				return 0;
		} catch (Exception e) {
			Toast.makeText(this, "Cannot connect to server. Try again later. " + e.getMessage(), Toast.LENGTH_SHORT).show();
		}
		return 0;
	}
	
	private String encrypt(String text) {
		//TODO: Do stuff to text
		return text;
	}
	
	@SuppressWarnings("unused")
	private String dencrypt(String text) {
		//TODO: Do stuff to text
		return text;
	}
	
	public void submit_clicked(View b) {
		String username = usernameTextView.getText().toString();
		String password = this.encrypt(passwordTextView.getText().toString());
		
		if (username.equals("") && password.equals("")) {
			Toast.makeText(this, "Enter a username and password.", Toast.LENGTH_SHORT).show();
		} else if (username.equals("")) {
			Toast.makeText(this, "Enter a username.", Toast.LENGTH_SHORT).show();
		} else if (password.equals("")) {
			Toast.makeText(this, "Enter a password.", Toast.LENGTH_SHORT).show();
		} else {
			int userId = validateLogin(username, password);
			if (userId != 0) {
				SharedPreferences.Editor editor = this.getPreferences(MODE_PRIVATE).edit();
				editor.putString("username", username);
				editor.putString("password",  password);
				editor.putInt("userID", userId);
				editor.putBoolean("persistant", persistantLogin.isChecked());
				editor.commit();
				Toast.makeText(this, "Logged in as " + username, Toast.LENGTH_SHORT);
				Intent i = new Intent(this, CardGameMenu.class);
				i.putExtra("userId", userId);
				this.startActivity(i);
			} else {
				Toast.makeText(this, "Invalid Username/Password Combonation", Toast.LENGTH_SHORT).show();
				passwordTextView.setText("");
			}
		}
	}
	
	public void create_clicked(View b) {
		Intent i = new Intent(this, CreateAccount.class);
		startActivity(i);
	}

}
