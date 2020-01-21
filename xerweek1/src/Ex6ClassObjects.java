import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

/*
 *  Using class objects to represent heroes
 *  https://en.wikipedia.org/wiki/Gladiators_(1992_UK_TV_series)
 *
 * See:
 * - ex5classes
 */
public class Ex6ClassObjects {

    public static void main(String[] args) {
        new Ex6ClassObjects().program();
    }

    final Scanner sc = new Scanner(in);

    void program() {
        // -------- Input ------------
        Hero no1 = new Hero();
        no1.name = "Sportacuz";
        no1.strength = 9001;
        Hero no2 = new Hero();
        no2.name = "Mr Dank";
        no2.strength  = 42069;

        // ------- Output --------------
        if(no2.strength > no1.strength){
            out.println(no2.name + " is the WINNER!!");
        }
        else if(no2.strength < no1.strength){
            out.println(no1.name + " is the WINNER!!");
        }
        else{
            out.println("WOW, THEY'RE IDENTICAL!");
        }

    }

    // ------ The class to use  -----------
    // A class for objects that describes a Hero
    class Hero {
        String name;
        int strength;
    }


}
