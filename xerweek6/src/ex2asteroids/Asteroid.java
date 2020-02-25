package ex2asteroids;

/*
    Class representing a single Asteroid

 */
public class Asteroid extends ST {

    public Asteroid(double x, double y, double width, double height, double dx, double dy) {
        super(x, y, width, height, dx, dy);
    }

    public void move() {
        setX(getX()+getDx());
        setY(getY()+getDy());
    }

    public double getMaxX() {
        return getX() + getWidth();
    }

    public double getMaxY() {
        return getY() + getHeight();
    }
}