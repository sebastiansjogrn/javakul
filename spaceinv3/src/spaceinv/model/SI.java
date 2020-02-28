package spaceinv.model;


import javafx.geometry.Pos;
import spaceinv.event.EventBus;
import spaceinv.event.ModelEvent;
import spaceinv.model.ships.BattleCruiser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 *  SI (Space Invader) class representing the overall
 *  data and logic of the game
 *  (nothing about the look/rendering here)
 */
public class SI {

    // Default values (not to use directly). Make program adaptable
    // by letting other programmers set values if they wish.
    // If not, set default values (as a service)
    public static final int GAME_WIDTH = 500;
    public static final int GAME_HEIGHT = 500;
    public static final int LEFT_LIMIT = 50;
    public static final int RIGHT_LIMIT = 450;
    public static final int SHIP_WIDTH = 20;
    public static final int SHIP_HEIGHT = 20;
    public static final int SHIP_MAX_DX = 3;
    public static final int SHIP_MAX_DY = 0;
    public static final int GUN_WIDTH = 20;
    public static final int GUN_HEIGHT = 20;
    public static final double GUN_MAX_DX = 3;
    public static final double PROJECTILE_WIDTH = 5;
    public static final double PROJECTILE_HEIGHT = 5;
    public static final int GROUND_HEIGHT = 20;
    public static final int OUTER_SPACE_HEIGHT = 10;

    public static final long ONE_SEC = 1_000_000_000;
    public static final long HALF_SEC = 500_000_000;
    public static final long TENTH_SEC = 100_000_000;

    private static final Random rand = new Random();

    // TODO More references here
    private final Gun gun;
    private List<AbstractSpaceship> ships;

    private final List<Projectile> shipBombs = new ArrayList<>();
    private Projectile gunProjectile;
    private int points;

    // TODO Constructor here
    public SI(Gun gun, List<AbstractSpaceship> ships) {
        this.gun = gun;
        this.ships = ships;

    }


    // Timing. All timing handled here!
    private long lastTimeForMove;
    private long lastTimeForFire;
    private int shipToMove = 0;

    // ------ Game loop (called by timer) -----------------

    public void update(long now) {

        /*if( ships.size() == 0){
            EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.HAS_WON));
        }*/

        /*
             Movement
         */

        List<Positionable> ps = getPositionables();
        List<Movable> ms = getMovables(ps);

        for (Movable m : ms) {
            m.move();
        }

        /*
            Ships fire
         */


        /*
             Collisions
         */
        boolean collision = false;
        for(AbstractSpaceship s: ships){
            collision = collision || shipHitLeftLimit(s) || shipHitRightLimit(s);
        }
                                                                                //Make the ships move yo!
    }

    private boolean shipHitRightLimit(AbstractMove m) {
        // TODO
        if (m.getX() >= RIGHT_LIMIT) {
            return true;
        }
        return false;
    }

    private boolean shipHitLeftLimit(AbstractMove m) {
        // TODO
        if (m.getX() <= LEFT_LIMIT) {
            return true;
        }
        return false;
    }


    // ---------- Interaction with GUI  -------------------------

    public void fireGun() {
        // TODO
        if (gunProjectile == null) {
            gunProjectile = gun.fire();
        } else if (gunProjectile.getY() < OUTER_SPACE_HEIGHT) {
            gunProjectile = gun.fire();
        }
    }

    // TODO More methods called by GUI

    public List<Positionable> getPositionables() {
        List<Positionable> ps = new ArrayList<>();
        ps.add(gun);
        if (gunProjectile != null) {
            ps.add(gunProjectile);
        }
        ps.addAll(ships);
        return ps;
    }

    List<Movable> getMovables(List<Positionable> ps) {
        List<Movable> ms = new ArrayList<>();
        for (Positionable p : ps) {
            if (p instanceof Movable) {
                ms.add((Movable) p);
            }
        }
        return ms;
    }



    public int getPoints() {
        return points;
    }

/*    public Gun getGun() {
        return gun;
    }

    public void accelerateGun(double d) {
        if (Math.abs(gun.getDx()) != GUN_MAX_DX+d) {
            gun.setDx(gun.getDx() + d);
        }
    }*/

    public void moveGun(double dx, double dy) {
        gun.setDx(dx);
        gun.setDy(dy);
    }
}
