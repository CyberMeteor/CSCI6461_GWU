package gwu.csci6461.team4;


import gwu.csci6461.team4.assembler.FileManager;
//import gwu.csci6461.team4.simulator.FrontPanel;
//import gwu.csci6461.team4.simulator.SimulatorFrame;
import gwu.csci6461.team4.simulator.SimulatorPanel;
import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
//        FileManager fileManager = FileManager.getInstance();
//        fileManager.readLinesFromFile("input.txt"); //Reading input.txt file of instructions.

        // old UI(Java.Swing)
//        SimulatorFrame simulatorFrame = new SimulatorFrame();

        // new UI(JavaFX)
        Application.launch(SimulatorPanel.class, args);
    }

}
