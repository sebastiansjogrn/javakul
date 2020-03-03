package spaceinv.model.ships;

import spaceinv.model.AbstractSpaceship;
import spaceinv.model.SI;

/*
 *   Type of space ship
 */
public class Bomber extends AbstractSpaceship {

    // Default value
    public static final int BOMBER_POINTS = 200;

    public Bomber(double x, double y){
        super(x, y, SI.SHIP_WIDTH, SI.SHIP_HEIGHT, SI.SHIP_MAX_DX, 0);
    }

}
