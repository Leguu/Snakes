/*
 * COMP249
 * Assignment #1
 * 2020-02-05
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * The board class containing all tiles, snakes, ladders positions as well as a display method.
 */
public class Board {
    /**
     * An array whose indices correspond to a position on the board, and contents to a ladder or snake.
     * <p>
     * We make no distinction between snakes and ladders; if the tiles[i] value is above your position, it is a snake.
     */
    private final int[] tiles = new int[101];

    /**
     * Default constructor of Board which initializes the snakes and ladders positions on the board. The tile position has a ladder if tile[i] is higher than the initial position.
     * The tile position has a snake if tile[i] is lower than the initial position
     */
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
        tiles[80] = 100;
        tiles[93] = 68;
        tiles[95] = 24;
        tiles[97] = 76;
        tiles[98] = 78;
    }

    /**
     * This method returns a boolean which tells you whether or not a player stepped on a ladder or snake.
     *
     * @param player The player which may have stepped on a ladder or snake
     * @return Returns true if player step on a ladder or snake, and return false if player did not step on a ladder or snake
     */
    public boolean onLadder(Player player) {
        return tiles[player.position] > 0;
    }

    /**
     * This method returns the position where the ladder and snakes points to.
     *
     * @param position The position of ladder and snake
     * @return returns the position of ladder and snake
     */
    public int getLadder(int position) {
        return tiles[position];
    }

    /**
     * This method displays the function on the console.
     *
     * @param players This is the player array which will have their name represented on the display
     */
    public void display(Player[] players) {
        // Print a divider.
        System.out.println("┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐");

        // Next, go through every row and print the row number and its contents
        for (int i = 9; i >= 0; i -= 1) {
            boolean reversed = i % 2 != 0;

            System.out.print("│");
            // Print out the first row... i.e, the snakes and ladders
            if (!reversed) for (int j = i * 10 + 1; j <= i * 10 + 10; j += 1) {
                if (tiles[j] > 0) System.out.printf("%3d│", tiles[j]);
                else System.out.print("   │");
            }
            else for (int j = i * 10 + 10; j > i * 10; j -= 1) {
                if (tiles[j] > 0) System.out.printf("%3d│", tiles[j]);
                else if (j == 100) System.out.print("WIN│"); // Special Case for the 100th tile
                else System.out.print("   │");
            }
            System.out.print("\n│");

            // Print out players.
            // Sort the players with their positions ascending or descending, depending on whether the row is reversed.
            if (reversed) Arrays.sort(players, Comparator.comparing(p -> -p.position));
            else Arrays.sort(players, Comparator.comparing(p -> p.position));

            // Select every player which is in the current row and create a list.
            ArrayList<Player> current = new ArrayList<>();
            for (Player player : players)
                if (i * 10 < player.position & player.position <= i * 10 + 10)
                    current.add(player);

            // Print the players in the current row, making sure that the distances are printed correctly. 
            for (int id = 0; id < current.size(); id += 1) {
                Player player = current.get(id);
                Player previous = null;
                if (id > 0) previous = current.get(id - 1);
                if (previous != null) if (player.position == previous.position) continue;

                if (!reversed) for (int j = previous == null ? 1 : previous.position - (i * 10) + 1;
                                    j < player.position - i * 10;
                                    j += 1
                )
                    System.out.print("   │");
                else for (int j = 0;
                          j < (previous == null ? 10 - (player.position - (i * 10)) : previous.position - player.position - 1);
                          j += 1
                )
                    System.out.print("   │");
                System.out.printf("%3s│", player.name.length() < 3 ? player.name : player.name.substring(0, 3));
            }

            // Print out the remainder of the row... If the row has no players, then just print an empty row.
            if (current.size() > 0)
                if (!reversed) for (int j = 0; j < (i * 10 + 10) - current.get(current.size() - 1).position; j += 1)
                    System.out.print("   │");
                else for (int j = 1; j < (current.get(current.size() - 1).position - (i * 10)); j += 1)
                    System.out.print("   │");
            else System.out.print("   │   │   │   │   │   │   │   │   │   │");

            System.out.println();

            // Print out row numbers
            System.out.print("│");
            if (reversed) for (int j = i * 10 + 10; j > i * 10; j -= 1)
                System.out.printf("%3d│", j);
            else for (int j = i * 10; j < i * 10 + 10; j += 1)
                System.out.printf("%3d│", j + 1);
            System.out.println();

            // Print out the separator or the bottom line.
            if (i > 0) System.out.println("├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤");
            else System.out.println("└───┴───┴───┴───┴───┴───┴───┴───┴───┴───┘");
        }
    }
}
