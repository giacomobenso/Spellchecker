package spellCheck;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * Main class for the Spell-Checker program, this class is used to correct some
 * words of a text file passed as an argument comparing them with another file
 * called dictionary also passed as an argument.
 */
public class SpellCheck {

	private final static char[] ALPHABET = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
			'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	private IMap mapType;
	private IMap misspelled;
	private StringHashCode hC = new StringHashCode();

	// the boolean serves to initialize a linkedList based (true) or a HashTable
	// based (false) spellChecker .
	public SpellCheck(boolean bool) throws MapException {
		if (bool) {
			mapType = new LinkedListMap();
			misspelled = new LinkedListMap();
		} else {
			mapType = new HashTableMap(hC, 0.5f);
			misspelled = new HashTableMap(hC, 0.5f);
		}
	}

	// the following method inserts the words of the dictionary file into the
	// Map taking 2 arguments,
	// the FileWordReader and the map type (in our case LinkedList or HashMap).
	public void fillMap(FileWordRead fw) throws IOException, MapException {
		while (fw.hasNextWord()) {
			String currentW = fw.nextWord();
			mapType.insert(currentW);

		}
	}

	
	// this method checks the misspelled words in the file and inserts them into
	// the "misspelled" map (this process is used to avoid printing the same
	// word more than once),
	// then returns a string of the format : "wordToCorrect --->
	// correctedWords".
	public String stringMispelled(FileWordRead fr) throws IOException, MapException {
		String toReturn = "";
		while (fr.hasNextWord()) {
			String nextWord = fr.nextWord();
			if (!mapType.find(nextWord) && !misspelled.find(nextWord)) {
				misspelled.insert(nextWord);
				toReturn = toReturn + nextWord + " --> " + checkWord(nextWord) + "\n";
			}
		}

		return toReturn;
	}

	
	// this method finds the possible corrections of the word passed as
	// parameter inserting them into a map and then calling the stringMap()
	// method.
	// the methods used to correct the word are:
	// 1. Letter substitution
	// 2. Letter insertion
	// 3. letter omission
	// 4. Letter reversal
	private String checkWord(String word) throws MapException {

		IMap S;

		if (mapType instanceof HashTableMap) {
			S = new HashTableMap(hC, 0.5f);
		} else {
			S = new LinkedListMap();
		}

		for (int i = 0; i <= word.length(); i++) {

			for (char x : ALPHABET) {
				
				StringBuffer sd = new StringBuffer(word);

				if (mapType.find(sd.insert(i, x).toString()) && !S.find(sd.toString())) {
					S.insert(sd.toString());
				}

				if (i < word.length()) {
					StringBuffer sb = new StringBuffer(word);
					sb.setCharAt(i, x);

					if (mapType.find(sb.toString()) && !S.find(sb.toString())) {
						S.insert(sb.toString());
					}
				}
				
			}
			if (i < word.length()) {
				StringBuffer se = new StringBuffer(word);
				if (mapType.find(se.deleteCharAt(i).toString()) && !S.find(se.toString())) {
					S.insert(se.toString());
				}
			}

			if (i < word.length() - 1) {
				StringBuffer sc = new StringBuffer(word);
				char toChange = word.charAt(i);
				sc.setCharAt(i, word.charAt(i + 1));
				sc.setCharAt(i + 1, toChange);

				if (mapType.find(sc.toString()) && !S.find(sc.toString())) {
					S.insert(sc.toString());
				}
			}

		}
		return stringMap(S);
	}

	
	// method used to return a string of the map passed as a parameter,
	// we insert commas between among the words in the map and then we remove
	// the last comma.
	private String stringMap(IMap map) {
		String toReturn = "";
		Iterator<String> it = map.elements();
		while (it.hasNext()) {
			toReturn = toReturn + it.next() + ", ";
		}

		StringBuffer removeComma = new StringBuffer(toReturn);
		if (!toReturn.equals("")) {
			removeComma.deleteCharAt(toReturn.length() - 2);
		} else {
			return "NO CORRECTIONS AVAILABLE";
		}
		return removeComma.toString();
	}

	public static void main(String[] args) throws java.io.IOException {
		if (args.length != 2) {
			System.out.println(
					"You need 2 arguments for this Program: \nargs[0]= your dictionary\nargs[1]= your file to spellcheck");
			System.exit(0);
		}

		try {
			BufferedInputStream dict, file;
			FileWordRead diction, toSpell;

			dict = new BufferedInputStream(new FileInputStream(args[0]));
			diction = new FileWordRead(dict);

			file = new BufferedInputStream(new FileInputStream(args[1]));
			toSpell = new FileWordRead(file);

			try {
				SpellCheck spell = new SpellCheck(false);

				spell.fillMap(diction);
				System.out.println(spell.stringMispelled(toSpell));

				dict.close();
				file.close();

			} catch (MapException e1) {
				e1.printStackTrace();
			}

		} catch (IOException e) { // catch exceptions caused by file
									// input/output errors
			e.printStackTrace();
		}

	}
}
