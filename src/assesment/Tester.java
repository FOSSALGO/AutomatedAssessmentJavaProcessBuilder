package assesment;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class Tester {

    public static void main(String[] args) {
        String sDir = "src/resource/";
        String sSourceCode = "Matriks.java";
        String sInput = "input.txt";
        String sOutput = "output.txt";//output yang seharusnya

        try {
            String input = Files.readString(Path.of(sDir + sInput));
            String output = Files.readString(Path.of(sDir + sOutput));

            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.directory(new File(sDir));
            processBuilder.command("cmd.exe", "/c", "java " + sSourceCode);
            processBuilder.redirectInput(new File(sDir + sInput));

            Process process = processBuilder.start();
            process.waitFor();

            //RESULT
            String outputTesting = extractOutput(process);
            String errorTesting = extractError(process);

            if (errorTesting.length() > 0) {
                System.out.println("RESULT:");
                System.out.println("--------------------------------------------");
                System.err.println("Ditemukan Error Program");
                System.out.println(errorTesting);
                System.out.println("--------------------------------------------");
            } else {
                System.out.println("RESULT:");
                System.out.println("--------------------------------------------");
                System.out.println("Program Berhasil");
                System.out.println(outputTesting);
                System.out.println("--------------------------------------------");
                if (output.trim().equals(outputTesting.trim())) {
                    System.out.println("CORRECT");
                }else{
                    System.err.println("WRONG ANSWER");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
       
    }

    public static String extractOutput(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        return sb.toString();
    }

    public static String extractError(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        return sb.toString();
    }

}
