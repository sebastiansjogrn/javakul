import java.util.Arrays;
import java.util.Random;

import static java.lang.System.out;

/*
 * Implement generic versions of shuffle and sort
 *
 *  See:
 * - lectweek4/ex2methods/M2GenericMethod
 */
public class Ex7GenericMethods {

    public static void main(String[] args) {
        new Ex7GenericMethods().program();
    }

    final Random rand = new Random();

    void program() {


        // Working with wrapper types, generic methods only work with reference types
        Integer[] is = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        String origIs = Arrays.toString(is);
        String[] ss = {"a", "b", "c", "d", "e"};
        String origSS = Arrays.toString(ss);

        // diffIndex works for all arrays reference types
        //out.println(diffIndex(is, is) == -1);
        //out.println(diffIndex(ss, ss) == -1);

        // shuffle should be a generic method, so we can use it for Integers ...
        //shuffle(is);
        out.println(!Arrays.toString(is).equals(origIs));  // May be false, but unlikely
        // ... and here for String.
        //shuffle(ss);
        out.println(!Arrays.toString(ss).equals(origSS));

        // sort should also be generic
        //sort(is);
        out.println(Arrays.toString(is).equals(origIs));
        //sort(ss);
        out.println(Arrays.toString(ss).equals(origSS));

    }

    // ------- Methods -------------------------


}
