package gwu.csci6461.team4;


import gwu.csci6461.team4.assembler.Assembler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
      readLines();
    }

    private static void readLines(){
        try{
            Path filePath = Paths.get("input.txt");
            StringBuilder fileContent = new StringBuilder();
            Files.readAllLines(filePath).forEach(line -> fileContent.append(line).append("\n"));
            String[] lines = fileContent.toString().split(System.getProperty("line.separator"));

            for(String str : lines) {
                String[] splitted = str.split(" ");
                Assembler assembler = new Assembler();
                assembler.assemble(splitted[0], splitted[1]);
            }


        }

        catch (IOException exception) {
            System.out.println("Main.readLines-File Read Error : " + exception.getMessage());
        }
    }

}