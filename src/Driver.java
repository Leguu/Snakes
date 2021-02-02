/**
 * Asil Erturan (40164714) and Christian Jerjian (40031909)
 * COMP249
 * Assignment #1
 * 2020-02-05
 */

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

class GameEnd extends Exception {
    Player player;

    GameEnd(Player player) {
        this.player = player;
    }
}

public class Driver {
    private final Board board = new Board();
    private final Player[] players;

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

    public static int flipDice() {
        Random random = new Random();
        return random.nextInt(6) + 1;
    }

    public static void promptUser(String prompt) {
        System.out.println(prompt + ": ");
        System.out.print("> ");
    }

    // Make a user order, and so on.
    public void setOrder() {
        System.out.println("Now deciding which player will start playing...");

        // Initialize order
        for (Player player : players) {
            int value = flipDice();
            System.out.printf("%s rolled a %d.\n", player.name, value);
            player.setOrder(value * 10);
        }
        Arrays.sort(players, Comparator.comparing(p -> -p.order));

        // Solve ties
        while (hasTie() != -1) {
            solveTie(hasTie());
        }

        // Print out order
        System.out.print("Reached final decision on order of playing: ");
        for (int i = 0; i < players.length - 1; i++) {
            System.out.printf("%s, ", players[i].name);
        }
        System.out.printf("and %s.\n", players[players.length - 1].name);
    }

    // Returns the index of a tie, or returns -1 if no ties were found.
    int hasTie() {
        for (int i = 0; i < players.length - 1; i++) {
            Player current = players[i];
            Player next = players[i + 1];
            if (current.order == next.order) return i;
        }
        return -1;
    }

    void solveTie(int index) {
        Player current = players[index];
        Player next = players[index + 1];

        System.out.printf("A tie was achieved between %s and %s. Re-rolling...\n", current.name, next.name);

        int currentdice = flipDice();
        int nextdice = flipDice();

        System.out.printf("%s rolled a %d.\n", current.name, currentdice);
        System.out.printf("%s rolled a %d.\n", next.name, nextdice);

        if (currentdice < nextdice) current.order -= 1;
        else current.order += 1;

        Arrays.sort(players, Comparator.comparing(p -> -p.order));
    }

    void doRound() throws GameEnd {
        for (Player player : players) {
            int diceNumber = flipDice();

            player.move(diceNumber);

            if (board.onLadder(player)) {
                player.ladder(board);
            } else {
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
                System.out.printf("Game is over! %s has won!\n", event.player.name);
                return;
            }
        }
    }
}
