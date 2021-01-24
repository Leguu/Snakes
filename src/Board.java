import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Asil Erturan (40164714) and Christian Jerjian (40031909)
 * COMP249
 * Assignment #1
 * 2020-02-05
 */

public class Board {
    // Contains the snakes / ladders
    int[] tiles = new int[101];

    // Initialize players
    public Board() {
        tiles[1] = 20;
        tiles[4] = 14;
        tiles[9] = 31;
        tiles[16] = 6;
        tiles[21] = 42;
        tiles[28] = 84;
        tiles[36] = 44;
        tiles[48] = 30;
        tiles[51] = 67;
        tiles[64] = 60;
        tiles[71] = 91;
        // Changed this from 100 to 99, in order to display the board better.
        // You shouldn't have 2 'win' tiles anyway.
        tiles[80] = 99;
        tiles[93] = 68;
        tiles[95] = 24;
        tiles[97] = 76;
        tiles[98] = 78;
    }

    void display(Player[] players) {
        // First print the top row of labels, i.e., A B C...
        System.out.print("  │");
        for (int i = 1; i <= 10; i += 1)
            System.out.printf("%2d│", i); // i + 64
        System.out.println();
        System.out.println("──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤");

        // Next Go through every row and print the row number and its contents
        for (int i = 9; i >= 0; i -= 1) {
            // Print out label for the row
            System.out.printf("%2d│", i);

            // Print out the first row... i.e, the snakes and ladders
            for (int j = i * 10 + 1; j <= i * 10 + 10; j += 1) {
                if (tiles[j] > 0) System.out.printf("%2d│", tiles[j]);
                else if (j == 100) System.out.print("WIN→");
                else System.out.print("  │");
            }
            System.out.print("\n  │");

            // Print out players.
            Arrays.sort(players, Comparator.comparing(p -> p.position));

            ArrayList<Player> current = new ArrayList<>();
            for (Player player : players)
                if (i * 10 < player.position & player.position <= i * 10 + 10)
                    current.add(player);

            for (int id = 0; id < current.size(); id += 1) {
                Player player = current.get(id);
                Player previous = null;
                if (id > 0) previous = current.get(id - 1);

                for (int j = previous == null ? 1 : previous.position - (i * 10) + 1;
                     j < player.position - i * 10;
                     j += 1
                )
                    System.out.print("  │");
                System.out.printf("%s│", player.name.substring(0, 2));
            }

            if (current.size() > 0)
                for (int j = 0; j < (i * 10 + 10) - current.get(current.size() - 1).position; j += 1)
                    System.out.print("  │");
            else System.out.print("  │  │  │  │  │  │  │  │  │  │");

            System.out.println();

            // Print out the separator or the bottom line.
            if (i > 0) System.out.println("──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤");
            else System.out.println("──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘");
        }
    }
}
