/**
 * Asil Erturan (40164714) and Christian Jerjian (40031909)
 * COMP249
 * Assignment #1
 * 2020-02-05
 */

public class Board {
    // Contains the snakes / ladders
    private int[] tiles;

    public int[] getTiles() {
		return tiles;
	}

	public void setTiles(int[] tiles) {
		this.tiles = tiles;
	}

	// Initialize players    -> Constructor 
    public Board() {
        this.tiles = new int[100];
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

    public void display(Player[] players) {
        System.out.println("Something... Printing board");
    }
}
