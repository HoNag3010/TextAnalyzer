import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 * This program counts word of a text file you located
 * @author Huy Hoang Nguyen
 */

public class TextAnalyzer {
    public static void main(String[] args) {

        printHeader();      // Print a welcome banner

        Scanner scan = new Scanner(System.in);      // Declare a scanner
        ArrayList<String> sortString = new ArrayList<>();         // Declare an ArrayList to use later
        LinkedHashMap<String, Integer> counts = new LinkedHashMap<>();           // Declare a LinkedHashMap to use later
        int uniqueChar = 0;         // This to count unique character
        int numChar = 0;            // This to count total character

        System.out.print("Enter the name of the file to analyze: ");
        boolean validInput = false;         // Declare a boolean to test the input
        while (!validInput) {               // Make a while loop so that if the input is invalid, loop the process
            String path = scan.nextLine();          // Scan user input

            File file = new File(path);            
            try (Scanner fsc = new Scanner(file)) {         // Use try..catch to catch invalid input
                while (fsc.hasNextLine()) {                 // If the input is valid, this while loop will start
                    String text = fsc.nextLine();
                    String[] words = text.split("\\P{Alpha}\\s*");          // Split words from every sentence
                    for (String word : words) {             // For loop to make word lowercase and no extra space
                        word = word.toLowerCase().trim();
                        if (!word.isEmpty()) {              // If the word is not empty (I had a problem here with line break that makes word count incorrect)
                            sortString.add(word);           // it will add word into ArrayList
                        }
                    }
                }   
                validInput = true;              // If everything is correct, boolean will turn to true so no more loop will occur
                fsc.close();                // Close the file scanner
            } catch (FileNotFoundException ex) {            
                System.out.print("Please enter a correct file path: ");         // If incorrect, loop and print the message
            } 
        }
        Collections.sort(sortString);           // Sort the string using Collections.sort()
        
        for (String str : sortString) {
            if (counts.containsKey(str)) {              // If there is a word identical inside the LinkedHashMap already, increment by 1
                counts.put(str, counts.get(str)+1);     
                numChar += 1;                   // Increment numChar everytime add a word
            } else if (!counts.containsKey(str)) {      // If there is no identical word, add a new one in
                counts.put(str, 1);     
                uniqueChar += 1;                // Increment both uniqueChar and numChar this time
                numChar += 1;
            }
        }

        System.out.println("There are " + numChar + " words in the text.");         // Print everything out
        System.out.println("There are " + uniqueChar + " unique words in the text.");
        System.out.println("Here are the unique words and how many times each appeared:");

        for (var entry : counts.entrySet()) {           // For loop to print every word listed and its count
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        printThankYouMessage(averageWords(numChar, sortString));        // Print the thank you message
        scan.close();       // Close the scanner
        

    }

    /**
     * This function prints a welcome banner for user
     */
    public static void printHeader() {
        System.out.println("********************************************************************************");
        System.out.println("                            Writing Analyzer V1.0                               ");
        System.out.println("********************************************************************************");
        System.out.println("");
    }

    /**
     * This function prints a thank you banner
     * @param averageLength is included in the thank you message as average words count in the text file
     */
    public static void printThankYouMessage(double averageLength) {
        System.out.printf("The average word length is %.2f.\n",averageLength);
        System.out.println("Thank you for using this program.");
    }

    /**
     * This function prints average word counts from the text file
     * @param numChar is number of total char in the text
     * @param sortedList is the list of words
     * @return average word counts
     */
    public static double averageWords(int numChar, ArrayList<String> sortedList) {
        double totalWord = 0;
        for (String str : sortedList) {
            totalWord += str.length();
        }
        return (totalWord / (double)numChar);
    }
}