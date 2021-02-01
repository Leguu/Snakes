/**
 * Asil Erturan (40164714) and Christian Jerjian (40031909)
 * COMP249
 * Assignment #1
 * 2020-02-05
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("--- Snakes and Ladders ---");

        // for keyboard input
        Scanner s = new Scanner(System.in);

        // Ask for player count
        int playerCount = 0;

//        ------------------------ Need to stop after 4 attempt, look at part 2. i think the best way to do this is to use for loop ----------------------------
        while (playerCount == 0) {
            try {
                Driver.promptUser("How many players are you");
                playerCount = Integer.parseInt(s.next());
            } catch (NumberFormatException e) {
                System.out.println("Error: Please input a number");
            }
        }

        Driver game = new Driver(playerCount);
        game.play();


        System.out.println("--- Program Terminated ---");
        s.close();
    }
}
