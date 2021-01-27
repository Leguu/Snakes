/**
 * Asil Erturan (40164714) and Christian Jerjian (40031909)
 * COMP249
 * Assignment #1
 * 2020-02-05
 */

import java.util.Random;
import java.util.Scanner;

public class Driver {

	private Board board;
	private Player[] players;

	public Driver(int players) {
		this.players = new Player[players];
		setUpPlayers();
	}


	public static int flipDice() {
		Random random = new Random();
		return random.nextInt(5) + 1;
	}

	public static void promptUser(String prompt) {
		System.out.println(prompt + ": ");
		System.out.print("> ");
	}

	// Make a user order, and so on.
	public void setUpPlayers() {

		int[] order = new int[players.length];
		

		System.out.println("Now deciding which player will start playing; ");

		for (int i = 0; i < players.length; i++) {
			promptUser("Player " + (i+1) + ", press f to flip the dice");

			Scanner s = new Scanner(System.in);
			String input = s.next();

			if (input.equalsIgnoreCase("f")) {
				int diceNumber = flipDice();
				System.out.println("Player " + (i+1) + " got a dice value of " + diceNumber );
				order[i] = diceNumber;	
			}else {
				System.out.println("Wrong Input");
				i--;
				continue;	
			}

		}

		//assigned dicednumber to player.setOrder
		for (int i = 0; i < players.length; i++) {
			int diceNumber = order[i];
			players[i].setOrder(diceNumber);		
		}

		//Sorting players.setOrder.
		for (int i = 0; i < players.length; i++) {
			if (players[i].getOrder() > players[i+1].getOrder()) {
				int temp = players[i].getOrder();
				players[i].setOrder(players[i+1].getOrder());
				players[i+1].setOrder(temp);
			}
			if (players[i].getOrder() == players[i+1].getOrder()) {
				System.out.println("A tie was achieved between player Player " + (i+1) + " and player " + (i+2) + ". Attempting to break the tie" );
				
			}
		}


	}

	public void play() {
		while (true) {
			Scanner s = new Scanner(System.in);
			promptUser("Type 'd' to display the board, 'r' to roll the next turn, or 'q' to quit");
			String input = s.next();

			if (input.equals("d")) {
				System.out.println(board);
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
