package gwu.csci6461.team4.console;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;


public class ConsoleController {
    @FXML
    protected void SubmitClick() {
        return;
    }

    // Labels
    @FXML
    private Label ConsoleLabel;

    @FXML
    private Label InputLabel;

    @FXML
    private Label OutputLabel;

    @FXML
    private Label PrinterLabel;


    // TextArea
    @FXML
    private TextArea InputTextArea;

    @FXML
    private TextArea OutputTextArea;

    @FXML
    private TextArea PrinterTextArea;

    private ConsoleIO consoleIO;

    // Method to append text to OutputTextArea
    public void appendToOutputTextArea(String text) {
        OutputTextArea.appendText(text);
    }

    // Method to append text to PrinterTextArea
    public void appendToPrinterTextArea(String text) {
        PrinterTextArea.setText(text);
    }

    // Method to initialize the controller
    @FXML
    public void initialize() {
        // Initialize ConsoleIO and redirect System.out to OutputTextArea
        consoleIO = new ConsoleIO(this);
        consoleIO.redirectSystemOut();
        InputTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            // Update the PrinterTextArea with the new value
            appendToPrinterTextArea(newValue);
        });
    }
}
