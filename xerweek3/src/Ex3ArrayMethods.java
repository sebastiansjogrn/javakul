import java.util.Arrays;

import static java.lang.System.*;

/*
 *  Some harder array methods
 *
 * See
 * - lectweek2
 */
public class Ex3ArrayMethods {


    public static void main(String[] args) {
        new Ex3ArrayMethods().program();
    }

    void program() {
        int[] arr1 = {1, 2, 3, 4, 5, 6, 7, 8};

        // Rotate all elements in the arr k steps to the right (in a circular fashion)
        // Assume arr.length > 0. NOTE: Original array changed!
//        rotate(arr1, 3);
//        out.println(Arrays.toString(arr1).equals("[6, 7, 8, 1, 2, 3, 4, 5]"));
//        rotate(arr1, 0);
//        out.println(Arrays.toString(arr1).equals("[6, 7, 8, 1, 2, 3, 4, 5]"));
//        rotate(arr1, arr1.length);
//        out.println(Arrays.toString(arr1).equals("[6, 7, 8, 1, 2, 3, 4, 5]"));
//        rotate(new int[]{1}, 3);

        // Same as above but here we have a return value
//        int[] r = rotate2(new int[]{1, 2, 3, 4, 5}, 2);  // Return value!
//        out.println(Arrays.toString(r).equals("[4, 5, 1, 2, 3]"));
//        r = rotate2(new int[]{1, 2, 3, 4, 5}, 5);  // Return value!
//        out.println(Arrays.toString(r).equals("[1, 2, 3, 4, 5]"));


        int[] arr2 = {1, 2, 2, 3, 3};   // All sorted in increasing order
        int[] arr3 = {1, 2, 3, 4, 5};
        int[] arr4 = {1, 1, 1, 1, 1, 1};
        int[] arr5 = {1};

        // Remove all duplicates from arr2, ... (original unchanged, copy created)
        // NOTE: Assume arr is sorted in increasing order and > 0
        out.println(Arrays.toString(removeDupl(arr2)).equals("[1, 2, 3]"));
        out.println(Arrays.toString(arr2).equals("[1, 2, 2, 3, 3]"));   // arr2 unchanged!
        out.println(Arrays.toString(removeDupl(arr3)).equals("[1, 2, 3, 4, 5]"));
        out.println(Arrays.toString(removeDupl(arr4)).equals("[1]"));
        out.println(Arrays.toString(removeDupl(arr5)).equals("[1]"));


        arr1 = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        // Use fact that array is sorted to search efficiently
        //out.println(search(arr1, 1) == 0);
        //out.println(search(arr1, 3) == 2);
        //out.println(search(arr1, 8) == 7);

    }

    // -------------- Methods --------------------------

    void rotate(int[] list, int k) {
        int[] tmp = new int[list.length];
        for (int i = 0; i < list.length; i++) {
            tmp[i] = list[i];
        }
        for (int n = 0; n < list.length; n++) {
            list[(n + k) % (list.length)] = tmp[n];
        }
    }

    int[] rotate2(int[] list, int k) {
        int[] tmp = new int[list.length];
        for (int i = 0; i < list.length; i++) {
            tmp[i] = list[i];
        }
        for (int n = 0; n < list.length; n++) {
            list[(n + k) % (list.length)] = tmp[n];
        }
        return list;
    }

    int[] removeDupl(int[] arr) {
        int[] tmp = new int[arr.length];
        int n = 0;
        for (int i = 0; i < arr.length; i++) {
            if (!contains(tmp, arr[i])) {
                tmp[n] = arr[i];
                n++;
            }
        }
        int[] output = new int[n];
        for (int i = 0; i < n; i++){
            output[i] = tmp[i];
        }
        return output;
    }

    boolean contains(int[] arr, int i) {
        boolean output = false;
        for (int n = 0; n < arr.length; n++) {
            if (arr[n] == i) {
                output = true;
            }
        }
        return output;
    }


}

