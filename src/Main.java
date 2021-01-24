// Asil Erturan (40164714) and Christian Jerjian (40031909)
// COMP249
// Assignment #1
// 2020-02-05

public class Main {

    public static void main(String[] args) {
        System.out.println("--- Snakes and Ladders ---");

        // Ask for player count

        Driver game = new Driver(4);
        game.play();

        System.out.println("--- Program Terminated ---");
    }
}
