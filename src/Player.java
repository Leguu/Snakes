/*
 * Asil Erturan (40164714) and Christian Jerjian (40031909)
 * COMP249
 * Assignment #1
 * 2020-02-05
 */


public class Player {
    public int position;
    public String name;
    public int order;



    public Player(int position, String name) {
        this.position = position;
        this.name = name;
    }


    public void move(int dice) {
        position += dice;
    }



    public void ladder(Board board) {
        int ladder = board.getLadder(position);
        System.out.printf("Player %s stepped on a %s! Now on position %d.\n",
                name,
                ladder < position ? "snake" : "ladder",
                ladder);
        this.position = ladder;
    }
}
