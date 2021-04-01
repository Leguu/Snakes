/*
 * COMP249
 * Assignment #1
 * 2020-02-05
 */

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

/**
 * GameEnd class which assigns the winning player to a new player calling winner. The exception is thrown when a player is on top of the 100th tile.
 */
class GameEnd extends Exception {
    /**
     * The winner of the game.
     */
    public Player winner;

    /**
     * Default constructor. Sets the winner.
     *
     * @param winner The winner of the game.
     */
    public GameEnd(Player winner) {
        this.winner = winner;
    }
}

/**
 * Driver class containing a board and a the player array.
 */
public class Driver {
    /**
     * Our board which contains snakes and ladders.
     */
    private final Board board = new Board();
    /**
     * Players in our game. The size is set on startup, in main().
     */
    private final Player[] players;

    /**
     * A parameterized constructor of the driver class. It creates an array of players and initializes player objects to this array.
     *
     * @param players The number of participating players
     */
    public Driver(int players) {
        this.players = new Player[players];

        // Initialize players
        for (int i = 0; i < this.players.length; i++) {
            promptUser("Player " + (i + 1) + ", enter your name");

            Scanner s = new Scanner(System.in);
            String input = s.nextLine().strip();

            if (input.isEmpty()) {
                System.out.println("Your name cannot be empty!");
                i -= 1;
                continue;
            }

            if (Arrays.stream(this.players).anyMatch(p -> p != null && p.name.equals(input))) {
                System.out.println("That name has already been chosen. Try again.");
                i -= 1;
                continue;
            }

            this.players[i] = new Player(0, input);
        }

        board.display(this.players);

        setOrder();
    }

    /**
     * This method flips the dice and returns an integer value.
     *
     * @return A value between 1 to 6 to represent the coin flip
     */
    public static int flipDice() {
        Random random = new Random();
        return random.nextInt(6) + 1;
    }

    /**
     * This method prompts the user to input a string.
     *
     * @param prompt The string displayed to the user
     */
    public static void promptUser(String prompt) {
        System.out.println(prompt + ": ");
        System.out.print("> ");
    }

    /**
     * This method makes a user order based on their flip dice.
     * If multiple users get the same dice number, the solveTie() method will be triggered.
     * The method also prints out the order of players.
     */
    private void setOrder() {
        System.out.println("Now deciding which player will start playing...");

        // Initialize order
        for (Player player : players) {
            int value = flipDice();
            System.out.printf("%s rolled a %d.\n", player.name, value);
            player.order = value * 10;
        }
        Arrays.sort(players, Comparator.comparing(p -> -p.order));

        // Solve ties
        while (true) {
            if (!solveTies()) break;
        }

        // Print out order
        System.out.print("Reached final decision on order of playing: ");
        for (int i = 0; i < players.length - 1; i++) {
            System.out.printf("%s, ", players[i].name);
        }
        System.out.printf("and %s.\n", players[players.length - 1].name);
    }

    /**
     * This method solves the first tie it encounters, and returns true if there are other ties in the setOrder().
     *
     * @return A boolean value is returned based on wheter the tie has been solved
     */
    private boolean solveTies() {
        for (int i = 0; i < players.length - 1; i++) {
            Player current = players[i];
            Player next = players[i + 1];
            if (current.order == next.order) {
                solveTie(current, next);
                return true;
            }
        }
        return false;
    }

    /**
     * This methods runs through the player array and solves the tie between 2 players.
     * This method also sorts the player array based on a descending order.
     *
     * @param current A player object which represents the current player
     * @param next    A player object representing the proceeding player
     */
    private void solveTie(Player current, Player next) {
        System.out.printf("A tie was achieved between %s and %s. Re-rolling...\n", current.name, next.name);

        int currentDice = flipDice();
        int nextDice = flipDice();

        System.out.printf("%s rolled a %d.\n", current.name, currentDice);
        System.out.printf("%s rolled a %d.\n", next.name, nextDice);

        if (currentDice > nextDice) current.order += 1;
        else current.order -= 1;

        Arrays.sort(players, Comparator.comparing(p -> -p.order));
    }

    /**
     * This method does 1 round of flip dice to each players,
     * move the players to the new positions while taking into considerations snakes and ladders.
     *
     * @throws GameEnd Throws GameEnd when the win condition is detected
     */
    private void doRound() throws GameEnd {
        for (Player player : players) {
            int diceNumber = flipDice();

            player.move(diceNumber, board);

            if (player.position == 100) {
                throw new GameEnd(player);
            }
        }
    }

    /**
     * This method asks for an input from the users and gives the choice between displaying the board, roll for the next turn, or quit the program.
     */
    public void play() {
        Scanner s = new Scanner(System.in);
        String input = "";

        while (true) {
            if (!input.equalsIgnoreCase("a")) {
                promptUser("Type 'd' to display the board, 'r' to roll the next turn, 'a' to do all rounds, or 'q' to quit");
                input = s.next();
            }

            if (input.equalsIgnoreCase("d")) {
                board.display(players);
                continue;
            }

            if (input.equalsIgnoreCase("q")) {
                System.out.println("Game ended prematurely!");
                return;
            }

            if (!input.equalsIgnoreCase("r") && !input.equalsIgnoreCase("a")) {
                System.out.println("That was invalid input!");
                continue;
            }

            try {
                doRound();
            } catch (GameEnd event) {
                System.out.printf("Game is over! %s has won!\n", event.winner.name);

                // Print out order
                Arrays.sort(players, Comparator.comparing(p -> -p.position));
                System.out.print("The order of winners: ");
                for (int i = 0; i < players.length - 1; i++) {
                    System.out.printf("%s, ", players[i].name);
                }
                System.out.printf("and %s.\n", players[players.length - 1].name);

                board.display(players);

                return;
            }
        }
    }
}
