package ar.edu.itba.ss.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Universe {

    // Variables
    private final List<Particle> particles;
    private final Particle BigParticle;
    private final Integer side;

    // Constructor
    public Universe(Integer side, int N) {
        this.side = side;
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
        double vx, vy, mod;
        int aux = 0;

        List<Pair<Double, Double>> mods = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            // Nos fijamos que el modulo de la velocidad sea menor a 2.
            // TODO hay que dejar que las velocidades puedan ser negativas
            do {
                vx = rnd.nextDouble() * 2;
                vy = rnd.nextDouble() * 2;
                mod = Math.sqrt((vx*vx)+(vy*vy));
            } while (mod >= 2.0);
            mods.add(new Pair<>(vx, vy));
        }

        while ( aux != N ) {
            double randomX = rnd.nextDouble() * side;
            double randomY = rnd.nextDouble() * side;
            Particle newPart = new Particle(randomX, randomY, 0.2, 0.9, mods.get(aux).first, mods.get(aux).second);
            // TODO revisar la funcion timeToCollisionWall
            if(newPart.timeToCollisionWall(side) <= 0)
                continue;

            boolean cont = false;
            for(Particle p2 : particles) {
                // TODO revisar la funcion timeToCollisionParticle
                double t = newPart.timeToCollisionParticle(p2);
                if(t <= 0) {
                    cont = true;
                    break;
                }
            }
            if(cont)
                continue;
            particles.add(newPart);
            aux++;
        }
    }

    public void printUniverse (){
        for (Particle p:particles) {
            System.out.println(p);
        }
    }

}
