package spaceinv.model;

import static spaceinv.model.SI.PROJECTILE_HEIGHT;
import static spaceinv.model.SI.PROJECTILE_WIDTH;

/*
       A projectile fired from the Gun or dropped by a spaceship

       This class should later be refactored (and inherit most of what it needs)
 */
public class Projectile extends AbstractMove {

    public Projectile(double x, double y, double width, double height, double dy) {
        super(x, y, width, height, 0, dy);
    }

    public Projectile(double dy) {
        this(0, 0, PROJECTILE_WIDTH, PROJECTILE_HEIGHT, dy);
        // Below: HINT
        //super(0, 0, PROJECTILE_WIDTH, PROJECTILE_HEIGHT, 0, dy);
    }

    @Override
    public void move() {
        setY(getY() - getDy());
        setDy(1.05 * getDy());
    }
}
