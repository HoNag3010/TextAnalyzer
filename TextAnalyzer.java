import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class TextAnalyzer {
    public static void main(String[] args) {

        printHeader();

        Scanner scan = new Scanner(System.in);
        ArrayList<String> sortString = new ArrayList<String>();
        LinkedHashMap<String, Integer> counts = new LinkedHashMap<String, Integer>();
        int uniqueChar = 0;
        int numChar = 0;

        System.out.print("Enter the name of the file to analyze: ");
        boolean validInput = false;
        while (!validInput) {
            String path = scan.nextLine();

            File file = new File(path);
            try (Scanner fsc = new Scanner(file)) {
                while (fsc.hasNextLine()) {
                    String text = fsc.nextLine();
                    String[] words = text.split("\\P{Alpha}\\s*");
                    for (String word : words) {
                        word = word.toLowerCase().trim();
                        if (!word.isEmpty()) {
                            sortString.add(word);
                        }
                    }
                }
                validInput = true;
            } catch (FileNotFoundException ex) {
                System.out.print("Please enter a correct file path: ");
            }
        }
        Collections.sort(sortString);
        
        for (String str : sortString) {
            if (counts.containsKey(str)) {
                counts.put(str, counts.get(str)+1);
                numChar += 1;
            } else if (!counts.containsKey(str)) {
                counts.put(str, 1);
                uniqueChar += 1;
                numChar += 1;
            }
        }

        System.out.println("There are " + numChar + " words in the text.");
        System.out.println("There are " + uniqueChar + " unique words in the text.");
        System.out.println("Here are the unique words and how many times each appeared:");

        for (var entry : counts.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        printThankYouMessage(averageWords(numChar, sortString));

    }

    public static void printHeader() {
        System.out.println("********************************************************************************");
        System.out.println("                          Writing Analyzer V1.0                                 ");
        System.out.println("********************************************************************************");
        System.out.println("");
    }

    public static void printThankYouMessage(double averageLength) {
        System.out.printf("The average word length is %.2f.\n",averageLength);
        System.out.println("Thank you for using this program.");
    }

    public static double averageWords(int numChar, ArrayList<String> sortedList) {
        double totalWord = 0;
        for (String str : sortedList) {
            totalWord += str.length();
        }
        return (totalWord / (double)numChar);
    }
}