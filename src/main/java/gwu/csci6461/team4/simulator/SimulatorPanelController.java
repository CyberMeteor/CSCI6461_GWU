package gwu.csci6461.team4.simulator;

import gwu.csci6461.team4.CPU;
import gwu.csci6461.team4.registers.RegisterType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javafx.util.Duration;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SimulatorPanelController {

    private Timeline timeline;
    boolean runCheck = false;

    @FXML
    public void initialize() {
        initComponents();
        cpu = new CPU();
        int[] tempValue = {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0};
        cpu.setRegisterValue(RegisterType.ProgramCounter, tempValue);
        timeline = new Timeline(new KeyFrame(Duration.millis(500), e -> {
            updateRegisters();
            if (runCheck) {
                cpu.execute("single");
                // If running, check if the halt flag is raised
                if (cpu.getRegisterValue(RegisterType.HLT)[0] == 1) {
                    runCheck = false;
                    int[] msg = new int[]{0};
                    cpu.setRegisterValue(RegisterType.HLT, msg);
                }
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


    CPU cpu;
    int[] initialArray = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    // button onClick() function
    @FXML
    protected void GPR0LoadClick() {
        cpu.setRegisterValue(RegisterType.GeneralPurposeRegister0, Arrays.copyOfRange(initialArray, 0, 16));
    }

    @FXML
    protected void GPR1LoadClick() {
        cpu.setRegisterValue(RegisterType.GeneralPurposeRegister1, Arrays.copyOfRange(initialArray, 0, 16));
    }

    @FXML
    protected void GPR2LoadClick() {
        cpu.setRegisterValue(RegisterType.GeneralPurposeRegister2, Arrays.copyOfRange(initialArray, 0, 16));
    }

    @FXML
    protected void GPR3LoadClick() {
        cpu.setRegisterValue(RegisterType.GeneralPurposeRegister3, Arrays.copyOfRange(initialArray, 0, 16));
    }

    @FXML
    protected void IXR1LoadClick() {
        cpu.setRegisterValue(RegisterType.IndexRegister1, Arrays.copyOfRange(initialArray, 0, 16));
    }

    @FXML
    protected void IXR2LoadClick() {
        cpu.setRegisterValue(RegisterType.IndexRegister2, Arrays.copyOfRange(initialArray, 0, 16));
    }

    @FXML
    protected void IXR3LoadClick() {
        cpu.setRegisterValue(RegisterType.IndexRegister3, Arrays.copyOfRange(initialArray, 0, 16));
    }

    @FXML
    protected void PCLoadClick() {
        cpu.setRegisterValue(RegisterType.ProgramCounter, Arrays.copyOfRange(initialArray, 4, 16));
    }

    @FXML
    protected void MARLoadClick() {
        int[] deneme = initialArray;
        cpu.setRegisterValue(RegisterType.MemoryAddressRegister, Arrays.copyOfRange(initialArray, 4, 16));
    }

    @FXML
    protected void MBRLoadClick() {
        cpu.setRegisterValue(RegisterType.MemoryBufferRegister, Arrays.copyOfRange(initialArray, 0, 16));
    }

    @FXML
    protected void LoadClick() {
        // Set MBR to the value located at MAR
        int[] currentMAR = cpu.getRegisterValue(RegisterType.MemoryAddressRegister);
        int transformedMAR = cpu.binaryToInt(currentMAR);
        cpu.setRegisterValue(RegisterType.MemoryBufferRegister, cpu.getMemoryValue(transformedMAR));
    }

    @FXML
    protected void LoadPlusClick() {
        // Set MBR to the value located at MAR
        int[] currentMAR = cpu.getRegisterValue(RegisterType.MemoryAddressRegister);
        int transformedMAR = cpu.binaryToInt(currentMAR);
        cpu.setRegisterValue(RegisterType.MemoryBufferRegister, cpu.getMemoryValue(transformedMAR));
        //Increment MAR 1
        transformedMAR++;
        int[] newMAR = cpu.intToBinaryArrayShort(Integer.toBinaryString(transformedMAR));
        cpu.setRegisterValue(RegisterType.MemoryAddressRegister, newMAR);
    }

    @FXML
    protected void StoreClick() {
        // Set Memory(MAR) to MBR
        int[] currentMAR = cpu.getRegisterValue(RegisterType.MemoryAddressRegister);
        int transformedMAR = cpu.binaryToInt(currentMAR);
        cpu.setMemoryValue(transformedMAR, cpu.getRegisterValue(RegisterType.MemoryBufferRegister));
    }

    @FXML
    protected void StorePlusClick() {
        // Setting MAR to MBR
        int[] currentMAR = cpu.getRegisterValue(RegisterType.MemoryAddressRegister);
        int transformedMAR = cpu.binaryToInt(currentMAR);
        cpu.setMemoryValue(transformedMAR, cpu.getRegisterValue(RegisterType.MemoryBufferRegister));
        //ADD 1
        transformedMAR++;
        int[] newMAR = cpu.intToBinaryArrayShort(Integer.toBinaryString(transformedMAR));
        cpu.setRegisterValue(RegisterType.MemoryAddressRegister, newMAR);
    }

    @FXML
    protected void RunClick() {
        if (runCheck == false) {
            runCheck = true;
        } else {
            runCheck = false;
        }
    }

    @FXML
    protected void StepClick() {
        cpu.execute("single");
    }

    @FXML
    protected void HaltClick() {
        int[] msg = new int[]{1};
        cpu.setRegisterValue(RegisterType.HLT, msg);
    }

    @FXML
    protected void IPLClick() {
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
        if (selectedFile != null) {
            // Set the path of the selected file to the ProgramFileTextField
            ProgramFileTextField.setText(selectedFile.getAbsolutePath());

            //Read the content of the selected file and put it into the memory
            try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                int rowNum = 0;
                while ((line = br.readLine()) != null && rowNum < 2048) {
                    String[] tokens = line.split(" ");

                    //Get the address and load into MAR
                    int row = cpu.octalToInt(tokens[0]);
                    int[] row_binary = cpu.octalToBinaryArrayShort(tokens[0]);
                    cpu.setRegisterValue(RegisterType.MemoryAddressRegister, row_binary);

                    //Get the value and load into MBR
                    int[] value = cpu.octalToBinaryArray(tokens[1]);
                    cpu.setRegisterValue(RegisterType.MemoryBufferRegister, value);

                    cpu.setMemoryValue(row, value);
                    rowNum++;
                }
            } catch (IOException ex) {
                Logger.getLogger(SimulatorPanelController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        int[] default_PC_loc = new int[]{0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0};
        cpu.setRegisterValue(RegisterType.ProgramCounter, default_PC_loc);
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


    public void initComponents() {
        OctalInputTextField.setOnAction(event -> {
            // Get the octal input from the octal text field and remove spaces
            String octalInput = OctalInputTextField.getText().replaceAll("\\s", "");

            // Convert octal input to binary
            String binaryOutput = octalToBinary(octalInput);

            // Ensure binaryOutput is exactly 16 characters long
            if (binaryOutput.length() < 16) {
                binaryOutput = "0".repeat(16 - binaryOutput.length()) + binaryOutput;
            }

            for (int i = 0; i < 16; i++) {
                initialArray[i] = Character.getNumericValue(binaryOutput.charAt(i));
            }

            BinaryTextField.setText(formatText(initialArray));
        });

        BinaryTextField.setOnAction(event -> {
            // Get the binary input from the BINARY text field and remove spaces
            String binaryInput = BinaryTextField.getText().replaceAll("\\s", "");

            // Pad the binary input with zeros at the beginning if it's shorter than 16 bits
            while (binaryInput.length() < 16) {
                binaryInput = "0" + binaryInput;
            }

            // Convert binary input to octal
            String octalOutput = binaryToOctal(binaryInput);

            // Ensure octalOutput is exactly 6 characters long by trimming or adding zeros at the beginning
            if (octalOutput.length() < 6) {
                octalOutput = "0".repeat(6 - octalOutput.length()) + octalOutput;
            } else if (octalOutput.length() > 6) {
                octalOutput = octalOutput.substring(octalOutput.length() - 6);
            }

            OctalInputTextField.setText(octalOutput);

            // Set the binary input to the initialArray
            for (int i = 0; i < 16; i++) {
                initialArray[i] = i < binaryInput.length() ? Character.getNumericValue(binaryInput.charAt(i)) : 0;
            }
        });
    }

    private String octalToBinary(String octalInput) {
        // Convert octal input to decimal first
        String decimalInput = Integer.toString(Integer.parseInt(octalInput, 8));

        // Convert decimal to binary using the decimalToBinary method
        return decimalToBinary(decimalInput);
    }

    private String decimalToBinary(String decimalAddress) {
        if (decimalAddress == null) {
            decimalAddress = "0";
        }
        return new BigInteger(decimalAddress, 10).toString(2);
    }

    private String binaryToOctal(String binaryInput) {
        // Convert binary input to decimal first
        int decimalInput = Integer.parseInt(binaryInput, 2);

        // Convert decimal to octal
        String octalOutput = Integer.toOctalString(decimalInput);

        return octalOutput;
    }

    public void updateRegisters() {

        //Update PC
        int[] tempRegisterValue;
        tempRegisterValue = cpu.getRegisterValue(RegisterType.ProgramCounter);
        PCTextField.setText(formatText(tempRegisterValue));

        //Update CC
        tempRegisterValue = cpu.getRegisterValue(RegisterType.ConditionCode);
        CCTextField.setText(formatText(tempRegisterValue));

        //Update MAR
        tempRegisterValue = cpu.getRegisterValue(RegisterType.MemoryAddressRegister);
        MARTextField.setText(formatText(tempRegisterValue));

        //Update MBR
        tempRegisterValue = cpu.getRegisterValue(RegisterType.MemoryBufferRegister);
        MBRTextField.setText(formatText(tempRegisterValue));
        //Update IR
        tempRegisterValue = cpu.getRegisterValue(RegisterType.InstructionRegister);
        IRTextField.setText(formatText(tempRegisterValue));
        //Update MFR
        tempRegisterValue = cpu.getRegisterValue(RegisterType.MemoryFaultRegister);
        MFRTextField.setText(formatText(tempRegisterValue));
        //Update X1
        tempRegisterValue = cpu.getRegisterValue(RegisterType.IndexRegister1);
        IXR1TextField.setText(formatText(tempRegisterValue));
        //Update X2
        tempRegisterValue = cpu.getRegisterValue(RegisterType.IndexRegister2);
        IXR2TextField.setText(formatText(tempRegisterValue));
        //Update X3
        tempRegisterValue = cpu.getRegisterValue(RegisterType.IndexRegister3);
        IXR3TextField.setText(formatText(tempRegisterValue));
        //Update GPR0
        tempRegisterValue = cpu.getRegisterValue(RegisterType.GeneralPurposeRegister0);
        GPR0TextField.setText(formatText(tempRegisterValue));
        //Update GPR1
        tempRegisterValue = cpu.getRegisterValue(RegisterType.GeneralPurposeRegister1);
        GPR1TextField.setText(formatText(tempRegisterValue));
        //Update GPR2
        tempRegisterValue = cpu.getRegisterValue(RegisterType.GeneralPurposeRegister2);
        GPR2TextField.setText(formatText(tempRegisterValue));
        //Update GPR3
        tempRegisterValue = cpu.getRegisterValue(RegisterType.GeneralPurposeRegister3);
        GPR3TextField.setText(formatText(tempRegisterValue));
    }

    //Function to format int[] values
    public String formatText(int[] arr) {
        String res = Arrays.toString(arr).replaceAll("[\\[\\],]", "");
        return res;
    }


}
