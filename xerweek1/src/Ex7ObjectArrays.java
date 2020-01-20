import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

/*
 *  Using class objects to represent heroes
 *  https://en.wikipedia.org/wiki/Gladiators_(1992_UK_TV_series)
 *
 * See:
 * - ex5classes
 * - C3ObjectArray
 */
public class Ex7ObjectArrays {

    public static void main(String[] args) {
        new Ex7ObjectArrays().program();
    }

    final Scanner sc = new Scanner(in);

    void program() {
        // Create (new) and initialize hero objects using the constructor
        Hero[] heroes = {new Hero("Attila", 45), new Hero("Amber", 41),
                new Hero("Plexus", 52), new Hero("Elektra", 50),
                new Hero("Furia", 38), new Hero("Tor", 51)};

       // TODO


       // out.println("Strongest hero is " + heroes[strongestIndex].name);
    }

    // ------ The class to use  -----------
    // A class for objects that describes a Hero
    class Hero {
        String name;
        int strength;
        // Below is a constructor (a special method). Used to initialize object
        // Called automagically just after creation (i.e. after new-operator
        // created the object, more later ...)
        public Hero(String n, int s) {
            name = n;
            strength = s;
        }
    }
}
