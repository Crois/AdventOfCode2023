package day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ElfGameSimulation {

    public static final int COLOR_COUNT = 3;

    public static void main(String[] args) {
        playGame("data/day2/test1", new int[] { 12, 13, 14 });
        playGame("data/day2/test12", new int[] { 12, 13, 14 });
        playGame("data/day2/puzzle1", new int[] { 12, 13, 14 });
    }

    private static void playGame(String filePath, int[] targetCounts) {
        List<Game> games = loadGamesFromFile(filePath);
        List<Integer> possibleGames = findPossibleGames(games, targetCounts);
        int sumOfIDs = calculateSumOfIDs(possibleGames);
        Long minDiceFactor = calucalateMinDiceFactor(games);
        System.out.println("Possible games: " + possibleGames);
        System.out.println("Sum of IDs: " + sumOfIDs);
        System.out.println("Power of a Set of Cubes: " + minDiceFactor);
    }

    private static Long calucalateMinDiceFactor(List<Game> games) {
        Long factor = 0L;
        for (Game g : games) {
            Long multipliedMin = g.getMultipliedMin();
            //            printIntArray(g.getMinPossible());
            //            System.out.println(multipliedMin);
            factor += multipliedMin;
        }
        return factor;
    }

    static void printIntArray(int[] array) {
        for (int element : array) {
            System.out.print(element + " ");
        }
        System.out.println(); // Neue Zeile für bessere Lesbarkeit
    }

    static List<Game> loadGamesFromFile(String filePath) {
        List<Game> games = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int gameId = extractGameId(line);
                String subsets = line.substring(line.indexOf(":") + 2); // Extract subsets part
                games.add(new Game(gameId, subsets));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return games;
    }

    static int extractGameId(String line) {
        // Extract game ID from the line, assuming the line starts with "Game X:"
        return Integer.parseInt(line.split(":")[0].substring(5).trim());
    }

    static List<Integer> findPossibleGames(List<Game> games, int[] targetCounts) {
        List<Integer> possibleGames = new ArrayList<>();
        for (Game game : games) {
            if (isPossibleGame(game, targetCounts)) {
                possibleGames.add(game.getId());
            }
        }
        return possibleGames;
    }

    static boolean isPossibleGame(Game game, int[] targetCounts) {
        boolean returnValue = true;
        int[] counts; // counts[0] = red, counts[1] = green, counts[2] = blue
        for (String subset : game.getSubsets()) {
            counts = new int[COLOR_COUNT];
            String[] hand = subset.split(", ");
            for (String dices : hand) {
                String[] parts = dices.split(" ");
                int count = Integer.parseInt(parts[0]);
                String color = parts[1];
                int colorIndex = getColorIndex(color);
                counts[colorIndex] = count;
                game.setMinPossible(count, colorIndex);
                for (int i = 0; i < COLOR_COUNT; i++) {
                    if (counts[i] > targetCounts[i]) {
                        returnValue = false;
                        break;
                    }
                }
            }

        }
        return returnValue;
    }

    static int getColorIndex(String color) {
        switch (color) {
            case "red":
                return 0;
            case "green":
                return 1;
            case "blue":
                return 2;
            default:
                throw new IllegalArgumentException("Invalid color: " + color);
        }
    }

    static int calculateSumOfIDs(List<Integer> possibleGames) {
        int sum = 0;
        for (int gameID : possibleGames) {
            sum += gameID;
        }
        return sum;
    }
}

class Game {
    private final int id;
    private final List<String> subsets;

    public int[] getMinPossible() {
        return minPossible;
    }

    public void setMinPossible(int min, int colorIndex) {
        minPossible[colorIndex] = Math.max(min, minPossible[colorIndex]);
    }

    private int[] minPossible = new int[] { 0, 0, 0 };

    public Game(int id, String input) {
        this.id = id;
        this.subsets = Arrays.asList(input.split("; "));
    }

    public int getId() {
        return id;
    }

    public List<String> getSubsets() {
        return subsets;
    }

    public Long getMultipliedMin() {
        return (long) (minPossible[0] * minPossible[1] * minPossible[2]);
    }
}