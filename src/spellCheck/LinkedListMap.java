package spellCheck;
import java.util.Iterator;
import java.util.LinkedList;

public class LinkedListMap extends LinkedList<String>implements IMap {

	private static final long serialVersionUID = 1L;
	
	/**
	 This class extends the existing class LinkedList
	 and implements the Imap interface
	 to create a Map based LinkedList.
	 The constructor is taken from the LinkedList superclass 
	 as well as the methods.
	 */

	public LinkedListMap() {
		super();
	}

	@Override
	public void insert(String key) throws MapException {
		if(!find(key))
			super.add(key);

	}

	@Override
	public void remove(String key) throws MapException {
		super.remove(key);

	}

	@Override
	public boolean find(String key) {
		return super.contains(key);
	}

	@Override
	public int numberOfElements() {
		return super.size();
	}

	@Override
	public Iterator<String> elements() {
		return super.iterator();
	}

}
