package gwu.csci6461.team4.simulator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class SimulatorPanelController {

    // button onClick() function
    @FXML
    protected void GPR0LoadClick(){
        GPR0TextField.setText("000001");
    }

    @FXML
    protected void GPR1LoadClick(){
        // TODO add handling code here:

    }

    @FXML
    protected void GPR2LoadClick(){
        // TODO add handling code here:

    }
    @FXML
    protected void GPR3LoadClick(){
        // TODO add handling code here:

    }

    @FXML
    protected void IXR1LoadClick(){
        // TODO add handling code here:

    }

    @FXML
    protected void IXR2LoadClick(){
        // TODO add handling code here:

    }

    @FXML
    protected void IXR3LoadClick(){
        // TODO add handling code here:

    }

    @FXML
    protected void PCLoadClick(){
        // TODO add handling code here:

    }

    @FXML
    protected void MARLoadClick(){
        // TODO add handling code here:

    }

    @FXML
    protected void MBRLoadClick(){
        // TODO add handling code here:

    }

    @FXML
    protected void LoadClick(){
        // TODO add handling code here:

    }

    @FXML
    protected void LoadPlusClick(){
        // TODO add handling code here:

    }

    @FXML
    protected void StoreClick(){
        // TODO add handling code here:

    }

    @FXML
    protected void StorePlusClick(){
        // TODO add handling code here:

    }

    @FXML
    protected void RunClick(){
        // TODO add handling code here:

    }

    @FXML
    protected void StepClick(){
        // TODO add handling code here:

    }

    @FXML
    protected void HaltClick(){
        // TODO add handling code here:

    }

    @FXML
    protected void IPLClick(){
        // Asks for the txt file to be read and once we select the ipl.txt file then it reads the file and loads into memory

        // Create a file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select IPL File");

        //Set the initial directory to the user's home directory
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        //Set the file extension filter to only show text files
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);

        //Show the file chooser dialog
        File selectedFile = fileChooser.showOpenDialog(null);

        //If a file is selected
        if (selectedFile != null){
            //Read the content of the selected file and put it into the memory (now I print it for testing)
            try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                while ((line = br.readLine()) != null){
                    //Save to the memory
                    System.out.println(line);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    // Labels
    @FXML
    private Label GPR0Label;

    @FXML
    private Label GPR1Label;

    @FXML
    private Label GPR2Label;

    @FXML
    private Label GPR3Label;

    @FXML
    private Label IXR1Label;

    @FXML
    private Label IXR2Label;

    @FXML
    private Label IXR3Label;

    @FXML
    private Label PCLabel;

    @FXML
    private Label MARLabel;

    @FXML
    private Label MBRLabel;

    @FXML
    private Label IRLabel;

    @FXML
    private Label CCLabel;

    @FXML
    private Label MFRLabel;

    @FXML
    private Label LoadLabel;

    @FXML
    private Label LoadPlusLabel;

    @FXML
    private Label StoreLabel;

    @FXML
    private Label StorePlusLabel;

    @FXML
    private Label RunLabel;

    @FXML
    private Label StepLabel;

    @FXML
    private Label HaltLabel;

    @FXML
    private Label OctalInputLabel;

    @FXML
    private Label BinaryLabel;

    @FXML
    private Label IPLLabel;

    @FXML
    private Label ProgramFileLabel;

    @FXML
    private Label SimulatorTitleLabel;


    //TextFields
    @FXML
    private TextField GPR0TextField;

    @FXML
    private TextField GPR1TextField;

    @FXML
    private TextField GPR2TextField;

    @FXML
    private TextField GPR3TextField;

    @FXML
    private TextField IXR1TextField;

    @FXML
    private TextField IXR2TextField;

    @FXML
    private TextField IXR3TextField;

    @FXML
    private TextField PCTextField;

    @FXML
    private TextField MARTextField;

    @FXML
    private TextField MBRTextField;

    @FXML
    private TextField IRTextField;

    @FXML
    private TextField CCTextField;

    @FXML
    private TextField MFRTextField;

    @FXML
    private TextField OctalInputTextField;

    @FXML
    private TextField BinaryTextField;

    @FXML
    private TextField ProgramFileTextField;

}
