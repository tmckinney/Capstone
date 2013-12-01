package edu.wcu.cs.cs495.capstonecardgame.activities;


import java.io.IOException;

import edu.wcu.cs.cs495.capstonecardgame.R;
import edu.wcu.cs.cs495.capstonecardgame.network.CardGameClient;
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
		CardGameClient client = null;
		try {
			client = new CardGameClient();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long seed = System.currentTimeMillis();
		String gameNum = client.newGame("" + seed);
		Intent i = new Intent(this, CardGame.class);
		i.putExtra("SEED", seed);
		i.putExtra("GAME", Integer.parseInt(gameNum));
		startActivity(i);
	}

}