package spellCheck;

public class MapException extends Exception {

	/**
	 * This Class simply extends the Exception class and prints the stack trace
	 * when is invoked
	 */

	static final long serialVersionUID = 1L;

	public MapException() {
		super.printStackTrace();
	}
}
