/**
 * @author 鲨鱼爱兜兜
 * @date 2024/12/6 17:53
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final ArrayList<NimPlayer> players = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Welcome to Nim");
        Scanner scanner = new Scanner(System.in);
        String input;
        while (true) {
            System.out.print("$");
            input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println();
                System.exit(0);
            }
            handleCommand(input);
        }
    }

    private static void handleCommand(String input) {
        if (input.isEmpty()) {
            System.out.println("Please enter a valid command.");
            return;
        }
        String[] parts = input.split(" ", 2);
        String command = parts[0];
        String arguments = parts.length > 1 ? parts[1] : "";
        switch (command) {
            case "addplayer":
                addPlayer(arguments);
                break;
            case "removeplayer":
                removePlayer(arguments);
                break;
            case "editplayer":
                editPlayer(arguments);
                break;
            case "resetstats":
                resetStats(arguments);
                break;
            case "displayplayer":
                displayPlayer(arguments);
                break;
            case "rankings":
                displayRankings(arguments);
                break;
            case "startgame":
                startGame(arguments);
                break;
            default:
                System.out.println("Invalid command.");
        }
    }

    private static void addPlayer(String arguments) {
        String[] args = arguments.split(",");
        if (args.length != 3) {
            System.out.println("Invalid syntax. Correct format: addplayer username,family_name,given_name");
            return;
        }
        String username = args[0];
        String familyName = args[1];
        String givenName = args[2];
        for (NimPlayer player : players) {
            if (player.getUsername().equals(username)) {
                System.out.println("The player already exists.");
                return;
            }
        }
        players.add(new NimPlayer(username, familyName, givenName));
    }

    private static void removePlayer(String arguments) {
        if (arguments.isEmpty()) {
            System.out.println("Are you sure you want to remove all players? (Y/N)");
            Scanner scanner = new Scanner(System.in);
            String confirmation = scanner.nextLine().trim();
            if (confirmation.equalsIgnoreCase("Y"))
                players.clear();
            return;
        }
        String username = arguments.trim();
        NimPlayer playerToRemove = null;
        for (NimPlayer player : players) {
            if (player.getUsername().equals(username)) {
                playerToRemove = player;
                break;
            }
        }
        if (playerToRemove == null) {
            System.out.println("The player does not exist.");
            return;
        }
        players.remove(playerToRemove);
    }

    private static void editPlayer(String arguments) {
        String[] args = arguments.split(",");
        if (args.length != 3) {
            System.out.println("Invalid syntax. Correct format: editplayer username,new_family_name,new_given_name");
            return;
        }
        for (NimPlayer player : players) {
            if (player.getUsername().equals(args[0])) {
                player.setFamilyName(args[1]);
                player.setGivenName(args[2]);
                return;
            }
        }
        System.out.println("The player does not exist.");
    }

    private static void resetStats(String arguments) {
        if (arguments.isEmpty()) {
            System.out.println("Are you sure you want to reset all player statistics? (Y/N)");
            Scanner scanner = new Scanner(System.in);
            String confirmation = scanner.nextLine().trim();
            if (confirmation.equalsIgnoreCase("Y")) {
                players.forEach(NimPlayer::resetStats);
            }
            return;
        }
        String username = arguments.trim();
        for (NimPlayer player : players) {
            if (player.getUsername().equals(username)) {
                player.resetStats();
                return;
            }
        }
        System.out.println("The player does not exist.");
    }

    private static void displayPlayer(String arguments) {
        if (arguments.isEmpty()) {
            players.forEach(System.out::println);
            return;
        }
        String username = arguments.trim();
        boolean playerFound = players.stream()
                .filter(player -> player.getUsername().equals(username))
                .peek(System.out::println)
                .findFirst()
                .isPresent();
        if (!playerFound) System.out.println("The player does not exist.");
    }


    private static void displayRankings(String arguments) {
        sortPlayersByWinRate(arguments);
        players.forEach(player ->
                System.out.printf("%3d%% | %02d games | %s %s%n",
                        (int) (player.getWinRate() * 100),
                        player.getGamesPlayed(),
                        player.getGivenName(),
                        player.getFamilyName()));
    }

    private static void sortPlayersByWinRate(String order) {
        players.sort((p1, p2) -> {
            int winRateComparison = Double.compare(p2.getWinRate(), p1.getWinRate());
            if (winRateComparison == 0)
                return Integer.compare(p2.getGamesPlayed(), p1.getGamesPlayed());
            return order.equalsIgnoreCase("asc") ? Double.compare(p1.getWinRate(), p2.getWinRate()) : winRateComparison;
        });
    }


    private static void startGame(String arguments) {
        String[] args = arguments.split(",");
        if (args.length != 4) {
            System.out.println("Invalid syntax. Correct format: startgame initialstones,upperbound,username1,username2");
            return;
        }
        int initialStones = Integer.parseInt(args[0]);
        int upperBound = Integer.parseInt(args[1]);
        NimPlayer player1 = null;
        NimPlayer player2 = null;
        for (NimPlayer player : players) {
            if (player.getUsername().equals(args[2])) player1 = player;
            if (player.getUsername().equals(args[3])) player2 = player;
        }
        if (player1 == null || player2 == null) {
            System.out.println("One of the players does not exist.");
            return;
        }
        GameRound currentGame = new GameRound(initialStones, upperBound, player1, player2);
        currentGame.play();
    }
}
