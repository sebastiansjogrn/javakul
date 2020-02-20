package spaceinv.model;


import static spaceinv.model.SI.*;

/*
 *    A Gun for the game
 *    Can only fire one projectile at the time
 */
public class Gun  {

    boolean firing;
    int x;
    int y;
    int dx;
    int dy;

    public Gun() {
        this.firing = false;
        this.x = 0;
        this.y = 0;
        this.dx = 0;
        this.dy = 0;
    }
}
