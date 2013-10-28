package edu.wcu.cs.cs495.capstonecardgame.activities;


import edu.wcu.cs.cs495.capstonecardgame.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CardGameMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cardgamemenu);
	}
	
	public void newGame(View v) {
		Intent i = new Intent(this, CardGame.class);
		startActivity(i);
	}

}