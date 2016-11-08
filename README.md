

Assessed Individual Coursework 1 


Spell-Checker


F28DA: Data Structures & Algorithms

23 – 10 – 2016 



Program Specifications

The program we are asked to develop is a spell-checker, used to correct some words of a text file passed as an argument, whilst comparing them with another file called dictionary which is also passed as an argument.
The SpellCheck class, the class containing the main method, has two major methods performing the required tasks: 
•	fillMap(): this is the method used to insert the words from the dictionary into the map.
•	stringMispelled() : is used to find the incorrectly spelled words in the file, correcting them and inserting them into the "misspelled" map. This process is used to avoid printing the same word more than once. Finally returning a string in the format of: "wordToCorrect ---> correctedWords".
There are also some helper methods used within in the methods listed above to perform the task of checking each word and to create a string from a map.

I implemented my program with both the linkedlist and the hashtable ADT using the interface IMap as asked.
The linked list part has been implemented extending the LinkedList Class provided by java and using all of it’s existing constructor and methods.
The hashMap instead, has been totally implemented by me as we were not allowed to use any existing class. For a more clear idea, it is better to have a closer look at the class inside my project.
This latter class has been implemented using an array of strings starting with the size of seven, which in turn, increases at the first prime number after the double of it when the max load factor (float number passed to the constructor to set the maximum “percentage” of the array fullness) is reached.                                                                     
The strings are inserted using an hashcode produced by a method (also implemented by me) in the StringHashCode class, which is then processed to be inserted into the array using the operation HASHCODE modulo SIZEOFTHEARRAY.                                              
If the cell we are trying to fill with the string is already full we try to find a new cell using another function (f(k) = (5 - CODE modulo 5) modulo SIZEOFTHEARRAY), this process is known as double hashing.

To find and remove a string, I used a process similar to the one used for the inserting, however, when we remove an object we must insert a dummy value to facilitate and speed up the process of finding that value or one which collided with it. Some helper methods have also been implemented to avoid code duplication and increase cohesion.









dictionary: d5.txt
file to spellcheck: “injuries against inseccts ar pesristent”






dictionary: dictionary.txt


file to spellcheck: “teh cat is not teh seye t
