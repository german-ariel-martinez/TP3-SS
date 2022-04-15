package models;

public class Particle {

    // Variables: Positions
    Double x;
    Double y;
    // Variables: Properties
    Double radius;
    Double mass;
    Double vx;
    Double vy;

    // Constructor
    public Particle(Double x, Double y, Double r, Double m, Double vx, Double vy) {
        this.x = x;
        this.y = y;
        this.radius = r;
        this.mass = m;
        this.vx = vx;
        this.vy = vy;
    }

    // Methods

    // Mueve la particula la distancia que recorreria
    // en t instantes de tiempo.
    public void updatePosition (Double t) {

        // Actualizamos el eje x.
        x = x + vx * t;
        // Actualizamos el eje y.
        y = y + vy * t;

    }

    // Nos devuelve el tiempo que falta para
    // que choquen contra una pared.
    public Double timeToCollisionWall(int side) {
        Double tx = 0.0, ty = 0.0;
        // Colision horizontal
        if(vx > 0.0) {
            tx = (side - radius - x)/vx;
        } else {
            tx = (radius - x)/vx;
        }
        // Colision vertical
        if(vy > 0.0) {
            ty = (radius - y)/vy;
        } else {
            ty = (side + radius - y)/vy;
        }
        return Math.min(tx, ty);
    }

    // Le pasamos una particula y nos dice
    // cuanto falta para que choquen
    public Double timeToCollisionParticle(Particle p) {

        // Variables
        Double sigma = radius + p.radius;
        Pair<Double, Double> Dr = new Pair<>(p.x-x, p.y-y);
        Pair<Double, Double> Dv = new Pair<>(p.vx-vx, p.vy-vy);
        Double d = Math.pow((Dv.first*Dr.first)+(Dv.second*Dr.second), 2) -
                   (Math.pow(Dv.first, 2)+Math.pow(Dv.second, 2)) * ((Math.pow(Dr.first, 2)+Math.pow(Dv.second, 2)) - Math.pow(sigma, 2));

        // En cualquier caso que nos de infinito
        // devolvemos -1.
        if(((Dv.first*Dr.first)+(Dv.second*Dr.second))>=0) {
            return -1.0;
        } else if(d < 0.0) {
            return -1.0;
        } else {
            return - (((Dv.first*Dr.first)+(Dv.second*Dr.second) + Math.sqrt(d))/(Math.pow(Dv.first, 2)+Math.pow(Dv.second, 2)));
        }

    }

    // Que hacemos si chocamos contra una pared
    public void collisionWall(boolean horiz) {

        if(horiz) {
            vy = -vy;
        } else {
            vx = -vx;
        }

    }

    // Que hacemos si choca contra otra particula
    public void collisionParticle(Particle p) {

        // Variables
        Double sigma = radius + p.radius;
        Pair<Double, Double> Dr = new Pair<>(p.x-x, p.y-y);
        Pair<Double, Double> Dv = new Pair<>(p.vx-vx, p.vy-vy);
        Double J = (2 * mass * p.mass * ((Dv.first*Dr.first)+(Dv.second*Dr.second))) / (sigma * (mass + p.mass));
        Double Jx = (J * (p.x-x))/sigma;
        Double Jy = (J * (p.y-y))/sigma;

        // Actualizamos las velocidades
        vx = vx + Jx/mass;
        p.vx = p.vx - Jx/p.mass;
        vy = vy + Jy/mass;
        p.vy = p.vy - Jy/p.mass;

    }

    // Getters n setters
    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public Double getMass() {
        return mass;
    }

    public void setMass(Double mass) {
        this.mass = mass;
    }

    public Double getVx() {
        return vx;
    }

    public void setVx(Double vx) {
        this.vx = vx;
    }

    public Double getVy() {
        return vy;
    }

    public void setVy(Double vy) {
        this.vy = vy;
    }

    // toString
    @Override
    public String toString() {
        return "Particle{" +
                "x=" + x +
                ", y=" + y +
                ", vx=" + vx +
                ", vy=" + vy +
                '}';
    }
}
