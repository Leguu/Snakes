/*
 * Asil Erturan (40164714) and Christian Jerjian (40031909)
 * COMP249
 * Assignment #1
 * 2020-02-05
 */

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * GameEnd class which assigns the winning player to a new player calling winner.
 *
 * @author Asil Erturan (40164714) and Christian Jerjian (40031909)
 * @version 1
 *
 */
class GameEnd extends Exception {
    public Player winner;

    public GameEnd(Player winner) {
        this.winner = winner;
    }
}

/**
 *
 * Driver class containing a board and a the player array.
 *
 * @author Asil Erturan (40164714) and Christian Jerjian (40031909)
 * @version 1
 *
 */
public class Driver {
    private final Board board = new Board();
    private final Player[] players;

    /**
     * A parameterized constructor of the driver class. It creates an array of players and initializes player objects to this array.
     * @param players The number of participating players
     */
    public Driver(int players) {
        this.players = new Player[players];

        // Initialize players
        for (int i = 0; i < this.players.length; i++) {
            promptUser("Player " + (i + 1) + ", enter your name");

            Scanner s = new Scanner(System.in);
            String input = s.next();

            this.players[i] = new Player(0, input);
        }

        board.display(this.players);

        setOrder();
    }

    /**
     * This method flips the dice and returns an integer value.
     * @return A value between 1 to 6 to represent the coin flip
     */
    public static int flipDice() {
        Random random = new Random();
        return random.nextInt(6) + 1;
    }

    /**
     * This method prompts the user to input a string.
     * @param prompt The value inputed by user
     */
    public static void promptUser(String prompt) {
        System.out.println(prompt + ": ");
        System.out.print("> ");
    }

    /**
     * This method makes a user order based on their flip dice. If multiple users get the same dice number, the solveTie() method will be triggered. The method also prints out the order of players.
     *
     */
    private void setOrder() {
        System.out.println("Now deciding which player will start playing...");

        // Initialize order
        for (Player player : players) {
            int value = flipDice();
            System.out.printf("%s rolled a %d.\n", player.name, value);

//-----------------------------------Why * 10, does it make it easier to sort ? ---------------------------------
            player.order = value * 10;
        }
//---------------------------------- Please explain me what you did here lord Asil ------------------------------
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

    // Should be used in a while loop.
    // Solves the first tie it founds, and returns true if there may be more,
    // or false if the array has no ties.

    /**
     * This method solves the first tie it finds, and returns true if there are other ties in the setOrder().
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
     * This method solves the following ties after the first one and solves ties between the current and the preceding player. This method also sorts the player array based on each player's order.
     * @param current A player object which represents the current player
     * @param next  A player object representing the preceding player
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
     * This method does 1 round of flip dice to each players, move the players to the new positions while taking into considerations snakes and ladders.
     * @throws GameEnd ?????????????????????????????????????????????????????????????
     */
    private void doRound() throws GameEnd {
        for (Player player : players) {
            int diceNumber = flipDice();

            player.move(diceNumber);


            if (board.onLadder(player)) {
                player.ladder(board);
            }
            else {
                System.out.printf("%s went forward %d steps, now on position %d.\n",
                        player.name,
                        diceNumber,
                        player.position
                );
            }

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
                return;
            }
        }
    }
}
