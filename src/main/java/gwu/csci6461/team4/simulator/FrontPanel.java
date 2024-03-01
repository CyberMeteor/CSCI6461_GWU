package gwu.csci6461.team4.simulator;

import gwu.csci6461.team4.CPU;
import gwu.csci6461.team4.registers.RegisterType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;


public class FrontPanel extends JPanel implements ActionListener {
    private JTextField[] gprFields;
    private JButton[] gprButtons;
    private JTextField[] ixrFields;
    private JButton[] ixrButtons;
    private JTextField pcField;
    private JButton pcButton;
    private JTextField marField;
    private JButton marButton;
    private JTextField mbrField;
    private JButton mbrButton;
    private JTextField irField;
    private JTextField ccField;
    private JButton ccButton;
    private JTextField mfrField;
    private JButton mfrButton;
    private JTextField binaryField;
    private JTextField octalField;
    CPU cpu;
    int[] initialButtonArray = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};


    public FrontPanel() {
        initComponents();
        cpu = new CPU();
        int[] tempValue = {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0};
        cpu.setRegisterValue(RegisterType.ProgramCounter, tempValue);
        new Timer(delay, mainLoop).start();
    }

    //Main loop runs every 500 ms
    int delay = 500;
    ActionListener mainLoop = evt -> {

        //Update the Registers for display
        updateRegisters();
    };

    private void initComponents(){
        setSize(600, 400);
        // Set the layout
        setLayout(new BorderLayout());
        // Set the background color to open blue

        // Create a panel for the text fields and buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(135, 206, 235));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;


        // Create and add the text fields, GPR buttons, and IXR buttons
        gprFields = new JTextField[4];
        gprButtons = new JButton[4];
        ixrFields = new JTextField[3];
        ixrButtons = new JButton[3];

        for (int i = 0; i < 4; i++) {
            JLabel label = new JLabel("GPR " + i);
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            panel.add(label, gbc);

            gprFields[i] = new JTextField(16);
            gprFields[i].setName("GPR" + i);
            gbc.gridx = 1;
            panel.add(gprFields[i], gbc);

            gprFields[i].addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();
                    if (!Character.isDigit(c)) {
                        e.consume(); // Ignore input that's not a digit
                    }
                }
            });

            gprButtons[i] = new JButton("Load");
            gprButtons[i].addActionListener(this);
            gprButtons[i].setActionCommand("GPR_" + i);
            gbc.gridx = 2;
            panel.add(gprButtons[i], gbc);

            if (i > 0) {
                JLabel ixrLabel = new JLabel("IXR " + i);
                gbc.gridx = 3;
                panel.add(ixrLabel, gbc);

                ixrFields[i - 1] = new JTextField(16);
                ixrFields[i - 1].setName("IXR" + i);
                gbc.gridx = 4;
                panel.add(ixrFields[i - 1], gbc);

                ixrFields[i - 1].addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        char c = e.getKeyChar();
                        if (!Character.isDigit(c)) {
                            e.consume(); // Ignore input that's not a digit
                        }
                    }
                });

                ixrButtons[i - 1] = new JButton("Load");
                ixrButtons[i - 1].addActionListener(this);
                ixrButtons[i - 1].setActionCommand("IXR_" + i);
                gbc.gridx = 5;
                panel.add(ixrButtons[i - 1], gbc);
            }
        }

        // Add BINARY text field and load button
        JLabel binaryLabel = new JLabel("BINARY");
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(binaryLabel, gbc);

        binaryField = new JTextField(10);
        binaryField.setName("BINARY");
        binaryField.setText(formatText(new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}));
        gbc.gridx = 1;
        panel.add(binaryField, gbc);

        binaryField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); // Ignore input that's not a digit
                }
            }
        });

        // Add OCTAL INPUT text field and load button
        JLabel octalLabel = new JLabel("OCTAL INPUT");
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(octalLabel, gbc);

        octalField = new JTextField(10);
        octalField.setName("OCTAL_INPUT");
        octalField.setActionCommand("OCTAL_INPUT");
        octalField.setText("0");
        gbc.gridx = 1;
        panel.add(octalField, gbc);
        octalField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the octal input from the octal text field
                String octalInput = octalField.getText();

                // Convert octal input to binary
                String binaryOutput = octalToBinary(octalInput);

                for (int i = 0; i < binaryOutput.length(); i++) {
                    initialButtonArray[i] = Character.getNumericValue(binaryOutput.charAt(i));
                }

                binaryField.setText(formatText(initialButtonArray));
            }
        });


        octalField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); // Ignore input that's not a digit
                }
            }
        });


        // Create and add the buttons for PC, MAR, MBR, IR, CC, and MFR
        JLabel pcLabel = new JLabel("PC");
        gbc.gridx = 6;
        gbc.gridy = 1;
        panel.add(pcLabel, gbc);

        pcField = new JTextField(12);
        pcField.setName("PC");
        gbc.gridx = 7;
        panel.add(pcField, gbc);

        pcField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); // Ignore input that's not a digit
                }
            }
        });

        pcButton = new JButton("Load");
        pcButton.addActionListener(this);
        pcButton.setActionCommand("PC");
        gbc.gridx = 8;
        panel.add(pcButton, gbc);

        JLabel marLabel = new JLabel("MAR");
        gbc.gridx = 6;
        gbc.gridy = 2;
        panel.add(marLabel, gbc);

        marField = new JTextField(16);
        marField.setName("MAR");
        gbc.gridx = 7;
        panel.add(marField, gbc);

        marField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); // Ignore input that's not a digit
                }
            }
        });

        marButton = new JButton("Load");
        marButton.addActionListener(this);
        marButton.setActionCommand("MAR");
        gbc.gridx = 8;
        panel.add(marButton, gbc);

        JLabel mbrLabel = new JLabel("MBR");
        gbc.gridx = 6;
        gbc.gridy = 3;
        panel.add(mbrLabel, gbc);

        mbrField = new JTextField(16);
        mbrField.setName("MBR");
        gbc.gridx = 7;
        panel.add(mbrField, gbc);

        mbrField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); // Ignore input that's not a digit
                }
            }
        });

        mbrButton = new JButton("Load");
        mbrButton.addActionListener(this);
        mbrButton.setActionCommand("MBR");
        gbc.gridx = 8;
        panel.add(mbrButton, gbc);

        JLabel irLabel = new JLabel("IR");
        gbc.gridx = 6;
        gbc.gridy = 4;
        panel.add(irLabel, gbc);

        irField = new JTextField(10);
        irField.setName("IR");
        gbc.gridx = 7;
        panel.add(irField, gbc);

        irField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); // Ignore input that's not a digit
                }
            }
        });

        JLabel ccLabel = new JLabel("CC");
        gbc.gridx = 6;
        gbc.gridy = 6;
        panel.add(ccLabel, gbc);

        ccField = new JTextField(4);
        ccField.setName("CC");
        gbc.gridx = 7;
        panel.add(ccField, gbc);

        ccField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); // Ignore input that's not a digit
                }
            }
        });

        JLabel mfrLabel = new JLabel("MFR");
        gbc.gridx = 6;
        gbc.gridy = 7;
        panel.add(mfrLabel, gbc);

        mfrField = new JTextField(4);
        mfrField.setName("MFR");
        gbc.gridx = 7;
        panel.add(mfrField, gbc);

        mfrField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); // Ignore input that's not a digit
                }
            }
        });


        String[] buttonNames = {"Load", "Load+", "Store", "Store+", "Run", "Step", "Halt", "IPL"};
        for (int i = 0; i < 8; i++) {
            JButton button = new JButton(buttonNames[i]);
            button.addActionListener(this);
            gbc.gridx = i;
            gbc.gridy = 8; // Place the buttons below the Octal Input field
            panel.add(button, gbc);
        }

        // Add the panel to the frame
        add(panel, BorderLayout.CENTER);

        // Set the frame to be visible
        setVisible(true);
        // Set the default close operation
    }

    private void updateRegisters(){
        //Update PC
        int[] tempRegisterValue;
        tempRegisterValue = cpu.getRegisterValue(RegisterType.ProgramCounter);
        pcField.setText(formatText(tempRegisterValue));

        //Update CC
        tempRegisterValue = cpu.getRegisterValue(RegisterType.ConditionCode);
        ccField.setText(formatText(tempRegisterValue));

        //Update MAR
        tempRegisterValue = cpu.getRegisterValue(RegisterType.MemoryAddressRegister);
        marField.setText(formatText(tempRegisterValue));

        //Update MBR
        tempRegisterValue = cpu.getRegisterValue(RegisterType.MemoryBufferRegister);
        mbrField.setText(formatText(tempRegisterValue));
        //Update IR
        tempRegisterValue = cpu.getRegisterValue(RegisterType.InstructionRegister);
        irField.setText(formatText(tempRegisterValue));
        //Update MFR
        tempRegisterValue = cpu.getRegisterValue(RegisterType.MemoryFaultRegister);
        mfrField.setText(formatText(tempRegisterValue));
        //Update X1
        tempRegisterValue = cpu.getRegisterValue(RegisterType.IndexRegister1);
        ixrFields[0].setText(formatText(tempRegisterValue));
        //Update X2
        tempRegisterValue = cpu.getRegisterValue(RegisterType.IndexRegister2);
        ixrFields[1].setText(formatText(tempRegisterValue));
        //Update X3
        tempRegisterValue = cpu.getRegisterValue(RegisterType.IndexRegister3);
        ixrFields[2].setText(formatText(tempRegisterValue));
        //Update GPR0
        tempRegisterValue = cpu.getRegisterValue(RegisterType.GeneralPurposeRegister0);
        gprFields[0].setText(formatText(tempRegisterValue));
        //Update GPR1
        tempRegisterValue = cpu.getRegisterValue(RegisterType.GeneralPurposeRegister1);
        gprFields[1].setText(formatText(tempRegisterValue));
        //Update GPR2
        tempRegisterValue = cpu.getRegisterValue(RegisterType.GeneralPurposeRegister2);
        gprFields[2].setText(formatText(tempRegisterValue));
        //Update GPR3
        tempRegisterValue = cpu.getRegisterValue(RegisterType.GeneralPurposeRegister3);
        gprFields[3].setText(formatText(tempRegisterValue));

    }

    //Function to format int[] values to display on the GUI
    public String formatText(int[] arr) {
        String res = Arrays.toString(arr).replaceAll("[\\[\\],]", "");
        return res;
    }

    private String octalToBinary(String octalInput) {
        // Convert octal string to binary string
        StringBuilder binaryString = new StringBuilder();
        for (int i = 0; i < octalInput.length(); i++) {
            char octalChar = octalInput.charAt(i);
            int octalDigit = Character.digit(octalChar, 8); // Use base 8 (octal)
            String binaryDigit = String.format("%03d", Integer.parseInt(Integer.toBinaryString(octalDigit)));
            binaryString.append(binaryDigit);
        }

        // Pad with zeros to make it 16 bits
        while (binaryString.length() < 16) {
            binaryString.insert(0, "0");
        }

        return binaryString.toString();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // Action performed logic here
    }

}