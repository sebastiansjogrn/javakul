import java.util.Arrays;
import java.util.Random;

import static java.lang.System.exit;
import static java.lang.System.out;

/*
 *  Simulation of LCR game. See, https://en.wikipedia.org/wiki/LCR_(dice_game)
 */
public class Ex8LCRSimulation {

    public static void main(String[] args) {
        new Ex8LCRSimulation().program();
    }

    final Random rand = new Random();

    void program() {
        //test();        // < --- Uncomment to run tests ---

        // Hard coded data
        final Player[] players = {new Player("olle", 3),
                new Player("fia", 3), new Player("pelle", 3)};
        Player current = players[0];
        out.println("Simulation starts");
        displayPlayers(players);

        // Runt the simulation
        // TODO
    }

    // ---- Logical methods -----------------




    int indexOf(Player[] players, Player player) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == player) {
                return i;
            }
        }
        return -1;
    }

    void displayState(Player actual, char[] result, Player[] players) {
        out.print(actual.name + " got ");
        out.println(Arrays.toString(result));
        displayPlayers(players);
        /*for (int i = 0; i < players.length; i++) {
            out.print(players[i].name + ":" + players[i].chips + " ");
        }
        out.println();*/
    }

    void displayPlayers( Player[] players ){
         for (int i = 0; i < players.length; i++) {
            out.print(players[i].name + ":" + players[i].chips + " ");
        }
        out.println();
    }

    // ------- Class to hold player data -----------

    class Player {
        String name;
        int chips;

        public Player(String name, int chips) {
            this.name = name;
            this.chips = chips;
        }
    }

    // ********************** Testing *************************************''

    void test() {
        // Local hard coded test data
        Player[] players = {new Player("1", 1),
                new Player("2", 2), new Player("3", 3)};

        exit(0);
    }
}
