import java.util.Scanner;

import static java.lang.System.*;;

/*
 * Program to calculate sum and average for non-negative integers
 *
 * See:
 * - ex3arraysfor
 * - IW5LoopAndAHalf in particular.
 */
public class Ex3SumAvg {

    public static void main(String[] args) {
        new Ex3SumAvg().program();
    }

    final Scanner sc = new Scanner(in);

    void program() {
        out.println("Number of numbers plz");
        int x = sc.nextInt();
        int[] arr = new int[x];
        for (int n = 0; n < x; n++) {
            out.println("Number plz");
            int y = sc.nextInt();
            arr[n] = y;
        }


        int sum = 0;
        double l = arr.length;
        for (int n = 0; n < l; n++) {
            sum += arr[n];
        }
        double avg = sum / l;

        out.println("Sum = " + sum + "\nAverage = " + avg);
    }

}
