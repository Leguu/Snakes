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

    public void ladder(int position) {
        System.out.printf("Player %s stepped on a %s! Now on position %d.\n",
                name,
                position < this.position ? "snake" : "ladder",
                position);
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
