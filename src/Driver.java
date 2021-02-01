/**
 * Asil Erturan (40164714) and Christian Jerjian (40031909)
 * COMP249
 * Assignment #1
 * 2020-02-05
 */

import java.util.Random;
import java.util.Scanner;

public class Driver {

    private final Board board = new Board();
    private Player[] players;

    public Driver(int players) {
        this.players = new Player[players];
        setUpPlayers();
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
    public void setUpPlayers() {

        // Enter names
        for (int i = 0; i < players.length; i++) {
            promptUser("Player " + (i + 1) + ", enter your name");

            Scanner s = new Scanner(System.in);
            String input = s.next();

            players[i] = new Player(0, input);
        }

        board.display(players);

        System.out.println("Now deciding which player will start playing; ");

        assignDiceRoll();

        for (int i = 0; i < players.length - 1; i++) {
            for (int j = 0; j < (players.length - i - 1); j++) {
                if (players[j].getOrder() > players[j + 1].getOrder()) {
                    Player temp = players[j];
                    players[j] = players[j + 1];
                    players[j + 1] = temp;
                } else if (players[j].getOrder() == players[j + 1].getOrder()) {
                    tie(j);
                }
            }
        }
        System.out.print("Reached final decision on order of playing: ");
        for (Player player : players) {
            System.out.print(player.getName() + ", ");
        }
        System.out.println();
    }

    public void tie(int j) {
        int rollForTie1, rollForTie2;

        do {
            System.out.println("A tie was achieved between " + players[j].getName() + " and " + players[j + 1].getName() + ". Attempting to break the tie");
            rollForTie1 = flipDice();
            System.out.println(players[j].getName() + " got a dice value of " + rollForTie1);
            rollForTie2 = flipDice();
            System.out.println(players[j + 1].getName() + " got a dice value of " + rollForTie2);

            if (rollForTie1 > rollForTie2) {
                //swap
                Player temp = players[j];
                players[j] = players[j + 1];
                players[j + 1] = temp;
            } else if (rollForTie2 > rollForTie1) {
                //swap
                Player temp = players[j + 1];
                players[j + 1] = players[j];
                players[j] = temp;
            }

        }
        while (rollForTie1 == rollForTie2);
    }


    public void assignDiceRoll() {
        for (Player player : players) {
            int diceNumber = flipDice();
            System.out.println(player.getName() + " got a dice value of " + diceNumber);
            player.setOrder(diceNumber);
        }
    }

    public void checkPlayerWin() {
        for (Player player : players) {
            if (player.position == 100) {
                System.out.println(player.getName() + " won!");
                System.exit(0);
            }
        }
    }

    public void play() {
        while (true) {
            Scanner s = new Scanner(System.in);
            promptUser("Type 'd' to display the board, 'r' to roll the next turn, or 'q' to quit");
            String input = s.next();

            if (input.equalsIgnoreCase("d")) {
                System.out.println(board);
                continue;
            }

            if (input.equalsIgnoreCase("q")) {
                System.out.println("Game ended prematurely!");
                return;
            }

            if (!input.equalsIgnoreCase("r")) {
                System.out.println("That was invalid input!");
                continue;
            }


            boolean playerWin = false;
            //------------------------------------ I need to put GETPOSITION--------------------------------------------

            while (true) {
                for (Player player : players) {

                    int diceNumber = flipDice();

                    player.setPosition((player.position) + diceNumber);

                    if (player.getPosition() > 100) {
                        int excessNumber = player.getPosition() - 100;
                        System.out.print(player.getName() + " has rolled " + diceNumber + " on the " + (player.position - diceNumber) + "th position");
                        player.setPosition(100 - excessNumber);
                        System.out.println(" which brings him/her back to the " + (player.getPosition()) + "th position");
                        checkPlayerWin();
                    } else if (player.getPosition() < 100) {
                        for (int j = 0; j < board.tiles.length; j++) {
                            if ((board.tiles[j] > 0) && (j == player.getPosition())) {
                                //got hit by a snake or ladder. set a new position
                                int temp = player.getPosition();
                                player.setPosition(board.tiles[j]);

                                //hit by ladder
                                if (temp < player.getPosition()) {
                                    System.out.println(player.getName() + " got a dice value of " + diceNumber + " and stepped on a ladder; now in square " + (player.getPosition()));
                                    checkPlayerWin();
                                    break;
                                }
                                //hit by snake
                                else if (temp > player.getPosition()) {
                                    System.out.println(player.getName() + " got a dice value of " + diceNumber + " and stepped on a snake; now in square " + (player.getPosition()));
                                    checkPlayerWin();
                                    break;
                                }

                            } else if ((board.tiles[j] == 0) && (j == player.getPosition())) {
                                System.out.println(player.getName() + " got a dice value of " + diceNumber + "; now in square " + (player.getPosition()));
                                checkPlayerWin();
                            }
                        }
                    } else {
                        checkPlayerWin();
                    }
                }
            }


        }
    }
}
