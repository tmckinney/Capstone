package edu.wcu.cs.cs495.capstonecardgame.activities;

import edu.wcu.cs.cs495.capstonecardgame.R;
import edu.wcu.cs.cs495.capstonecardgame.network.CardGameClient;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CreateAccount extends Activity {

	private TextView usernameTextBox;
	
	private TextView passwordTextBox;
	
	private TextView passwordConfirmation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_account);
		
		this.usernameTextBox = (TextView) findViewById(R.id.username);
		this.passwordTextBox = (TextView) findViewById(R.id.password);
		this.passwordConfirmation = (TextView) findViewById(R.id.password_conformation_text_view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_account, menu);
		return true;
	}
	
	public void submit_clicked(View b) {
		String username = usernameTextBox.getText().toString();
		String password = passwordTextBox.getText().toString();
		String passwordDuplicate = passwordConfirmation.getText().toString();
		
		if (password.equals(passwordDuplicate)) {
			try {
				CardGameClient client = new CardGameClient();
				String result = client.newUser(username, password);
				if (result.equals("good"))
					Toast.makeText(this, "Could not create account", Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				Toast.makeText(this, "Cannot connect to server. Try again later. " + e.getMessage(), Toast.LENGTH_SHORT).show();
			}
		}
		this.finish();
	}
}
