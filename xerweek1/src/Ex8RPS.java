import java.util.Random;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

/*
 * The Rock, paper, scissor game.
 * See https://en.wikipedia.org/wiki/Rock%E2%80%93paper%E2%80%93scissors
 *
 * This is exercising smallest step programming (no methods needed)
 *
 * Rules:
 *
 *       -----------  Beats -------------
 *       |                              |
 *       V                              |
 *      Rock (1) --> Scissors (2) --> Paper (3)
 *
 */
public class Ex8RPS {

    public static void main(String[] args) {
        new Ex8RPS().program();
    }

    final Random rand = new Random();
    final Scanner sc = new Scanner(in);

    void program() {

        int maxRounds = 5;
        int human;          // Outcome for player
        int computer;       // Outcome for computer
        int result;         // Result for this round
        int round = 0;      // Number of rounds
        int total = 0;      // Final result after all rounds

        // All code here ... (no method calls)

        out.println("Welcome to Rock, Paper and Scissors");

        while (round < maxRounds) {   // Game loop
            // -------- Input --------------
            out.println("Do you choose rock(1), paypal(2) or scissors(3)?");
            human = sc.nextInt();
            computer = rand.nextInt(3) +1;
            out.println(computer);

            // ----- Process -----------------
            if(human%3 == (computer-1)){
                total += 1;
                out.println("You beat!");
            }else if(computer%3 == (human-1)){
                total -= 1;
                out.println("You no beat!");
            }else{
                out.println("    ______\n" +
                        "   /(    )\\\n" +
                        "   \\ \\  / /\n" +
                        "    \\/[]\\/\n" +
                        "      /\\\n" +
                        "     |  |\n" +
                        "     |  |\n" +
                        "     |  |\n" +
                        "     |  |\n" +
                        "     |  |\n" +
                        "     \\  /   \n" +
                        "      \\/");
            }

            // ---------- Output --------------


            round++;
        }

        out.println("Game over! ");
        if (total == 0) {
            out.println("Draw");
        } else if (total > 0) {
            out.println("Human won.");
        } else {
            out.println("Computer won.");
        }
    }
}
