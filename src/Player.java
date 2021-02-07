/*
 * Asil Erturan (40164714) and Christian Jerjian (40031909)
 * COMP249
 * Assignment #1
 * 2020-02-05
 */

/**
 * The player class where the blueprint for each player is set. It has for variables the position, name and order of the player.
 */
public class Player {
    public int position;
    public String name;
    public int order;


    /**
     * This is the parameterized constructor of the Player class.
     * @param position Position of the player on the board
     * @param name  Name of the player
     */
    public Player(int position, String name) {
        this.position = position;
        this.name = name;
    }

    /**
     * This method increases the position of the player based on the dice roll.
     * @param dice The number of the dice roll
     */
    public void move(int dice) {
        position += dice;
        if (position > 100) {
            int excess = position - 100;
            System.out.printf("%s has rolled %d on position %d,", name, dice, position-dice);
            position = 100 - excess;
            System.out.printf(" which sends them back to position %d. \n", position);
        }
    }


    /**
     * This method is called when a player stepped on a ladder or snake and assigns the new position to the player.
     * @param board Contains the position of the snakes and ladder on the board
     */
    public void ladder(Board board) {
        int ladder = board.getLadder(position);
        System.out.printf("Player %s stepped on a %s! Now on position %d.\n",
                name,
                ladder < position ? "snake" : "ladder",
                ladder);
        this.position = ladder;
    }
}
