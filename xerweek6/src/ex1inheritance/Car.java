package ex1inheritance;

import javafx.scene.paint.Color;

public class Car extends Vehicle {
  // TODO

    int topSpeed;

    public Car(Person owner, String id, int topSpeed) {
        this.topSpeed = topSpeed;
        this.id = id;
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Car{" +
                "topSpeed=" + topSpeed +
                toString(id, owner) +
                '}';
    }
}
