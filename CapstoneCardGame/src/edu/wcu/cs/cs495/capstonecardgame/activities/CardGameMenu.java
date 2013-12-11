package edu.wcu.cs.cs495.capstonecardgame.activities;


import java.io.IOException;

import edu.wcu.cs.cs495.capstonecardgame.R;
import edu.wcu.cs.cs495.capstonecardgame.network.CardGameClient;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class CardGameMenu extends Activity {

	private static final String TAG = "CardGameMenu.class";
	private int gameNum;

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
		//long seed = System.currentTimeMillis();
		long seed = 1386733225184l;
		this.gameNum = client.newGame("1", "2", "" + seed);
		Log.d(TAG, "gameNum = " + gameNum);
		Intent i = new Intent(this, CardGame.class);
		i.putExtra("SEED", seed);
		i.putExtra("GAME", gameNum);
		startActivity(i);
	}
	
	public void joinGame(View v) {
		CardGameClient client;
		try {
			client      = new CardGameClient();
			String seed = client.pull("1", "2");
			Intent i    = new Intent(this, CardGame.class);
			i.putExtra("SEED", seed);
			i.putExtra("GAME", 1);
			startActivity(i);
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

	}

}