package ar.edu.itba.ss.models;

import ar.edu.itba.ss.parsers.OutputParser;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Universe {

    // Variables
    private final List<Particle> particles;
    private final Particle BigParticle;
    private final Integer side;
    private final Double temp;

    // Constructor
    public Universe(Integer side, int N, Double temp) {
        this.side = side;
        this.temp = temp;
        particles = new ArrayList<>();
        BigParticle = new Particle(side/2.0, side/2.0, 0.7, 2.0, 0.0, 0.0);
        // La primera siempre es la BP
        particles.add(BigParticle);
        populate(N);
    }

    // Methods

    // Poblamos el universo y las particulas
    // no se superponen
    public void populate(int N) {
        Random rnd = new Random();
        double vx, vy;
        double v, angulo;
        int aux = 0;

        List<Pair<Double, Double>> mods = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            // Nos fijamos que el modulo de la velocidad sea menor a 2.
            v = rnd.nextDouble() * 2;
            angulo = rnd.nextDouble() * 2 * Math.PI;
            vx = Math.cos(angulo) * v;
            vy = Math.sin(angulo) * v;
            mods.add(new Pair<>(vx, vy));
        }

        while ( aux != N ) {
            double randomX = rnd.nextDouble() * side;
            double randomY = rnd.nextDouble() * side;
//            Double velX = Math.sqrt(3*8.314*temp/0.9) * ((rnd.nextDouble() > 0.5) ? -1 : 1);
//            Double velY = Math.sqrt(3*8.314*temp/0.9) * ((rnd.nextDouble() > 0.5) ? -1 : 1);
//            if(Math.sqrt((velX*velX)+(velY*velY) >= 2) {
//                throw InvalidParameterException
//            }

            Particle newPart = new Particle(randomX, randomY, 0.2, 0.9, mods.get(aux).first, mods.get(aux).second);

            if(randomY-0.2 <= 0 || randomY+0.2 >= 6 || randomX-0.2 <= 0 || randomX+0.2 >= 6)
                continue;

            boolean cont = false;
            for(Particle p2 : particles) {
                if(Math.pow(randomX-p2.getX(),2) + Math.pow(randomY-p2.getY(),2) <= Math.pow(0.2 + p2.getRadius(),2)) {
                    cont = true;
                    break;
                }
            }
            if(cont)
                continue;
            particles.add(newPart);
            aux++;
        }

        OutputParser.writeUniverse(particles, 0);
        OutputParser.writePythonCSV(particles, 0,0);
        //TODO: revisar
//        try{
//            OutputParser.writeVelocityPythonCSV(particles);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    // Comienza la simulacion.
    public void simulate () {
        List<Double> collisionTimes = new ArrayList<>();
        long collisions = 0;
        long start = System.currentTimeMillis();
//        while (System.currentTimeMillis() - start < 2000) {
        while (collisions < 18000 && bigParticleInBoundaries()) {
            Particle toCollide1 = null, toCollide2 = null;
            // PASO 2: Calcular el tiempo hasta el primer choque
            double tMin = 999999999;
            for (int i = 0; i < particles.size(); i++) {
                Particle p1 = particles.get(i);
                double tAux = p1.timeToCollisionWall(side);
                if (tAux > 0) {
                    if (tAux < tMin) {
                        tMin = tAux;
                        toCollide1 = p1;
                        toCollide2 = null;
                    }
                }

                for (int j = i + 1; j < particles.size(); j++) {
                    Particle p2 = particles.get(j);
                    tAux = p1.timeToCollisionParticle(p2);
                    if (tAux > 0) {
                        if (tAux < tMin) {
                            tMin = tAux;
                            toCollide1 = p1;
                            toCollide2 = p2;
                        }
                    }
                }
            }

            // Nos guardamos el tiempo minimo para promediarlo
            collisionTimes.add(tMin);

            // PASO 3: evolucionar el universo hasta tMin
            for (Particle p : particles) {
                p.updatePosition(tMin);
            }

            // PASO 4: se guarda el estado del sistema (posiciones y velocidades) en tMin

            // PASO 5: hacemos el choque
            if (toCollide2 == null) {
                collisions++;
                // Chocamos con una wall
                boolean horizontal;
                if (toCollide1.getX() == 0.2 || toCollide1.getX() == 5.8 || toCollide1.getX() == 0.7 || toCollide1.getX() == 5.3) {
                    // Chocamos con la wall izquierda o derecha
                    horizontal = false;
                } else if (toCollide1.getY() == 0.2 || toCollide1.getY() == 5.8 || toCollide1.getY() == 0.7 || toCollide1.getY() == 5.3) {
                    // Chocamos con la wall superior o inferior
                    horizontal = true;
                } else {
                    throw new RuntimeException("Revisemos que esta pasando.\n X = " + toCollide1.getX() + " Y = " + toCollide1.getY());
                }
                toCollide1.collisionWall(horizontal);
            } else {
                collisions++;
                // Chocamos con otra particula
                toCollide1.collisionParticle(toCollide2);
            }

            OutputParser.writeUniverse(particles, 0);
            OutputParser.writePythonCSV(particles, System.currentTimeMillis()-start, tMin);
        }
        Double meanTime = collisionTimes.stream().reduce(0.0, Double::sum)/collisionTimes.size();
//        try{
//            OutputParser.writeCollisionTimesPythonCSV(collisionTimes);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        System.out.println("El tiempo promedio de choques es => "+meanTime);
        long end = System.currentTimeMillis();
        System.out.println(collisions);
        System.out.println(end-start);
        System.out.println("Frecuencia de colisiones => " + ((double)collisions/(end-start)));
    }

    private Boolean bigParticleInBoundaries(){
        double r = BigParticle.getRadius();
        double x = BigParticle.getX();
        double y = BigParticle.getY();
        if (x-r <= 0 || x+r >= 6 || y-r <= 0 || y+r >= 6)
            return false;
        return true;
    }

}
