import java.util.Arrays;
import java.util.Random;

import static java.lang.StrictMath.round;
import static java.lang.System.*;

/*
 *  Methods with array params and/or return value. Implement methods.
 *
 *  See:
 *  ex2references
 *  ex3methods
 *  ex4algorithms
 *  - in particular A1BasicAlgs
 * -  and Fisher-Yates
 */
public class Ex1ArrayMethods {

    public static void main(String[] args) {
        new Ex1ArrayMethods().program();
    }

    final static Random rand = new Random();

    void program() {
        int[] arr = {1, 2, 2, 5, 3, 2, 4, 2, 7};  // Hard coded test data

        // Uncomment one at a time and implement

        // Count occurrences of some element in arr
        //out.println(count(arr, 2) == 4);      // There are four 2's
        //out.println(count(arr, 7) == 1);

        // Generate array with 100 elements with 25% distribution of -1's and 1's (remaining will be 0)
        arr = generateDistribution(100, 0.25, 0.25);
        out.println(count(arr, 1) == 25);
        out.println(count(arr, -1) == 25);
        out.println(count(arr, 0) == 50);

        // Generate array with 14 elements with 40% 1's and 30% -1's
        arr = generateDistribution(14, 0.4, 0.3);
        out.println(count(arr, -1) == 6);
        out.println(count(arr, 1) == 4);

        for (int i = 0; i < 10; i++) {
            // Random reordering of arr, have to check by inspecting output
            shuffle(arr);
            out.println(Arrays.toString(arr));  // Does it look random?
        }
    }


    // ---- Write methods below this ------------

    int count(int[] arr, int element) {
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] == element){
                count++;
            }
        }
        return count;
    }

    int[] generateDistribution(int amount, double prc1, double prc2){
        int [] nArr = new int [amount];
        int am1 = (int)Math.round(amount*prc1);
        int am2 = (int)Math.round(amount*prc2);
        for(int i=0;i<am1;i++){
            nArr[i] = -1;
        }
        for(int i=am1;i<am1+am2;i++){
            nArr[i] = 1;
        }
        return nArr;
    }

    int[] shuffle(int[] arr){
        for(int i = arr.length-1;i>=0;i--){
            int j = rand.nextInt(i+1);
            int tmp = arr[i];
            arr[i]=arr[j];
            arr[j] = tmp;
        }
        return arr;
    }

}
