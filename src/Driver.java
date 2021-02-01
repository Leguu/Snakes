/**
 * Asil Erturan (40164714) and Christian Jerjian (40031909)
 * COMP249
 * Assignment #1
 * 2020-02-05
 */

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

    void doRound() throws GameEnd {
        for (Player player : players) {
            int diceNumber = flipDice();

            player.doTurn(diceNumber);

            if (board.onLadder(player)) {
                player.ladder(board.tiles[player.getPosition()]);
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
