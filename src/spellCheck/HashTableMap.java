package spellCheck;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

public class HashTableMap implements IMap, IHashTableMonitor {

	/** This class extends the IMap and the IHashTableMonitor interfaces to have all
	 * the methods needed as a map and to monitor the behavior of the HashTable.
	 * This class has been implemented using the open addressing with double hashing strategy.
	 * The size of the array is initialized at 7 and increased at the first prime number of the size
	 * after it being doubled every time the max load factor is reached */
	
	private String[] arr;
	private int size;
	private int numOfElements;
	private float maxLoadFactor;
	private int totalProbes;
	private int numOfOperations;
	private IHashCode iHash;

	//this default constructor is just used to throw a MapException.
	public HashTableMap() throws MapException {
		throw new MapException();
	}

	//This is the constructor for the hash table which takes an IHashCode object and
	//float maxLoadFactor which specifies the maximum allowed load factor for the hash table.
	//If the load factor calculated with the getLoadFactor() method becomes larger than maxLoadFactor,
	//the (private) rehash() method must be invoked.
	//It also initializes all the global variables needed by the program.
	public HashTableMap(StringHashCode inputCode, float maxLoadFactor) {

		size = 7;
		arr = new String[size];
		numOfElements = 0;
		numOfOperations = 0;
		totalProbes = 0;
		this.maxLoadFactor = maxLoadFactor;
		iHash = inputCode;

	}

	public float averNumProbes() {
		return totalProbes / numOfOperations;
	}

	public float getMaxLoadFactor() {
		return maxLoadFactor;
	}

	public float getLoadFactor() {
		return (float) numOfElements / size;
	}
	
	public int numberOfElements() {
		return numOfElements;
	}

	private boolean isEmpty() {
		if (getLoadFactor() == 0.0f)
			return true;
		else
			return false;
	}

	// This method checks whether a number is prime, we need
	// this method to increment the size of the array to the next prime.
	private boolean isPrime(int n) {
		for (int i = 2; 2 * i < n; i++) {
			if (n % i == 0)
				return false;
		}
		return true;
	}

	// method to determinate the size of the new array in the
	// rehash() method.
	public int getNextSize(int size) {
		size = size * 2;
		for (int i = size; i > 2; i++) {
			if (isPrime(i))
				return i;
		}
		return -1;
	}

	// Method used to perform the rehashing when the array
	// has reached the maximum load factor.
	private void rehash() throws MapException {
		
		size = getNextSize(size);
		String[] newArr = new String[size];
		
		for (String s : arr) {
			if (s != null && !s.equals("-1")) {
				insert(s, newArr);
			}
		}
		arr = newArr;
	}

	// this helper method finds the next cell in an array whit value "what"
	// using the
	// double hashing function (if we are looking for a word and is not there it
	// stops returning -1),
	// this latter is performed taking the prime number 5
	// (it will always be smaller then the size of the array)
	// and using it as a constant variable for the double hashing of the
	// series: f(k) = (n - k % n) % size.
	private int probe(int lastChecked, int code, String what, String[] array) {

		int j = lastChecked;
		int acc = 0;
		int prime = 5;

		while (acc < size) {

			j = (j + (prime - code % prime)) % size;
			totalProbes++;
			acc++;
			if (what != null && array[j] == null) {
				return -1;
			} else if (what != null && array[j].equals(what)) {
				return j;
			} else if (what == null && array[j] == null)
				return j;
		}
		return -1;

	}

	// public method used outside the class to insert a string
	// in the default array of the HashTableMap class.
	public void insert(String key) throws MapException {
		
		if (!find(key)) {
			int code = iHash.giveCode(key);
			int slot = code % size;
			if (arr[slot] == null) {
				arr[slot] = key;
				totalProbes++;
			} else {
				arr[probe(slot, code, null, arr)] = key;
			}
		}
		numOfElements++;
		numOfOperations++;
		if (getLoadFactor() > maxLoadFactor) {
			rehash();
		}

	}

	// private method used within the class to insert a string
	// in an array (we use this in the rehash() method).
	private void insert(String key, String[] array) throws MapException {

		int code = iHash.giveCode(key);
		int slot = code % size;
		
		if (array[slot] == null) {
			array[slot] = key;
		} else {
			int check = probe(slot, code, null, array);
			if (check != -1) {
				array[check] = key;
			} else {
				throw new MapException();
			}
		}

	}

	// this method returns the index of the cell in the array
	// containing the value "key", if it is not there it returns -1.
	// it first checks if the value is in the slot given with the first hash
	// function,
	// if the value is not there it tries with the probe() method (double
	// hashing).
	private int getIndexKey(String key) {

		if (!isEmpty() && key != null) {

			int code = iHash.giveCode(key);
			int slot = code % size;

			if (arr[slot] == null) {
				return -1;
			}
			if (arr[slot].equals(key)) {
				return slot;
			} else {
				return probe(slot, code, key, arr);
			}
		}

		return -1;
	}

	// This method returns true if the HashMap contains the value "key".
	public boolean find(String key) {

		numOfOperations++;

		if (getIndexKey(key) == -1) {
			return false;
		} else {
			return true;
		}

	}

	// this method sets the value of cell corresponding to the String "key"
	// in the hashTable to "-1" (dummy value).
	public void remove(String key) throws MapException {

		if (!isEmpty()) {

			int toRemove = getIndexKey(key);
			if (toRemove != -1) {
				arr[toRemove] = "-1";
			} else {
				throw new MapException();
			}
		}
		numOfElements--;
		numOfOperations++;
	}

	//The iterator for this class first removes
	public Iterator<String> elements() {
		List<String> iterable = new ArrayList<String>(Arrays.asList(arr));
		iterable.removeAll(Collections.singleton(null));
		iterable.removeAll(Collections.singleton("-1"));

		Iterator<String> it = iterable.iterator();
		return it;
	}

}
