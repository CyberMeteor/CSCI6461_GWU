package gwu.csci6461.team4.assembler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
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
            createListingFile("listing.txt", assembler.getLocToInstructionMap(), lines); // Write the listing file for the test case
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

    public void createListingFile(String fileName, Map<String, String> locToInstructionMap, String[] inputInstructions) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Iterate through the locToInstructionMap and inputInstructions array simultaneously
            Iterator<Map.Entry<String, String>> mapIterator = locToInstructionMap.entrySet().iterator();
            int inputIndex = 0;
            while (mapIterator.hasNext()) {
                Map.Entry<String, String> entry = mapIterator.next();
                String loc = entry.getKey();
                String octalInstruction = entry.getValue();
                String inputInstruction = inputInstructions[inputIndex];
                // Write the line with the input instruction added
                writer.write(loc + " " + octalInstruction + " " + inputInstruction);
                writer.newLine();
                inputIndex++;
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
