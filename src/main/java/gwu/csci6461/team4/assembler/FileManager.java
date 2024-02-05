package gwu.csci6461.team4.assembler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class FileManager {

    private static FileManager fManager = null;

    private FileManager(){}

    public static synchronized FileManager getInstance() {
        if (fManager == null) {
            fManager = new FileManager();
        }
        return fManager;
    }

    public void readLinesFromFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            StringBuilder fileContent = new StringBuilder();
            Files.readAllLines(path).forEach(line -> fileContent.append(line).append("\n"));
            String[] lines = fileContent.toString().split(System.getProperty("line.separator"));
            String finalLocation = getFinalLocation(lines);
            Assembler assembler = new Assembler();
            for (String str : lines) {
                String[] splitted = str.split(" ");
                assembler.assemble(splitted[0], splitted[1], finalLocation);
            }
            createLoadFile("load.txt", assembler.getLocToInstructionMap()); // Calling writeToFile after processing all lines
        } catch (IOException exception) {
            System.out.println("Main.readLines-File Read Error : " + exception.getMessage());
        }
    }

    public void createLoadFile(String fileName, Map<String, String> locToInstructionMap) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Map.Entry<String, String> entry : locToInstructionMap.entrySet()) {
                writer.write(entry.getKey() + " " + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private static String getFinalLocation(String[] lines){
        String finalLocation = "";
        for(String str : lines) {
            String[] splitted = str.split(" ");
            if(splitted[0].equals("LOC")) {
                finalLocation = splitted[1];
            }
        }
        return finalLocation;
    }
}
