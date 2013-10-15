package edu.wcu.cs.cs495.capstonecardgame.server;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

import android.util.Log;

public class NetworkQueue implements Queue<String> {
	
	private static final String TAG = "NetworkQueue.class";
	private String[] array;
	private int index;
	private int size;
	private int capacity;
	private String queue;
	
	public NetworkQueue(int size) {
		array    = new String[size];
		capacity = size;
		index    = 0;
		size     = 0;
		queue    = "";
	}
	
	public NetworkQueue() {
		this(10);
	}
	
	@Override
	public boolean addAll(Collection<? extends String> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(String arg0) {
		boolean pushed = isFull();
		if (size == 0) {
			array[0] = arg0;
		} else {
			array[index] = arg0;
		}
		size++;
		index++;
		Log.i(TAG, "queue: " + queueToString());
		Log.i(TAG, "pushed = " + pushed);
		return pushed;
	}

	@Override
	public boolean offer(String arg0) {
		// TODO Auto-generated method stub
		return false;
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
		String queue = queueToString();
		Log.i(TAG, queue);
	}

	private String queueToString() {
		for (int i = 0; i < size; i++) {
			queue += remove();
		}
		return queue;
	}

	@Override
	public void clear() {
		size  = 0;
		index = 0;
	}

	@Override
	public boolean contains(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<String> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String element() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String peek() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String poll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String remove() {
		String removed = array[0];
		for (int i = 1; i < size; i++) {
			array[i] = array[i + 1];
		}
		index--;
		size--;
		return removed;
	}

}
