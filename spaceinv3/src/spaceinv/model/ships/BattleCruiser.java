package spaceinv.model.ships;

import spaceinv.model.AbstractMove;
import spaceinv.model.AbstractSpaceship;
import spaceinv.model.SI;

/*
 *   Type of space ship
 */
public class BattleCruiser extends AbstractSpaceship {

    // Default value
    public static final int BATTLE_CRUISER_POINTS = 100;

    public BattleCruiser(double x, double y){
        super(x, y, SI.SHIP_WIDTH, SI.SHIP_HEIGHT, SI.SHIP_MAX_DX, 0);
    }
}
