package gwu.csci6461.team4.simulator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SimulatorPanel extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        Parent root = FXMLLoader.load(getClass().getResource("/view/FrontPanel.fxml"));
        // Create a scene
        Scene scene = new Scene(root);
        // Add CSS file to the scene
        scene.getStylesheets().add(getClass().getResource("/styles/PanelStyle.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulator");
        primaryStage.show();
    }
}
