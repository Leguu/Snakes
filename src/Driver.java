/**
 * Asil Erturan (40164714) and Christian Jerjian (40031909)
 * COMP249
 * Assignment #1
 * 2020-02-05
 */

import java.util.Random;
import java.util.Scanner;

public class Driver {
    private Board board = new Board();
    private Player[] players;

    Driver(int players) {
        this.players = new Player[players];
        setUpPlayers();
    }
    
    static int flipDice() {
        Random random = new Random();
        return random.nextInt(5) + 1;
    }
    
    static void promptUser(String prompt) {
        System.out.println(prompt + ": ");
        System.out.print("> ");
    }

    // Make a user order, and so on.
    void setUpPlayers() {
        // Code here!
    }

    void play() {
        while (true) {
            Scanner s = new Scanner(System.in);
            promptUser("Type 'd' to display the board, 'r' to roll the next turn, or 'q' to quit");
            String input = s.next();
            
            if (input.equals("d")) {
                board.display(players);
                continue;
            }
            
            if (input.equals("q")) {
                System.out.println("Game ended prematurely!");
                return;
            }

            if (!input.equals("r")) {
                System.out.println("That was invalid input!");
                continue;
            }

            for (Player player : players) {
                // Code here
            }
        }
    }
}
