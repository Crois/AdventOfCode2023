package day1;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalibrationSum {
    public static void main(String[] args) throws IOException {


        printColaburationValueForFile("data/test");
        printColaburationValueForFile("data/puzzle");
        printColaburationValueForFile("data/test2");
        printColaburationValueForFile("data/puzzle2");
    }



    private static void printColaburationValueForFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int sum = 0;

            while ((line = br.readLine()) != null) {
                line = replaceWordsWithNumbers(line);
                int [] bounds = extractBounds(line);
                int calibrationValue = (bounds[0] * 10) + bounds[1];

                // Add the calibration value to the sum
                sum += calibrationValue;
            }

            // Print the sum of all calibration values
            System.out.println("Sum of calibration values: " + sum);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int[] extractBounds(String input) {
        Pattern pattern = Pattern.compile("\\d");
        Matcher matcher = pattern.matcher(input);

        int[] bounds = new int[2];

        if (matcher.find()) {
            bounds[0] = Integer.parseInt(matcher.group()); // Start index
        } else {
            return new int[] {0,0}; // No numbers found
        }

        bounds[1] = bounds[0];
        while (matcher.find()) {
            bounds[1] = Integer.parseInt(matcher.group()); // End index
        }

        return bounds;
    }

    private static String replaceWordsWithNumbers(String input) {
        // Definiere eine Map für die Zuordnung von Wörtern zu Zahlen
        java.util.Map<String, String> wordToNumberMap = new java.util.HashMap<>();
        wordToNumberMap.put("zero", "0");
        wordToNumberMap.put("one", "1");
        wordToNumberMap.put("two", "2");
        wordToNumberMap.put("three", "3");
        wordToNumberMap.put("four", "4");
        wordToNumberMap.put("five", "5");
        wordToNumberMap.put("six", "6");
        wordToNumberMap.put("seven", "7");
        wordToNumberMap.put("eight", "8");
        wordToNumberMap.put("nine", "9");

        // Ersetze die Wörter in der Eingabezeichenkette
        for (java.util.Map.Entry<String, String> entry : wordToNumberMap.entrySet()) {
            String word = entry.getKey();
            String number = entry.getValue();
            input = input.replaceAll( word , word.charAt(0) + number + word.substring(1));
        }

        return input;
    }
}