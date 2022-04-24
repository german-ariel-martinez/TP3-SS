package ar.edu.itba.ss;

import ar.edu.itba.ss.models.Universe;
import ar.edu.itba.ss.parsers.OutputParser;

public class App {

    private static final double TEMPERATURE = 298.15;

    public static void main(String[] args) {
        OutputParser.createCleanFile();
        OutputParser.createCleanPythonFile();

        Universe u = new Universe(6, 105, TEMPERATURE);
        u.simulate();
    }
}
