package spaceinv.model;

public abstract class AbstractMove extends AbstractPosition implements Movable{

    private double dx;
    private double dy;

    public void move() {
        this.setX(this.getX()+this.getDx());
        this.setY(this.getY()+this.getDy());
        this.setDy(0);
    }

    public AbstractMove(double x, double y, double width, double height, double dx, double dy) {
        super(x, y, width, height);
        this.setDx(dx);
        this.setDy(dy);
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }
}
