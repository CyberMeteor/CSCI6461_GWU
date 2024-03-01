package gwu.csci6461.team4.simulator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SimulatorPanel extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/FrontPanel.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Simulator");
        primaryStage.show();
    }
}
