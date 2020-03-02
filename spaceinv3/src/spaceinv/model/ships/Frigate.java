package spaceinv.model.ships;

import spaceinv.model.AbstractSpaceship;
import spaceinv.model.SI;

/*
 *   Type of space ship
 */
public class Frigate extends AbstractSpaceship {

    // Default value
    public static final int FRIGATE_POINTS = 300;

    public Frigate(double x, double y){
        super(x, y, SI.SHIP_WIDTH, SI.SHIP_HEIGHT, SI.SHIP_MAX_DX, 0);
    }

}
