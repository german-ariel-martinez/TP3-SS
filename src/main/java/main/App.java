package main;

import models.Particle;
import models.Universe;

public class App {
    public static void main(String[] args) {
        Universe u = new Universe(6, 10);
        u.printUniverse();
    }
}
