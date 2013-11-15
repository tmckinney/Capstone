package edu.wcu.cs.cs495.capstonecardgame.activities;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import edu.wcu.cs.cs495.capstonecardgame.R;

public class LoginScreen extends Activity {

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
	
	private boolean validateLogin(String username, String password) {
		//TODO: Validate the login.
		return true;
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
			if (validateLogin(username, password)) {
				SharedPreferences.Editor editor = this.getPreferences(MODE_PRIVATE).edit();
				editor.putString("username", username);
				editor.putString("password",  password);
				editor.putBoolean("persistant", persistantLogin.isChecked());
				editor.commit();
				Toast.makeText(this, "Logged in as " + username, Toast.LENGTH_SHORT);
				Intent i = new Intent(this, CardGameMenu.class);
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
