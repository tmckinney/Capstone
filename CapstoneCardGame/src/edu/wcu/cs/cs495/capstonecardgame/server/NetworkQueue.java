package edu.wcu.cs.cs495.capstonecardgame.server;

import android.util.Log;

public class NetworkQueue  {
	
	private static final String TAG = "NetworkQueue.class";
	private int size;
	private int capacity;
	private String queue;
	
	public NetworkQueue(int size) {
		capacity = size;
		size     = 0;
		queue    = "";
	}
	
	public NetworkQueue() {
		this(10);
	}
	
	public NetworkQueue(String string) {
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
			pushToNetwork();
			return true;
		}
		return false;
	}

	private void pushToNetwork() {
		String queue = this.toString();
		Log.i(TAG, queue);
	}

	@Override
	public String toString() {
		return queue;
	}
}