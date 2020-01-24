
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
    final int startAmount = 2/*3*/;   // Money for a player. Select any
    final int mexico = 1000;     // A value greater than any other

    void program() {
        //test();            // <----------------- UNCOMMENT to test

        int pot = 0;         // What the winner will get
        Player[] players;    // The players (array of Player objects)
        Player current;      // Current player for round
        Player leader;       // Player starting the round
        int max = maxRolls;  // Max rolls for the round
        boolean ongoing = false;
        boolean round_over = false;

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
                } else {
                    out.println("You roll to  much, greedy boi.\n");
                    current = next(players, current);
                    ongoing = true;
                }
                // ---- Out --------
                roundMsg(current);

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
            //*******************************************************************Loser aren't removed, plz fix, finish game when one player left.
            if (round_over) {
                // --- Process -----
                pot++;
                ongoing = false;
                round_over = false;
                Player loser = getLoser(players);
                loser.amount--;
                if (loser.amount == 0) {
                    current = next(players, loser);
                    removeLoser(players, loser);
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
        for (int n = 0; n < nPlayers.length; n++) {
            if (players[n] != loser) {
                nPlayers[n] = players[n];
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
        // Ugly for now. If using a constructor this may
        // be cleaned up.
        Player[] players = new Player[2/*3*/];
        Player p1 = new Player();
        p1.name = "Markus"/*"Olle"*/;
        p1.amount = startAmount;
        Player p2 = new Player();
        p2.name = "Sebbestian"/*"Fia"*/;
        p2.amount = startAmount;
        Player p3 = new Player();
        p3.name = "Lisa";
        p3.amount = startAmount;
        players[0] = p1;
        players[1] = p2;
//        players[2] = p3;
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
        Player[] ps = {new Player(), new Player(), new Player()};
        ps[0].fstDice = 2;
        ps[0].secDice = 6;
        ps[1].fstDice = 6;
        ps[1].secDice = 5;
        ps[2].fstDice = 1;
        ps[2].secDice = 1;
        //out.println(next(ps, ps[0]) == ps[1]);
        //out.println(next(ps, ps[1]) == ps[2]);
        //out.println(next(ps, ps[2]) == ps[0]);

        //out.println(getScore(ps[0]) == 62);
        //out.println(getScore(ps[1]) == 65);
        //out.println(next(ps, ps[0]) == ps[1]);
        //out.println(getLoser(ps) == ps[0]);

        exit(0);
    }


}

