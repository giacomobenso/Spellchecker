package spellCheck;

import static java.lang.Math.pow;

public class StringHashCode implements IHashCode {
	/**  
	 This class is used to get hash code for strings,
	 it only implements one method which performs this task.
	 the method giveCode takes an object(that we know is a string)
	 and multiplies every char in it with 7(prime number)
	 to the power of an accumulator starting at 0.
	  */

	public StringHashCode() {

	}

	@Override
	public int giveCode(Object o) {

		if (o.getClass() == String.class) {
			String s = (String) o;
			int hashValue = 0;
			char[] stringC = s.toCharArray();
			int accumulator = 0;

			for (char c : stringC) {
				hashValue += (int) c * pow(7, accumulator);
				accumulator++;
			}
			return hashValue;

		} else {
			System.err.println("you can only find hashCode for Strings");
			return -1;
		}
	}
	
}
