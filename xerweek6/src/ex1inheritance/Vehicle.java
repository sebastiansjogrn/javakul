package ex1inheritance;

import javafx.scene.paint.Color;

import java.util.Random;

public abstract class Vehicle {

   // TODO

    String id;
    Person owner;

    public String toString(String id, Person owner) {
        return ", owner=" + owner +
                ", id=" + id +
                '}';
    }
}
