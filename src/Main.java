/*
 * Asil Erturan (40164714) and Christian Jerjian (40031909)
 * COMP249
 * Assignment #1
 * 2020-02-05
 */

import java.util.Scanner;

/**
 * The main class of the program where the program starts running.
 *
 * @author Asil Erturan (40164714) and Christian Jerjian (40031909)
 * @version 1
 */
public class Main {

    /**
     * The main method is where we find the player count and run the driver.
     *
     * @param args ??????????????????????????????????????????????????????????????
     */
    public static void main(String[] args) {
        System.out.println("--- Snakes and Ladders ---");

        // for keyboard input
        Scanner s = new Scanner(System.in);

        // Ask for player count
        int playerCount = 0;


        for (int attempts = 0; playerCount < 2 || 4 < playerCount; attempts += 1) {

            if (attempts == 4) {
                System.out.println("Failed to enter player count 4 times. Exiting...");
                return;
            }

            Driver.promptUser("Input the number of players");

            try {
                playerCount = Integer.parseInt(s.nextLine());
                if ((playerCount < 2 || 4 < playerCount) && attempts < 3)
                    //if entered a number but it is not between 2 to 4
                    System.out.println("Please enter a number between 2 and 4.");
            } catch (NumberFormatException e) {
                //if did NOT enter a number
                System.out.println("Error: Please input a number");
            }
        }


        Driver game = new Driver(playerCount);
        game.play();


        System.out.println("--- Program Terminated ---");
    }
}
