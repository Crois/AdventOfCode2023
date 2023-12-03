package day3;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class EngineSchematicSolver {

    public static void main(String[] args) {
        try {
            // Read the engine schematic from a file
            System.out.println("Test 1");
            solveDay3Puzzle("data/day3/test1");
            System.out.println("Puzzle 1");
            solveDay3Puzzle("data/day3/puzzle1"); // 427606 to low
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void solveDay3Puzzle(String filePath) throws IOException {
        String engineSchematic = readEngineSchematic(filePath);

        // Solve the puzzle and print the result
        int[] result = sumPartNumbers(engineSchematic);
        System.out.println("Sum of all part numbers: " + result[0]);
        System.out.println("Sum of all gear factors: " + result[1]);
    }

    private static String readEngineSchematic(String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }
        return sb.toString();
    }

    private static int[] sumPartNumbers(String engineSchematic) {
        String[] lines = engineSchematic.split("\n");
        int sumPart1 = 0;
        int sumPart2 = 0;

        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines[i].length(); j++) {
                char currentChar = lines[i].charAt(j);

                // Check if the current character is a symbol
                if (isSymbol(currentChar)) {
                    // Sum the adjacent numbers
                    Set<Integer> partNumbers = getAdjacentNumbers(lines, i, j);
                    sumPart1 += partNumbers.stream().mapToInt(Integer::intValue).sum();
                    if(partNumbers.size() == 2 && currentChar == '*') {
                        sumPart2 += partNumbers.stream().mapToInt(Integer::intValue).reduce(1, (a, b) -> a * b);
                    }
                }
            }
        }

        return new int[] { sumPart1, sumPart2 };
    }

    private static boolean isSymbol(char c) {
        // Define the symbols in the engine schematic
        return c != '.' && !Character.isDigit(c);
    }

    private static Set<Integer> getAdjacentNumbers(String[] lines, int row, int col) {

        int[][] directions = {
                        {-1, 0}, {-1, 1}, {0, 1}, {1, 1},
                        {1, 0}, {1, -1}, {0, -1}, {-1, -1}
        };
        Set<Integer> partNumbers = new HashSet<>();
        for (int[] direction : directions) {
            int x = row + direction[0];
            int y = col + direction[1];
            if (isValidCoordinate(lines, x, y) && Character.isDigit(lines[x].charAt(y))) {
                // Add the adjacent number to the sum
                partNumbers.add(extractNumber(lines, x, y));
            }
        }



        return partNumbers;
    }



    private static int extractNumber(String[] lines, int row, int col) {
        StringBuilder number = new StringBuilder();

        String inputString = lines[row];

        char currentChar = inputString.charAt(col);

        // Wenn das aktuelle Zeichen eine Ziffer ist und der Zielwert entspricht
        if (Character.isDigit(currentChar)) {
            // Extrahiere die gesamte Zahl
            int startIndex = col;

            // Gehe nach links, um die Zahl zu erweitern
            while (startIndex >= 0 && Character.isDigit(inputString.charAt(startIndex))) {
                number.insert(0, inputString.charAt(startIndex));
                startIndex--;
            }

            // Gehe nach rechts, um die Zahl zu erweitern
            int endIndex = col + 1;
            while (endIndex < inputString.length() && Character.isDigit(inputString.charAt(endIndex))) {
                number.append(inputString.charAt(endIndex));
                endIndex++;
            }

        }
        return Integer.parseInt(number.toString());
    }

    private static boolean isValidCoordinate(String[] lines, int row, int col) {
        return row >= 0 && row < lines.length && col >= 0 && col < lines[row].length();
    }
}
