package spaceinv.model;

public abstract class AbstractSpaceship extends AbstractMove implements Shootable{

    public AbstractSpaceship(double x, double y, double width, double height, double dx, double dy) {
        super(x, y, width, height, dx, dy);
    }

    @Override
    public Projectile fire(){
        return Shooter.fire(this, -SI.PROJECTILE_SPEED);
    }
}
