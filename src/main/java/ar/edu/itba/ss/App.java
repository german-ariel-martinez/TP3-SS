package ar.edu.itba.ss;

import ar.edu.itba.ss.models.Universe;
import ar.edu.itba.ss.parsers.OutputParser;

public class App {

    private static final String OUTPUT_FILE = "outputTP3.xyz";
    private static final double TEMPERATURE = 298.15;

    public static void main(String[] args) {
        OutputParser.setFileName(OUTPUT_FILE);
        OutputParser.createCleanFile();
        Universe u = new Universe(6, 100, TEMPERATURE);
        u.simulate();
    }
}
