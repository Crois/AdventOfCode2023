package day4.day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CardScrapperSolver {

    public static void main(String[] args) {
        try {
            // Read the engine schematic from a file
            System.out.println("Test 1");
            solveDay4Puzzle("data/day4/test1");
            System.out.println("Puzzle 1");
            solveDay4Puzzle("data/day4/puzzle1"); // 427606 to low
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void solveDay4Puzzle(String filePath) throws IOException {
        String engineSchematic = readCardScrappers(filePath);

        Map<Integer, Integer> cardCountMap = new HashMap<>();
        // Solve the puzzle and print the result
        long result = sumCardPoints(engineSchematic, cardCountMap);
        int calculateSum = calculateSum(cardCountMap);
        System.out.println("Sum of all part numbers: " + result);
        System.out.println("TotalScratchCards: " + calculateSum);
    }

    private static String readCardScrappers(String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }
        return sb.toString();
    }

    private static long sumCardPoints(String cardScrappers, Map<Integer, Integer> cardCountMap) {
        String[] lines = cardScrappers.split("\n");
        long generalPoints = 0L;
        for (String line : lines) {
            int count = 0;
            String[] cardAndScrapSplit = line.split(":");
            String[] cardNumberParts = cardAndScrapSplit[0].split(" ");
            int cardNumber = Integer.parseInt(cardNumberParts[cardNumberParts.length - 1]);
            int copies = 0;
            if(cardCountMap.containsKey(cardNumber)) {
                copies = cardCountMap.get(cardNumber);
            }
            updateHashMap(cardCountMap,cardNumber);
            String[] split = cardAndScrapSplit[1].split("\\|");
            String winnerNumbersS = split[0].trim();
            String myNumbersS = split[1].trim();
            String[] winnerNumbersArr = winnerNumbersS.split("\s+");
            Set<Integer> winnerNumbersSet = new HashSet<>();
            for (String winnerNumber : winnerNumbersArr) {
                if (!winnerNumber.isEmpty()) {
                    winnerNumbersSet.add(Integer.parseInt(winnerNumber));
                }
            }
            String[] myNumbers = myNumbersS.split("\s+");
            for (String myNumber : myNumbers) {
                if (!myNumber.isEmpty() && winnerNumbersSet.contains(Integer.parseInt(myNumber))) {
                    count++;
                }
            }
            int cardPoints = count > 0 ? 1 : 0;
            for (int j = 0; j < copies + 1; j++) {

                int cardNumberIter = cardNumber;
                for (int i = 0; i < count; i++) {
                    cardNumberIter++;
                    updateHashMap(cardCountMap, cardNumberIter);

                }
            }
            while (--count > 0) {
                cardPoints *= 2;
            }
            generalPoints += cardPoints;
        }
        return generalPoints;
    }


    public static void updateHashMap(Map<Integer, Integer> integerMap, int key) {
        // Use the compute method to update the value associated with the key
        if(integerMap.containsKey(key)){
            integerMap.put(key, integerMap.get(key) + 1);
        } else {
            integerMap.put(key, 1);
        }
    }

    public static int calculateSum(Map<Integer, Integer> integerMap) {
        int sum = 0;

        // Alle Werte in der Map durchlaufen und aufsummieren
        for (int value : integerMap.values()) {
            sum += value;
        }

        return sum;
    }
}
