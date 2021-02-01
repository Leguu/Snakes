/**
 * Asil Erturan (40164714) and Christian Jerjian (40031909)
 * COMP249
 * Assignment #1
 * 2020-02-05
 */

public class Player {
    int position;
    String name;
    int order;

    public Player(int position, String name) {
        this.position = position;
        this.name = name;
    }

    public void doTurn(int dice) {
        position += dice;

        if (position > 100) {
            int excessNumber = position - 100;
            System.out.print(name + " has rolled " + dice + " on the " + (position - dice) + "th position");
            position = 100 - excessNumber;
            System.out.println(" which brings him/her back to the " + position + "th position");
        }
    }

    public void ladder(Board board) {
        int ladder = board.tiles[position];
        System.out.printf("Player %s stepped on a %s! Now on position %d.\n",
                name,
                ladder < position ? "snake" : "ladder",
                ladder);
        this.position = ladder;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
