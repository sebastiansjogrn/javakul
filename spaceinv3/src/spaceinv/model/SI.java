package spaceinv.model;


import javafx.geometry.Pos;
import spaceinv.event.EventBus;
import spaceinv.event.ModelEvent;
import spaceinv.model.ships.BattleCruiser;
import spaceinv.model.ships.Bomber;
import spaceinv.model.ships.Frigate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.System.*;

/*
 *  SI (Space Invader) class representing the overall
 *  data and logic of the game
 *  (nothing about the look/rendering here)
 */
public class SI {

    // Default values (not to use directly). Make program adaptable
    // by letting other programmers set values if they wish.
    // If not, set default values (as a service)
    public static final int DIFFICULTY = 32;
    public static final int GAME_WIDTH = 500;
    public static final int GAME_HEIGHT = 500;
    public static final int LEFT_LIMIT = 50;
    public static final int RIGHT_LIMIT = 450;
    public static final int SHIP_WIDTH = 20;
    public static final int SHIP_HEIGHT = 20;
    public static final int SHIP_MAX_DX = 3;
    public static final int SHIP_MAX_DY = 20;
    public static final int GUN_WIDTH = 20;
    public static final int GUN_HEIGHT = 20;
    public static final double GUN_MAX_DX = 3;
    public static final double PROJECTILE_WIDTH = 5;
    public static final double PROJECTILE_HEIGHT = 5;
    public static final int GROUND_HEIGHT = 20;
    public static final int OUTER_SPACE_HEIGHT = 10;
    public static final int PROJECTILE_SPEED = 1;

    public static final long ONE_SEC = 1_000_000_000;
    public static final long HALF_SEC = 500_000_000;
    public static final long TENTH_SEC = 100_000_000;
    public static final long ACTUAL_HALF_SEC = 50;

    private static final Random rand = new Random();

    // TODO More references here

    //Music!!
    //Acceleration for gun


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
    private int time = 0;


    // ------ Game loop (called by timer) -----------------
    public void update(long now) {
        time++;

        if (ships.size() == 0) {
            EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.HAS_WON));
        }

        /*
             Movement
         */

        List<Positionable> ps = getPositionables();
        List<Movable> ms = getMovables(ps);

        gunCollision();

        for (Movable m : ms) {
            m.move();
        }

        ships.get(ships.size() - 1 - (shipToMove % ships.size())).move();
        shipToMove++;


        /*
            Ships fire
         */
        if (time % ACTUAL_HALF_SEC == 0) {
            shipBombs.add(ships.get(rand.nextInt(ships.size())).fire());
        }


        /*
             Collisions
         */
        boolean collision = false;
        for (AbstractSpaceship s : ships) {
            collision = collision || shipHitLeftLimit(s) || shipHitRightLimit(s);
        }

        if (collision) {
            for (AbstractSpaceship s : ships) {
                s.setX(s.getX() - SHIP_MAX_DX * sgn(s.getDx()));
                s.setDx(-s.getDx());
                s.setDy(SHIP_MAX_DY);
            }
        }

        for (AbstractSpaceship s : ships) {
            if (hitBorder(s)) {
                EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.BOMB_HIT_GUN, gun));
            }
        }

        if (gunProjectile != null && ships.size() > 0 && shipBombs.size() > 0) {
            List<Positionable> enemies = new ArrayList<>();
            enemies.addAll(ships);
            enemies.addAll(shipBombs);
            projectilesCollision(enemies, gunProjectile);
        }

        if (shipBombs.size() > 0) {
            List<Positionable> bombs = new ArrayList<>(shipBombs);
            projectilesCollision(bombs, gun);
        }
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

    private void gunCollision() {
        if (0 < gun.getDx() && shipHitRightLimit(gun)) {
            gun.setDx(0);
        } else if (gun.getDx() < 0 && shipHitLeftLimit(gun)) {
            gun.setDx(0);
        }
    }

    public void moveGun(double dx, double dy) {
        gun.setDx(/*gun.getDx()+*/dx);
        gun.setDy(/*gun.getDy()+*/dy);
    }

    // TODO More methods called by GUI

    void projectilesCollision(List<Positionable> ps, Positionable pos) {
        for (Positionable p : ps) {
            if (collision(p, pos)) {
                if (ships.contains(p)) {
                    ships.remove(p);
                    if (p.getClass() == BattleCruiser.class) {
                        points += BattleCruiser.BATTLE_CRUISER_POINTS;
                    } else if (p.getClass() == Frigate.class) {
                        points += Frigate.FRIGATE_POINTS;
                    } else {
                        points += Bomber.BOMBER_POINTS;
                    }
                } else {
                    shipBombs.remove(p);
                }
                EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.GUN_HIT_SHIP, p));

                if (pos instanceof Projectile) {
                    gunProjectile = null;
                    break;
                } else {
                    EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.BOMB_HIT_GUN, pos));
                }
            } else if (hitBorder(p)) {
                shipBombs.remove(p);
                EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.BOMB_HIT_GROUND, p));
            }
        }

    }

    boolean collision(Positionable p1, Positionable p2) {
        if (overlapX(p1, p2) && overlapY(p1, p2)) {
            return true;
        } else {
            return false;
        }
    }

    boolean hitBorder(Positionable pos) {
        double y = pos.getY();
        if (y < OUTER_SPACE_HEIGHT || GAME_HEIGHT - GROUND_HEIGHT < y) {
            return true;
        }

        return false;
    }

    boolean overlapY(Positionable p1, Positionable p2) {
        for (int i = 0; i <= p1.getHeight(); i++) {
            if (p2.getY() <= p1.getY() + i && p1.getY() + i <= p2.getY() + p2.getHeight()) {
                return true;
            }
        }
        return false;
    }

    boolean overlapX(Positionable p1, Positionable p2) {
        for (int i = 0; i <= p1.getWidth(); i++) {
            if (p2.getX() <= p1.getX() + i && p1.getX() + i <= p2.getX() + p2.getWidth()) {
                return true;
            }
        }
        return false;
    }

    public List<Positionable> getPositionables() {
        List<Positionable> ps = new ArrayList<>();
        ps.add(gun);
        if (gunProjectile != null) {
            ps.add(gunProjectile);
        }
        ps.addAll(ships);
        ps.addAll(shipBombs);
        return ps;
    }

    List<Movable> getMovables(List<Positionable> ps) {
        List<Movable> ms = new ArrayList<>();
        for (Positionable p : ps) {
            if (p instanceof Movable) {
                if (!(p instanceof AbstractSpaceship))
                    ms.add((Movable) p);
            }
        }
        return ms;
    }


    public int getPoints() {
        return points;
    }

    int sgn(double d) {
        if (d < 0) {
            return -1;
        } else if (d > 0) {
            return 1;
        }
        return 0;
    }
}
