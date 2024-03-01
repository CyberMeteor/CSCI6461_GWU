package gwu.csci6461.team4.simulator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SimulatorPanelController {
    @FXML
    private Label GPR0Label;

    @FXML
    private TextField GPR0TextField;

    @FXML
    protected void GPR0LoadClick(){
        GPR0TextField.setText("000001");
    }

}
