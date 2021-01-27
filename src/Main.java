/**
 * Asil Erturan (40164714) and Christian Jerjian (40031909)
 * COMP249
 * Assignment #1
 * 2020-02-05
 */

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

		Scanner s = new Scanner(System.in);		// for keyboard input
        System.out.println("--- Snakes and Ladders ---");
        
        
        // Ask for player count
        Driver.promptUser("How many players are you");
        int playerCount = s.nextInt();

        Driver game = new Driver(playerCount);
        game.play();
        

        System.out.println("--- Program Terminated ---");
        s.close();
    }
}
