package spaceinv.model;


import static spaceinv.model.SI.*;

/*
 *    A Gun for the game
 *    Can only fire one projectile at the time
 */
public class Gun implements Positionable, Shootable{

    double x;
    double y;
    double width;
    double heigth;
    double dx;

    public Gun(double x, double y){
        this(x, y, GUN_WIDTH, GUN_HEIGHT);
    }

    public Gun(double x, double y, double width, double heigth) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = heigth;
    }

    @Override
    public Projectile fire() {
        return Shooter.fire(this, 1);
    }

    public void move() {
        x += dx;
    }

    @Override
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return heigth;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }
}
