public class Main {

    public static void main(String[] args) {
        System.out.println("--- Snakes and Ladders ---");

        // Ask for player count

        Driver game = new Driver(4);
        game.play();

        System.out.println("--- Program Terminated ---");
    }
}
