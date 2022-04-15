package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Universe {

    // Variables
    private List<Particle> particles;
    private Particle BigParticle;
    private Integer side;

    // Constructor
    public Universe(Integer side, int N) {
        this.particles = new ArrayList<>();
        BigParticle = new Particle(side/2.0, side/2.0, 0.7, 2.0, 0.0, 0.0);
        // La primera siempre es la BP
        particles.add(BigParticle);
        populate(N);
        this.side = side;
    }

    // Methods

    // Poblamos el universo y las particulas
    // no se superponen
    public void populate(int N) {
        System.out.println("entre al populate");
        Random rx = new Random();
        Random ry = new Random();
        Random rvx = new Random();
        Random rvy = new Random();
        Double vx = 0.0, vy = 0.0, mod = 0.0;
        int aux = 0;

        List<Pair<Double, Double>> mods = new ArrayList<>();
        System.out.println("Estoy por entrar al for");
        for (int i = 0; i < N; i++) {
            // Nos fijamos que el modulo de la velocidad sea
            // menor a 2.
            do {
                vx = rvx.nextDouble(2);
                vy = rvy.nextDouble(2);
                mod = Math.sqrt((vx*vx)+(vy*vy));
            } while (mod >= 2.0);
            mods.add(new Pair<>(vx, vy));
        }
        System.out.println("sali del for");
        System.out.println("estoy por entrar al while");
        while ( aux != N ) {
            Particle newPart = new Particle(rx.nextDouble(6), ry.nextDouble(6), 0.2, 0.9, mods.get(aux).first, mods.get(aux).second);
            if(newPart.timeToCollisionWall(6) <= 0) {
                continue;
            }
            boolean cont = false;
            for(Particle p2 : particles) {
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
        System.out.println("sali del while");
    }

    public void printUniverse (){
        for (Particle p:particles) {
            System.out.println(p);
        }
    }

}
