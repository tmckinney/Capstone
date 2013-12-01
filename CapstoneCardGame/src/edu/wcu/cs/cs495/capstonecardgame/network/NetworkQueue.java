package edu.wcu.cs.cs495.capstonecardgame.network;

import android.util.Log;

public class NetworkQueue  {
	
	private static final String TAG = "NetworkQueue.class";
	private int size;
	private int capacity;
	private int gameNum;
	private int turnNum;
	private String queue;
	
	public NetworkQueue(int gameNum, int size) {
		this.capacity = size;
		this.size     = 0;
		this.queue    = "";
		this.gameNum  = gameNum;
	}
	
	public NetworkQueue(int gameNum) {
		this(gameNum, 10);
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
		String networkString = this.gameNum + CallCodes.SEPARATOR + turnNumber + CallCodes.SEPARATOR + this.toString();
		queue = "";
		
		Log.i(TAG, networkString);
	}

	@Override
	public String toString() {
		return queue;
	}
}