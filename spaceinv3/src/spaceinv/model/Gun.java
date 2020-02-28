package spaceinv.model;


import static spaceinv.model.SI.*;

/*
 *    A Gun for the game
 *    Can only fire one projectile at the time
 */
public class Gun extends AbstractMove implements Shootable {

    public Gun(double x, double y) {
        this(x, y, GUN_WIDTH, GUN_HEIGHT);
    }

    public Gun(double x, double y, double width, double height) {
        super(x, y, width, height, 0, 0);
    }

    @Override
    public Projectile fire() {
        return Shooter.fire(this, 1);
    }

    @Override
    public void move() {
        this.setX(this.getX()+this.getDx());
        this.setY(this.getY()+this.getDy());
    }
}
