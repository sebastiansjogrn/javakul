package ex2asteroids;


/*
    Class representing a Spaceship

 */
public class Spaceship extends ST{
    public static final double MAX_DX = 2;
    public static final double MAX_DY = 2;

    public Spaceship(double x, double y, double width, double height, double dx, double dy) {
        super(x, y, width, height, dx, dy);
    }

    public Spaceship(double x, double y, int width, int height) {
        this(x, y, width, height, 0, 0);
    }


    public boolean intersects(Asteroid asteroid) {
        boolean above = asteroid.getMaxY() < getY();
        boolean below = asteroid.getY() > getMaxY();
        boolean leftOf = asteroid.getMaxX() < getX();
        boolean rightOf = asteroid.getX() > getMaxX();
        return !(above || below || leftOf || rightOf);
    }

    public void stop() {
        setDx(0);
        setDy(0);
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
