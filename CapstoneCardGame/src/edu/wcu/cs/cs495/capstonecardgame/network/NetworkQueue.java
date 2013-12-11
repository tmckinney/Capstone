package edu.wcu.cs.cs495.capstonecardgame.network;

import java.io.IOException;

import android.util.Log;

public class NetworkQueue  {
	
	private static final String TAG = "NetworkQueue.class";
	private int size;
	private int capacity;
	private int gameNum;
	private int turnNum;
	private String queue;
	private int player;
	private CardGameClient client;
	
	public NetworkQueue(int player, int gameNum, int size) {
		this.capacity = size;
		this.size     = 0;
		this.queue    = "";
		this.gameNum  = gameNum;
		this.player   = player;
		try {
			this.client   = new CardGameClient();
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
	
	public NetworkQueue(int player, int gameNum) {
		this(player, gameNum, 10);
	}
	
	public NetworkQueue(int gameNum, String string) {
		size  = 0;
		String[] strings = string.split(CallCodes.SEPARATOR);
		for (String aString : strings) {
			this.add(aString);
			this.add(CallCodes.SEPARATOR);
			size += 2;
		}
		Log.d(TAG, this.toString());
		try {
			this.client   = new CardGameClient();
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
	
	

	public boolean add(String string) {
		boolean pushed = isFull();
		queue += string;
		Log.d("NETWORK QUEUE", "Pushed = " + pushed + " : Param = " + string + " :  Queue = " + this.toString());
		return pushed;
	}
	
	/** Checks to see if the queue is full and if it is pushes it to the network. 
	 * @return */
	private boolean isFull() {
		if (size == capacity) {
			pushToNetwork(this.turnNum);
			return true;
		}
		return false;
	}

	public void pushToNetwork(int turnNumber) {
		this.turnNum = turnNumber;
		String networkString = this.toString();
		queue = "";
		client.newMove("" + this.gameNum, "" + this.player, networkString);
		Log.i(TAG, "gameNum = " + this.gameNum + " : player = " + this.player + " : networkString = " + networkString);
	}

	@Override
	public String toString() {
		return queue;
	}
}