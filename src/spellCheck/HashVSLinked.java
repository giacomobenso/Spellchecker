package spellCheck;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class HashVSLinked {

	/**
	 * Class only used to run the test to compare the linkedList based map
	 * versus the hashTable based map.
	 * It takes two argument as the spellcheck class, just chose the files and run it to check
	 * the differences between the two.
	 */

	public static void main(String[] args) {

		try {
			BufferedInputStream dict, file;
			BufferedInputStream dict2, file2;
			FileWordRead diction, toSpell;
			FileWordRead diction2, toSpell2;

			dict = new BufferedInputStream(new FileInputStream(args[0]));
			diction = new FileWordRead(dict);

			dict2 = new BufferedInputStream(new FileInputStream(args[0]));
			diction2 = new FileWordRead(dict2);

			file = new BufferedInputStream(new FileInputStream(args[1]));
			toSpell = new FileWordRead(file);

			file2 = new BufferedInputStream(new FileInputStream(args[1]));
			toSpell2 = new FileWordRead(file2);

			try {
				SpellCheck hash = new SpellCheck(false);
				SpellCheck linked = new SpellCheck(true);

				double timetotal1 = System.currentTimeMillis();
				
				double time1fill = System.currentTimeMillis();
				linked.fillMap(diction);
				double time2fill = System.currentTimeMillis();
				
				double time1insert = System.currentTimeMillis();
				linked.stringMispelled(toSpell);
				double time2insert = System.currentTimeMillis();
				
				double timetotal2 = System.currentTimeMillis();
				
				double timetotal3 = System.currentTimeMillis();
				
				double time3fill = System.currentTimeMillis();
				hash.fillMap(diction2);
				double time4fill = System.currentTimeMillis();
				
				double time3insert = System.currentTimeMillis();
				hash.stringMispelled(toSpell2);
				double time4insert = System.currentTimeMillis();
				
				double timetotal4 = System.currentTimeMillis();

				System.out.println("");
				System.out.println(" Time Needed from each method to perform and total time needed (SECONDS)");
				System.out.println("");
				System.out.println("              LinkedListMap    HashTableMap");
				System.out.println(String.format(" fillMap()     %10f      %10f", (time2fill - time1fill),(time4fill - time3fill)));
				System.out.println(String.format(" insert()      %10f      %10f", (time2insert - time1insert),(time4insert - time3insert)));
				System.out.println(String.format(" TOTAL         %10f      %10f", (timetotal2 - timetotal1),(timetotal4 - timetotal3)));
				
				dict.close();
				dict2.close();
				file.close();
				file2.close();

			} catch (MapException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
