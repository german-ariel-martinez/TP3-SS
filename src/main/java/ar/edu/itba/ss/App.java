package ar.edu.itba.ss;

import ar.edu.itba.ss.models.Universe;
import ar.edu.itba.ss.parsers.OutputParser;

public class App {

    private static final String OUTPUT_FILE = "outputTP3.xyz";
    private static final String VELOCITIES_FILE = "outPutVelocities.csv";
    private static final String COLLISION_TIMES_FILE = "outputCollisionTimes";
    private static final double TEMPERATURE = 298.15;

    public static void main(String[] args) {
        OutputParser.setFileName(OUTPUT_FILE);
        OutputParser.createCleanFile();
        OutputParser.createCleanPythonFile("outPutVelocities.csv");
        OutputParser.createCleanPythonFile("outputCollisionTimes");
        Universe u = new Universe(6, 100, TEMPERATURE);
        u.simulate();
    }
}
