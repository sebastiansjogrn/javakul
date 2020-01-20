import java.util.Arrays;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

/*
 * Program to exercise arrays
 *
 * See:
 * - ex3arraysfor
 */
public class Ex4Arrays {

    public static void main(String[] args) {
        new Ex4Arrays().program();
    }

    final Scanner sc = new Scanner(in);

    void program() {
        int[] arr = {0, 0, 0, 0, 0};  // or new int[5]

        out.print("Input 5 integers (space between, then enter) > ");

        // Write your code here


        for(int n= 0; n<5;n++){
            int x = sc.nextInt();
            arr[n]=x;
        }
        out.println("You wrote " + Arrays.toString(arr));
    }
}
