
F28DA: Data Structures & Algorithms CW1 2016/17

Overview

In this assignment, you are requested to write a simple spell-checker program. Your program should be named SpellCheck and it will take as command line arguments two file names. The first name is the dictionary file which contains correctly spelled words (On Vision the file dictionary.txt has been provided). The second file contains the text to be spell-checked. Your program should first read all words from the dictionary file and insert them into a map data structure. Then your program should read words from the second file and check if they are in the map. For words that do exist in the map nothing needs to be done. For words which are not in the map, your program should suggest possible correct spellings by printing to the standard output. You should perform modifications of a misspelled word given in Section 2. In this assignment you will implement and compare (experimentally) a linked list based and a hash table based map.


Modifications of Misspelled Word

You should perform the modifications of a misspelled word to handle commonly made mistakes:
• Letter substitution: go over all the characters in the misspelled word, and try to replace a character by any other character. In this case, if there are k characters in a word, the number of modifications to try is 26k. For example, in a misspelled word ’lat’, substituting ’c’ instead of ’l’ will produce a word ’cat’, which is in the dictionary.
• Letter omission: try to omit (in turn, one by one) a single character in the misspelled word and see if the word with omitted character is in the dictionary. In this case, there are k modifications to try where k is the number of characters in the word. For example, if the misspelled word is ’catt’, omitting the last character ’t’ will produce a word ’cat’ which is in the dictionary.
• Letter insertion: try to insert a letter in the misspelled word. In this case, if the word is k characters long, there are 26 ∗ (k + 1) modifications to try, since there are 26 characters to try to insert and
k + 1 places (including the beginning and the end of the word) to insert a character. For example, for word ’plce’, inserting letter ’a’ in the middle will produce a correctly spelled word ’place’.
 • Letter reversal: Try swapping every 2 adjacent characters. For a word of length k, there are k − 1 pairs to try to swap. For example in a misspelled word ’paernt’, swapping letters ’e’ and ’r’ will produce a correctly spelled word ’parent’.
For each word which was misspelled, on a separate line, print out the misspelled word and all possible correct spellings that you found in the dictionary. For example, if your dictionary file contains the words ’cats like on of to play’, and the file to spell check contains 4 words: ’Catts lik o play’, the output should be:
catts => cats
lik => like
o => on, to, of
Notice that the list of possible correct spelling must contain only unique words. In the example above, for the misspelled word ’catts’, removing the first ’t’ or the second ’t’ leads to the same word ’cats’, but this word appears in the output only once. For the modifications of a word above, the Java class StringBuffer and its built-in methods are very useful.
A file FileWordRead.java which will read the next word from an opened file is provided. See comments in the file FileWordRead.java for the usage. In this implementation, all words are converted to lower case so that the words ’Cat’ and ’cat’ are treated as one word. Thus all the words you read from the file using the program will be lowercase words.


Implementation

You are to implement the program based on a map, let us call it M . You will use M for storing the words in the dictionary input file specified by the first command line argument. After storing all the words in M , you should open the second file (the one to be spell-checked) for reading and look up the words in the second file in map M. If any word w of the second file is not in M, you have to try all possible modifications of w suggested above, in Section 2. Notice that different modifications may result in the same word. The implementation of the map, see sections below, insures that each map entry contains a unique word. Therefore, to make sure that there are no duplicates on the list of possible spellings, create a second map S (for each misspelled word), and insert all modifications of the misspelled words that are in map M. After you went over all modifications, print out all the words stored in map S.

