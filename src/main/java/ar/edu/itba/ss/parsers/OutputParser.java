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

    private static String fileName;
    private static boolean first = true;

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
            appendToEndOfFile(dump.toString());
//            writeAux(particlesToDraw);
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

//    public static void writeAux(int n) throws IOException {
//        String pythonFilename = "outputForPython.csv";
//        FileWriter fw = new FileWriter(pythonFilename, true);
//        BufferedWriter bw = new BufferedWriter(fw);
//        if(first){
//            bw.write(String.valueOf(n));
//            first=false;
//        }else
//            bw.write("," + n);
//        bw.close();
//    }

//    public static void createCleanPythonFile(int n) {
//        Path fileToDeletePath = Paths.get("outputForPython.csv");
//        try {
//            Files.deleteIfExists(fileToDeletePath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void setFileName(String fn) {
        fileName = fn;
    }

    private static void appendToEndOfFile(String text) throws IOException {
        FileWriter fw = new FileWriter(fileName, true);
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