
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import static java.lang.System.*;

/*
 *  The Mexico dice game
 *  See https://en.wikipedia.org/wiki/Mexico_(game)
 *
 */
public class Mexico {

    public static void main(String[] args) {
        new Mexico().program();
    }

    final Random rand = new Random();
    final Scanner sc = new Scanner(in);
    final int maxRolls = 3;      // No player may exceed this
    final int startAmount = 3;   // Money for a player. Select any
    final int mexico = 1000;     // A value greater than any other

    void program() {
        //test();            // <----------------- UNCOMMENT to test

        int pot = 0;         // What the winner will get
        Player[] players;    // The players (array of Player objects)
        Player current;      // Current player for round
        Player leader;       // Player starting the round
        int max = maxRolls;  // Max rolls for the round
        boolean ongoing = false;        // Indicates if the game has moved on from the leader
        boolean round_over = false;     // Indicates if the round is over
        boolean returnRolls = true;     // Indicates if dice results should be displayed

        players = getPlayers();
        current = getRandomPlayer(players);
        leader = current;

        out.println("Mexico Game Started");
        statusMsg(players);

        while (players.length > 1) {   // Game over when only one player left

            // ----- In ----------
            String cmd = getPlayerChoice(current);
            if ("r".equals(cmd)) {

                // --- Process ------
                if (current.nRolls < max) {
                    current.nRolls++;
                    int[] dice = new int[2];
                    dice = diceRoll();
                    current.fstDice = dice[0];
                    current.secDice = dice[1];
                    returnRolls = true;

                } else {
                    out.println("No rolls left.\n");
                    current = next(players, current);
                    ongoing = true;
                    returnRolls = false;
                }
                // ---- Out --------
                if (returnRolls) {
                    roundMsg(current);
                }

            } else if ("n".equals(cmd)) {
                // Process
                if (current == leader) {
                    max = current.nRolls;
                }
                current = next(players, current);
                ongoing = true;
            } else {
                out.println("?");
            }


            if (current == leader && ongoing) {
                round_over = true;
            }
            if (round_over) {
                // --- Process -----
                pot++;
                ongoing = false;
                round_over = false;
                Player loser = getLoser(players);
                loser.amount--;
                if (loser.amount == 0) {
                    current = next(players, loser);
                    players = removeLoser(players, loser);
                } else {
                    current = loser;
                }
                leader = current;
                players = clearRoundResults(players);
                // ----- Out --------------------
                out.println("\nRound done " + loser.name + " lost!");
                out.println("Next to roll is " + current.name + "\n");
                max = maxRolls;

                statusMsg(players);
            }
        }
        out.println("Game Over, winner is " + players[0].name + ". Will get " + pot + " from pot");
    }


    // ---- Game logic methods --------------

    // TODO

    Player next(Player[] players, Player current) {
        int i = indexOf(players, current);
        i = (i + 1) % players.length;
        Player nCurrent = players[i];
        return nCurrent;
    }


    int[] diceRoll() {
        int d1 = rand.nextInt(6) + 1;
        int d2 = rand.nextInt(6) + 1;
        int[] die = {d1, d2};
        return die;
    }

    Player getLoser(Player[] players) {
        Player loser = players[0];
        for (int i = 1; i < (players.length); i++) {
            if (getScore(loser) > getScore(players[i])) {
                loser = players[i];
            }
        }
        return loser;
    }


    Player[] removeLoser(Player[] players, Player loser) {
        Player[] nPlayers = new Player[players.length - 1];
        int i = 0;
        for (int n = 0; n < players.length; n++) {
            if (players[n] != loser) {
                nPlayers[i] = players[n];
                i++;
            }
        }
        return nPlayers;
    }


    int indexOf(Player[] players, Player player) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == player) {
                return i;
            }
        }
        return -1;
    }

    Player getRandomPlayer(Player[] players) {
        return players[rand.nextInt(players.length)];
    }


    int getScore(Player player) {
        int n;
        if (player.fstDice > player.secDice) {
            n = (player.fstDice * 10 + player.secDice);
        } else if (player.fstDice < player.secDice) {
            n = (player.secDice * 10 + player.fstDice);
        } else {
            n = (player.fstDice * 10 + player.secDice) * 10;
        }
        if (n == 21) {
            n = 9001;
        }
        return n;
    }


    Player[] clearRoundResults(Player[] players) {
        for (int n = 0; n < players.length; n++) {
            players[n].fstDice = 0;
            players[n].secDice = 0;
            players[n].nRolls = 0;
        }
        return players;
    }


    // ---------- IO methods -----------------------

    Player[] getPlayers() {
        Player[] players = {new Player("Olle", startAmount, 0, 0, 0),
                new Player("Fia", startAmount, 0, 0, 0),
                new Player("Lisa", startAmount, 0, 0, 0)};

        return players;
    }

    void statusMsg(Player[] players) {
        out.print("Status: ");
        for (int i = 0; i < players.length; i++) {
            out.print(players[i].name + " " + players[i].amount + " ");
        }
        out.println();
    }

    void roundMsg(Player current) {
        out.println(current.name + " got " +
                current.fstDice + " and " + current.secDice);
    }

    String getPlayerChoice(Player player) {
        out.print("Player is " + player.name + " > ");
        return sc.nextLine();
    }

    // Possibly useful utility during development
    String toString(Player p) {
        return p.name + ", " + p.amount + ", " + p.fstDice + ", "
                + p.secDice + ", " + p.nRolls;
    }

    // Class for a player
    class Player {
        String name;
        int amount;   // Start amount (money)
        int fstDice;  // Result of first dice
        int secDice;  // Result of second dice
        int nRolls;   // Current number of rolls

        public Player(String n, int cash, int d1, int d2, int rolls) {
            name = n;
            amount = cash;
            fstDice = d1;
            secDice = d2;
            nRolls = rolls;
        }
    }

    /**************************************************
     *  Testing
     *
     *  Test are logical expressions that should
     *  evaluate to true (and then be written out)
     *  No testing of IO methods
     *  Uncomment in program() to run test (only)
     ***************************************************/
    void test() {
        // A few hard coded player to use for test
        // NOTE: Possible to debug tests from here, very efficient!
        Player[] ps = {new Player("Olle", 2, 2, 6, 1),
                new Player("Fia", 3, 6, 5, 2),
                new Player("Lisa", 1, 1, 1, 1)};

        //out.println(next(ps, ps[0]) == ps[1]);
        //out.println(next(ps, ps[1]) == ps[2]);
        //out.println(next(ps, ps[2]) == ps[0]);

        //out.println(getScore(ps[0]) == 62);
        //out.println(getScore(ps[1]) == 65);
        //out.println(next(ps, ps[0]) == ps[1]);
        //out.println(getLoser(ps) == ps[0]);
        //out.println(removeLoser(ps, ps[0])[0].name + removeLoser(ps, ps[0])[1].name);
        //out.println(removeLoser(ps, ps[1])[0].name + removeLoser(ps, ps[1])[1].name);
        //out.println(removeLoser(ps, ps[2])[0].name + removeLoser(ps, ps[2])[1].name);
//        Player p1 = new Player("Bertil", 7, 0, 0, 0);
//        out.println(p1.name + p1.amount);

        exit(0);
    }


}

