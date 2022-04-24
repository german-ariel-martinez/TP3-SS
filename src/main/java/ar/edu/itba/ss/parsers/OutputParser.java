package ar.edu.itba.ss.parsers;

import ar.edu.itba.ss.models.Particle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class OutputParser {

    private static final String fileName = "outputTP3.xyz";
    private static final String fileName2 = "outputPythonCSV.csv";
    private static final String fileName3 = "outputBigParticlePythonCSV.csv";
    private static final String fileName4 = "fiveParticles.csv";
    private static int state_number = 0;
    private static int big_particle_state_number = 0;
    private static int ten_particles_state_number = 0;
    private static boolean first = true;
    private static boolean first10 = true;

    public static void writeUniverse(List<Particle> particles, long eTime) {
        int numberOfParticles = 4 + particles.size(); // Le metemos las 4 particulas en los vertices del universo
        try {
            StringBuilder dump = new StringBuilder("" + numberOfParticles + "\n" + "Time=" + eTime + "\n");
            appendWallParticles(dump);
            for (Particle p : particles) {
                // TODO revisar si podemos cambiar los colores de la animacion
                int rainbowPercentage = 120;
                dump.append(rainbowPercentage).append(" ");
                dump.append(p.getX()).append(" ").append(p.getY()).append(" ").append("0 ").append(p.getRadius()).append(" \n");
            }
            appendToEndOfFile(fileName,dump.toString());
//            writeAux(particlesToDraw);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void writePythonCSV(List<Particle> particles, long eTime, double tMin) {
        try {
            StringBuilder dump = new StringBuilder("");
            if(first){
                dump.append("State,Time,TMin,X,Y,V\n");
                first=false;
            }
            for (Particle p : particles) {
                if(p.getRadius() != 0.7) {
                    double v = Math.sqrt(Math.pow(p.getVx(), 2) + Math.pow(p.getVy(), 2));
                    dump.append(state_number).append(",").append(eTime).append(",").append(tMin).append(",").append(p.getX()).append(",").append(p.getY()).append(",").append(v).append("\n");
                }
            }
            state_number++;
            appendToEndOfFile(fileName2,dump.toString());
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void writeBigParticlePythonCSV(Particle bp, long eTime, double tMin) {
        try {
            StringBuilder dump = new StringBuilder("");
            if(first){
                dump.append("State,Time,TMin,X,Y\n");
                first=false;
            }
            dump.append(big_particle_state_number).append(",").append(eTime).append(",").append(tMin).append(",").append(bp.getX()).append(",").append(bp.getY());
            big_particle_state_number++;
            appendToEndOfFile(fileName3,dump.toString());
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void write10ParticlesFile(List<Particle> particles, long eTime, double tMin) {
        try {
            StringBuilder dump = new StringBuilder("");
            if(first10){
                dump.append("State,Time,TMin,X,Y\n");
                first10=false;
            }
            for(Particle p : particles) {
                if(p.isMarca()) {
                    dump.append(ten_particles_state_number).append(",").append(eTime).append(",").append(tMin).append(",").append(p.getX()).append(",").append(p.getY()).append("\n");
                }
            }
            ten_particles_state_number++;
            appendToEndOfFile(fileName4,dump.toString());
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    private static void appendWallParticles(StringBuilder dump) {
        dump.append("1").append(" ").append("0").append(" ").append("0").append(" ").append("0 ").append("0.0000001").append(" \n");
        dump.append("1").append(" ").append("6").append(" ").append("0").append(" ").append("0 ").append("0.0000001").append(" \n");
        dump.append("1").append(" ").append("0").append(" ").append("6").append(" ").append("0 ").append("0.0000001").append(" \n");
        dump.append("1").append(" ").append("6").append(" ").append("6").append(" ").append("0 ").append("0.0000001").append(" \n");
    }

    public static void createCleanPythonFile() {
        Path fileToDeletePath = Paths.get(fileName2);
        try {
            Files.deleteIfExists(fileToDeletePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileToDeletePath = Paths.get(fileName3);
        try {
            Files.deleteIfExists(fileToDeletePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileToDeletePath = Paths.get(fileName4);
        try {
            Files.deleteIfExists(fileToDeletePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void appendToEndOfFile(String file,String text) throws IOException {
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(text);
        bw.close();
    }

    public static void createCleanFile() {
        Path fileToDeletePath = Paths.get(fileName);
        try {
            Files.deleteIfExists(fileToDeletePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
