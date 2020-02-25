package ex1inheritance;

public class Van extends Vehicle{
   // TODO

    int maxCargo;

    public Van(Person owner, String id, int maxCargo) {
        this.maxCargo = maxCargo;
        this.id = id;
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Van{" +
                "maxCargo=" + maxCargo +
                toString(id, owner) +
                '}';
    }
}
