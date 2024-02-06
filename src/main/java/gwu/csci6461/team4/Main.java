package gwu.csci6461.team4;


import gwu.csci6461.team4.assembler.FileManager;

public class Main {
    public static void main(String[] args) {
        FileManager fileManager = FileManager.getInstance();
        fileManager.readLinesFromFile("input.txt"); //Reading input.txt file of instructions.
    }

}
