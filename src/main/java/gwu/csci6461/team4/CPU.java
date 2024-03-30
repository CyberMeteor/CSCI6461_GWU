package gwu.csci6461.team4;

import gwu.csci6461.team4.cache.Cache;
import gwu.csci6461.team4.assembler.Assembler;
import gwu.csci6461.team4.console.ConsoleController;
import gwu.csci6461.team4.registers.Register;
import gwu.csci6461.team4.registers.RegisterType;
import javafx.application.Platform;
import javafx.scene.control.TextInputDialog;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class CPU {

    Register mar = new Register(12);
    Register mbr = new Register(16);
    Register pc = new Register(12);
    Register x1 = new Register(16);
    Register x2 = new Register(16);
    Register x3 = new Register(16);
    ArrayList<Register> IXRList = new ArrayList<Register>();
    Register gpr0 = new Register(16);
    Register gpr1 = new Register(16);
    Register gpr2 = new Register(16);
    Register gpr3 = new Register(16);
    ArrayList<Register> GPRList = new ArrayList<Register>();
    Register cc = new Register(4);
    Register ir = new Register(16);
    Register mfr = new Register(4);
    Register hlt = new Register(1);
    Memory mem = new Memory();
    private BigInteger maxINT = BigInteger.valueOf((long) Math.pow(2, 16));
    private Cache cache = new Cache();
    private ArrayList<Integer> asciiValue = new ArrayList<Integer>();
    private boolean executeInstructions = true;



    public CPU(){
        GPRList.add(gpr0); GPRList.add(gpr1); GPRList.add(gpr2); GPRList.add(gpr3);
        IXRList.add(x1); IXRList.add(x2); IXRList.add(x3);
    }


    public void setRegisterValue(RegisterType registerType, int[] value) {
        if (registerType.getType().equals("MAR")) {
            mar.setRegisterValue(value);
        } else if (registerType.getType().equals("MBR")) {
            mbr.setRegisterValue(value);
        } else if (registerType.getType().equals("PC")) {
            pc.setRegisterValue(value);
        } else if (registerType.getType().equals("CC")) {
            cc.setRegisterValue(value);
        } else if (registerType.getType().equals("IR")) {
            ir.setRegisterValue(value);
        } else if (registerType.getType().equals("X1")) {
            x1.setRegisterValue(value);
        } else if (registerType.getType().equals("X2")) {
            x2.setRegisterValue(value);
        } else if (registerType.getType().equals("X3")) {
            x3.setRegisterValue(value);
        } else if (registerType.getType().equals("MFR")) {
            mfr.setRegisterValue(value);
        } else if (registerType.getType().equals("GPR0")) {
            gpr0.setRegisterValue(value);
        } else if (registerType.getType().equals("GPR1")) {
            gpr1.setRegisterValue(value);
        } else if (registerType.getType().equals("GPR2")) {
            gpr2.setRegisterValue(value);
        } else if (registerType.getType().equals("GPR3")) {
            gpr3.setRegisterValue(value);
        } else if (registerType.getType().equals("HLT")) {
            hlt.setRegisterValue(value);
        }
    }

    public int[] getRegisterValue(RegisterType registerType) {
        int[] registerValue = new int[0];
        if (registerType.getType().equals("PC")) {
            registerValue = pc.getRegisterValue();
        } else if (registerType.getType().equals("GPR0")) {
            registerValue = gpr0.getRegisterValue();
        } else if (registerType.getType().equals("GPR1")) {
            registerValue = gpr1.getRegisterValue();
        } else if (registerType.getType().equals("GPR2")) {
            registerValue = gpr2.getRegisterValue();
        } else if (registerType.getType().equals("GPR3")) {
            registerValue = gpr3.getRegisterValue();
        } else if (registerType.getType().equals("CC")) {
            registerValue = cc.getRegisterValue();
        } else if (registerType.getType().equals("IR")) {
            registerValue = ir.getRegisterValue();
        } else if (registerType.getType().equals("MAR")) {
            registerValue = mar.getRegisterValue();
        } else if (registerType.getType().equals("X1")) {
            registerValue = x1.getRegisterValue();
        } else if (registerType.getType().equals("X2")) {
            registerValue = x2.getRegisterValue();
        } else if (registerType.getType().equals("X3")) {
            registerValue = x3.getRegisterValue();
        } else if (registerType.getType().equals("MBR")) {
            registerValue = mbr.getRegisterValue();
        } else if (registerType.getType().equals("MFR")) {
            registerValue = mfr.getRegisterValue();
        } else if (registerType.getType().equals("HLT")){
            registerValue = hlt.getRegisterValue();
        }
        return registerValue;
    }

    public int binaryToInt(int[] binary) {
        int returnVal;
        StringBuilder binaryBuilder = new StringBuilder();
        for (int i = 1; i < binary.length; i++) {
            binaryBuilder.append(binary[i]);
        }
        returnVal = Integer.parseInt(binaryBuilder.toString(), 2);
        if (binary[0] == 1) {
            returnVal = -returnVal;
        }
        return returnVal;
    }


    //Converts a binary int value to binary array value specifically for the PC
    public int[] intToBinaryArrayShort(String value) {
        int[] returnValue = new int[12];

        char[] arr = value.toCharArray();


        for (int i = 0; i < 12; i++) {
            if (i < 12 - arr.length) {
                returnValue[i] = 0;
            } else {
                returnValue[i] = Character.getNumericValue(value.charAt(i - (12 - arr.length)));
            }

        }
        return returnValue;
    }

    //Converts a binary int value to binary array value.
    public int[] intToBinaryArray(String value) {
        boolean negative = false;

        if (value.charAt(0) == '-') {
            negative = true;
            value = value.substring(1); // Remove the negative sign
        }

        int[] returnValue = new int[16];

        char[] arr = value.toCharArray();

        // Set sign bit
        returnValue[0] = negative ? 1 : 0;

        for (int i = 1; i < 16; i++) {
            if (i < 16 - arr.length) {
                returnValue[i] = 0;
            } else {
                returnValue[i] = Character.getNumericValue(value.charAt(i - (16 - arr.length)));
            }
        }

        return returnValue;
    }

    public int octalToInt(String octal) {
        int ret_val = Integer.parseInt(octal, 8);
        return ret_val;
    }

    // For MBR
    public int[] octalToBinaryArray(String octal) {
        // Check if the number is negative
        boolean negative = false;
        if (octal.charAt(0) == '-') {
            negative = true;
            octal = octal.substring(1); // Remove the negative sign
        }

        // Convert octal to binary
        StringBuilder binaryBuilder = new StringBuilder();
        for (int i = 0; i < octal.length(); i++) {
            int digit = Character.digit(octal.charAt(i), 8);
            String binaryDigit = String.format("%03d", Integer.parseInt(Integer.toBinaryString(digit)));
            binaryBuilder.append(binaryDigit);
        }

        // Truncate or extend the binary string to 16 bits
        String binary = binaryBuilder.toString();
        if (binary.length() < 16) {
            binary = "0".repeat(16 - binary.length()) + binary; // Extend with zeros
        } else if (binary.length() > 16) {
            binary = binary.substring(binary.length() - 16); // Truncate
        }

        // Convert the binary string to an int array
        int[] ret_val = new int[16];
        for (int i = 0; i < 16; i++) {
            ret_val[i] = Character.getNumericValue(binary.charAt(i));
        }

        // Set sign bit for negative numbers
        if (negative) {
            ret_val[0] = 1;
        }

        return ret_val;
    }




    // For MAR
    public int[] octalToBinaryArrayShort(String octal) {
        int[] ret_val = new int[12];
        octal = octal.replaceAll("0", "000");
        octal = octal.replaceAll("1", "001");
        octal = octal.replaceAll("2", "010");
        octal = octal.replaceAll("3", "011");
        octal = octal.replaceAll("4", "100");
        octal = octal.replaceAll("5", "101");
        octal = octal.replaceAll("6", "110");
        octal = octal.replaceAll("7", "111");

        char[] arr = octal.toCharArray();

        for (int i = 0; i < (octal.length() - 6); i++) {
            ret_val[i] = Character.getNumericValue(octal.charAt(i + 6));
        }
        return ret_val;
    }

    public int binaryArrayToInt(int[] binaryArray) {
        int value = 0;
        boolean isNegative = binaryArray[0] == 1; // Check if the number is negative

        // Convert the remaining bits to integer value, ignoring the sign bit
        for (int i = 1; i < binaryArray.length; i++) {
            value = (value << 1) + binaryArray[i];
        }

        // Handle negative numbers
        if (isNegative) {
            value = -value; // Make the value negative
        }

        return value;
    }



    // Right shift the register by count
    public String rightShift(String bitValue, int count, int ALvalue) {
        char sign;
        if (ALvalue == 0) {
            sign = bitValue.charAt(0);
        }else {
            sign = '0';
        }
        for (int i = 0; i < count; i++) {
            bitValue = sign + bitValue.substring(0, gpr0.getRegisterSize() - 1);
        }
        return bitValue;
    }

    // Left shift the register by count
    public String leftShift(String bitValue, int count) {
        for (int i = 0; i < count; i++) {
            bitValue = bitValue.substring(1, gpr0.getRegisterSize()) + "0";
        }
        return bitValue;
    }

    //Returns the memory value
    public int[] getMemoryValue(int row) {
        if (row < 6) {
            return new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        } else {
            return mem.getMemoryValue(row);
        }
    }

    public void setMemoryValue(int row, int[] value) {
        mem.setMemoryValue(row, value);
    }

    public void execute(String type) {
        if ("single".equals(type)) {
            int[] instructionAddress = getRegisterValue(RegisterType.ProgramCounter);

            int intAddress = binaryToInt(instructionAddress);
            setRegisterValue(RegisterType.InstructionRegister, getMemoryValue(intAddress));

            //Increment PC
            int[] pc = getRegisterValue(RegisterType.ProgramCounter);
            int transformedPC = binaryToInt(pc);
            transformedPC = transformedPC + 1;
            int[] latestPC = intToBinaryArrayShort(Integer.toBinaryString(transformedPC));
            setRegisterValue(RegisterType.ProgramCounter, latestPC);

            //Read and decode instruction
            int[] instructionBinary = getMemoryValue(intAddress);
            int[] opCode = Arrays.copyOfRange(instructionBinary, 0, 6);
            String instruction = decodeOPCode(opCode);
            int[] result = computeEffectiveAddr(instructionBinary);
            System.out.println("Instruction is : " + instruction + "Op code is : " + Arrays.toString(opCode));
            //We first get the instruction location and save it to IR from PC

            if(binaryArrayToInt(getRegisterValue(RegisterType.InstructionRegister)) == 0){
                int[] fault_code = new int[]{0, 0, 0, 1};
                mfr.setRegisterValue(fault_code);
                int[] msg = new int[]{1};
                hlt.setRegisterValue(msg);
            }

            else{
                executeInstruction(instruction, result);
            }
        }
    }

    public void load() {
        // Set MBR to the value located at MAR
        fetch();
    }

    public void loadPlus() {
        // Set MBR to the value located at MAR
        int[] currentMAR = getRegisterValue(RegisterType.MemoryAddressRegister);
        int transformedMAR = binaryToInt(currentMAR);
        fetch();
        //Increment MAR 1
        transformedMAR++;
        int[] newMAR = intToBinaryArrayShort(Integer.toBinaryString(transformedMAR));
        setRegisterValue(RegisterType.MemoryAddressRegister, newMAR);
    }

    public void store() {
        int[] currentMAR = getRegisterValue(RegisterType.MemoryAddressRegister);
        int transformedMAR = binaryToInt(currentMAR);
        int[] currentMBR = getRegisterValue(RegisterType.MemoryBufferRegister);
        int transformedMBR = binaryToInt(currentMBR);
        // Store MAR and MBR into Cache
        cache.addElement(transformedMAR, transformedMBR);
        // Set Memory(MAR) to MBR
        setMemoryValue(transformedMAR, currentMBR);
    }

    public void storePlus() {
        int[] currentMAR = getRegisterValue(RegisterType.MemoryAddressRegister);
        int transformedMAR = binaryToInt(currentMAR);
        int[] currentMBR = getRegisterValue(RegisterType.MemoryBufferRegister);
        int transformedMBR = binaryToInt(currentMBR);
        // Store MAR and MBR into Cache
        cache.addElement(transformedMAR, transformedMBR);
        // Set Memory(MAR) to MBR
        setMemoryValue(transformedMAR, currentMBR);

        //ADD 1
        transformedMAR++;
        int[] newMAR = intToBinaryArrayShort(Integer.toBinaryString(transformedMAR));
        setRegisterValue(RegisterType.MemoryAddressRegister, newMAR);
    }

    public void fetch() {  // Fetch MBR from cache
        int[] currentMAR = getRegisterValue(RegisterType.MemoryAddressRegister);
        int transformedMAR = binaryToInt(currentMAR);
        int currentMBR = cache.getElement(transformedMAR);
        int[] transformedMBR = intToBinaryArray(Integer.toString(currentMBR));  // Cache hit, Load from cache
        if (currentMBR == 0) {  // Cache miss, Load from memory
            transformedMBR = mem.getMemoryValue(transformedMAR);
        }
        mbr.setRegisterValue(transformedMBR);
    }

    private String decodeOPCode(int[] binaryOPCode) {
        String opCode = Arrays.toString(binaryOPCode);
        opCode = opCode.replace("[", "");
        opCode = opCode.replace("]", "");
        opCode = opCode.replace(",", "");
        opCode = opCode.replace(" ", "");
        System.out.println("OpCode is : " + opCode);
        Assembler assembler = new Assembler();
        for (Map.Entry<String, String> entry : assembler.getOpCodeMap().entrySet()) {
            if (opCode.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return "";
    }

    private void executeInstruction(String instruction, int[] effectiveAddress) {

        switch (instruction) {
            case "LDR":
                executeLDR(effectiveAddress);
                break;
            case "LDA":
                executeLDA(effectiveAddress);
                break;
            case "LDX":
                executeLDX(effectiveAddress);
                break;
            case "STR":
                executeSTR(effectiveAddress);
                break;
            case "STX":
                executeSTX(effectiveAddress);
                break;
            case "JZ":
                executeJZ(effectiveAddress);
                break;
            case "JNE":
                executeJNE(effectiveAddress);
                break;
            case "JCC":
                executeJCC(effectiveAddress);
                break;
            case "JMA":
                executeJMA(effectiveAddress);
                break;
            case "JSR":
                executeJSR(effectiveAddress);
                break;
            case "RFS":
                executeRFS(effectiveAddress);
                break;
            case "SOB":
                executeSOB(effectiveAddress);
                break;
            case "JRE":
                executeJGE(effectiveAddress);
                break;
            case "AMR":
                executeAMR(effectiveAddress);
                break;
            case "SMR":
                executeSMR(effectiveAddress);
                break;
            case "AIR":
                executeAIR(effectiveAddress);
                break;
            case "SIR":
                executeSIR(effectiveAddress);
                break;
            case "MLT":
                executeMLT(effectiveAddress);
                break;
            case "DVD":
                executeDVD(effectiveAddress);
                break;
            case "TRR":
                executeTRR(effectiveAddress);
                break;
            case "AND":
                executeAND(effectiveAddress);
                break;
            case "ORR":
                executeORR(effectiveAddress);
                break;
            case "NOT":
                executeNOT(effectiveAddress);
                break;
            case "SRC":
                executeSRC(effectiveAddress);
                break;
            case "RRC":
                executeRRC(effectiveAddress);
                break;
            case "IN":
                executeIN(effectiveAddress);
                break;
            case "OUT":
                executeOUT(effectiveAddress);
                break;
            case "SETCCE":
                executeSetCCE(effectiveAddress);
                break;
            default:
        }
    }


    public int[] computeEffectiveAddr(int[] instruction) {
        String strInstruction = Arrays.toString(instruction);
        strInstruction = strInstruction.replace("[", "");
        strInstruction = strInstruction.replace("]", "");
        strInstruction = strInstruction.replace(",", "");
        strInstruction = strInstruction.replace(" ", "");

        //Calculate I
        int I;
        if (strInstruction.charAt(10) == '0') {
            I = 0;
        } else {
            I = 1;
        }

        //Calculate R
        int R;
        if (strInstruction.charAt(6) == '0' && strInstruction.charAt(7) == '0') {
            R = 0;
        } else if (strInstruction.charAt(6) == '0' && strInstruction.charAt(7) == '1') {
            R = 1;
        } else if (strInstruction.charAt(6) == '1' && strInstruction.charAt(7) == '0') {
            R = 2;
        } else {
            R = 3;
        }

        //Calculate IX
        int IX;
        if (strInstruction.charAt(8) == '0' && strInstruction.charAt(9) == '0') {
            IX = 0;
        } else if (strInstruction.charAt(8) == '0' && strInstruction.charAt(9) == '1') {
            IX = 1;
        } else if (strInstruction.charAt(8) == '1' && strInstruction.charAt(9) == '0') {
            IX = 2;
        } else {
            IX = 3;
        }

        //Calculate logically (A/L = 1) or arithmetically (A/L = 0)
        int AL;
        if (strInstruction.charAt(8) == '0') {
            AL = 0;
        } else {
            AL = 1;
        }

        //Calculate shifted left (L/R = 1) or right (L/R = 0)
        int LR;
        if (strInstruction.charAt(9) == '0') {
            LR = 0;
        } else {
            LR = 1;
        }

        //Calculate Address Field
        int[] addressField = Arrays.copyOfRange(instruction, 11, 16);

        //Calculate EA using I,R,IX,and address
        int EA;
        int[] tmp_var;
        if (I == 0) {
            if (IX == 0) {
                //EA is the whole Address Field
                EA = binaryToInt(addressField);

            } else {
                //Get IX Register value
                int ix_value;
                if (IX == 1) {
                    ix_value = binaryToInt(getRegisterValue(RegisterType.IndexRegister1));
                } else if (IX == 2) {
                    ix_value = binaryToInt(getRegisterValue(RegisterType.IndexRegister2));
                } else {
                    ix_value = binaryToInt(getRegisterValue(RegisterType.IndexRegister2));
                }
                EA = binaryToInt(addressField) + ix_value;

            }
        } else {
            if (IX == 0) {
                //Get memory index from Address Field
                int tmp_var2 = binaryToInt(addressField);

                tmp_var = getMemoryValue(tmp_var2);

                //Get address value from earlier address
                tmp_var2 = binaryToInt(tmp_var);

                tmp_var = getMemoryValue(tmp_var2);
                EA = binaryToInt(tmp_var);

            } else {
                //Get Value in index register
                int IX_value;
                if (IX == 1) {
                    IX_value = binaryToInt(getRegisterValue(RegisterType.IndexRegister1));
                } else if (IX == 2) {
                    IX_value = binaryToInt(getRegisterValue(RegisterType.IndexRegister2));
                } else {
                    IX_value = binaryToInt(getRegisterValue(RegisterType.IndexRegister2));
                }

                int tmp_var2 = binaryToInt(addressField);
                //Get memory location of EA
                int tmp_var3 = tmp_var2 + IX_value;
                EA = binaryToInt(getMemoryValue(tmp_var3));
            }
        }
        int ret[] = {EA, I, R, IX, binaryToInt(addressField), AL, LR};
        return ret;
    }

    private void executeLDR(int[] effectiveAddress) {
        int EA = effectiveAddress[0];
        int R = effectiveAddress[2];
        setRegisterValue(RegisterType.MemoryAddressRegister, intToBinaryArrayShort(Integer.toBinaryString(EA)));
        switch (R) {
            case 0:
                //Set MBR
                setRegisterValue(RegisterType.MemoryBufferRegister, getMemoryValue(EA));
                //Set register value from MBR
                gpr0.setRegisterValue(mbr.getRegisterValue());
                break;
            case 1:
                //Set MBR to the value to be stored in register
                setRegisterValue(RegisterType.MemoryBufferRegister, getMemoryValue(EA));
                //Set register to value from MBR
                gpr1.setRegisterValue(mbr.getRegisterValue());
                break;
            case 2:
                //Set MBR to the value to be stored in register
                setRegisterValue(RegisterType.MemoryBufferRegister, getMemoryValue(EA));
                // Set register to value from MBR
                gpr2.setRegisterValue(mbr.getRegisterValue());
                break;
            default:
                //Set MBR to the value to be stored in register
                setRegisterValue(RegisterType.MemoryBufferRegister, getMemoryValue(EA));
                //Set register to value from MBR
                gpr3.setRegisterValue(mbr.getRegisterValue());
        }
        //Set MBR to value we just fetched
        setRegisterValue(RegisterType.MemoryBufferRegister, getMemoryValue(EA));
    }

    private void executeLDA(int[] effectiveAddress) {
        int EA = effectiveAddress[0];
        int R = effectiveAddress[2];
        setRegisterValue(RegisterType.MemoryAddressRegister, intToBinaryArrayShort(Integer.toBinaryString(EA)));
        int[] convertedValue = intToBinaryArray(Integer.toBinaryString(EA));
        switch (R) {
            case 0:
                gpr0.setRegisterValue(convertedValue);
                break;
            case 1:
                gpr1.setRegisterValue(convertedValue);
                break;
            case 2:
                gpr2.setRegisterValue(convertedValue);
                break;
            default:
                gpr3.setRegisterValue(convertedValue);
        }
    }

    private void executeSTR(int[] effectiveAddress) {
        int EA = effectiveAddress[0];
        int R = effectiveAddress[2];
        StringBuilder binaryBuilder = new StringBuilder();
        for (int i = 0; i < getMemoryValue(EA).length; i++) {
            binaryBuilder.append(getMemoryValue(EA)[i]);
        }
        if(GPRList.get(R).getDeviceInput() == 0) {
            setRegisterValue(RegisterType.MemoryAddressRegister, intToBinaryArrayShort(Integer.toBinaryString(EA)));
            switch (R) {
                case 0:
                    setRegisterValue(RegisterType.MemoryBufferRegister, gpr0.getRegisterValue());
                    setMemoryValue(Integer.parseInt(binaryBuilder.toString(),2), mbr.getRegisterValue());
                    break;
                case 1:
                    setRegisterValue(RegisterType.MemoryBufferRegister, gpr1.getRegisterValue());
                    setMemoryValue(Integer.parseInt(binaryBuilder.toString(),2), mbr.getRegisterValue());
                    break;
                case 2:
                    setRegisterValue(RegisterType.MemoryBufferRegister, gpr2.getRegisterValue());
                    setMemoryValue(Integer.parseInt(binaryBuilder.toString(),2), mbr.getRegisterValue());
                    break;
                default:
                    setRegisterValue(RegisterType.MemoryBufferRegister, gpr3.getRegisterValue());
                    setMemoryValue(Integer.parseInt(binaryBuilder.toString(),2), mbr.getRegisterValue());
            }
        }

        else{
            int value;
            List<Integer> asciiList = GPRList.get(R).getAsciiValue();
            for (int i = 0; i<asciiList.size(); i++){
                value = asciiValue.get(i);
                setRegisterValue(RegisterType.MemoryAddressRegister, intToBinaryArrayShort(Integer.toBinaryString(EA)));
                setRegisterValue(RegisterType.MemoryBufferRegister, intToBinaryArrayShort(Integer.toBinaryString(value)));
                store();
                EA += 1;
            }
            GPRList.get(R).setDeviceInput(0);
        }
    }
    private void executeSTX(int[] effectiveAddress) {
        int EA = effectiveAddress[0];
        int IX = effectiveAddress[3];
        setRegisterValue(RegisterType.MemoryAddressRegister, intToBinaryArrayShort(Integer.toBinaryString(EA)));
        switch (IX) {
            case 1:
                //Set MBR
                setRegisterValue(RegisterType.MemoryBufferRegister, x1.getRegisterValue());
                //Store value from MBR
                setMemoryValue(EA, mbr.getRegisterValue());
                break;
            case 2:
                //Set MBR
                setRegisterValue(RegisterType.MemoryBufferRegister, x2.getRegisterValue());
                // Store value from MBR
                setMemoryValue(EA, mbr.getRegisterValue());
                break;
            case 3:
                //Set MBR
                setRegisterValue(RegisterType.MemoryBufferRegister, x3.getRegisterValue());
                //Store value from MBR
                setMemoryValue(EA, mbr.getRegisterValue());
                break;
            default:
        }
    }

    private void executeLDX(int[] effectiveAddress) {
        int EA = effectiveAddress[0];
        int IX = effectiveAddress[3];
        int addressField = effectiveAddress[4];
        setRegisterValue(RegisterType.MemoryAddressRegister, getMemoryValue(EA));
        switch (IX) {
            case 1:
                //Set MBR from memory
                setRegisterValue(RegisterType.MemoryBufferRegister, getMemoryValue(addressField));
                //Load MBR into index register
                x1.setRegisterValue(mbr.getRegisterValue());
                break;
            case 2:
                //Set MBR from memory
                setRegisterValue(RegisterType.MemoryBufferRegister, getMemoryValue(addressField));
                //Load MBR into index register
                x2.setRegisterValue(mbr.getRegisterValue());
                break;
            case 3:
                //Set MBR from memory
                setRegisterValue(RegisterType.MemoryBufferRegister, getMemoryValue(addressField));
                //Load MBR into index register
                x3.setRegisterValue(mbr.getRegisterValue());
                break;
            default:
        }

    }

    private void executeRFS(int[] effectiveAddress) {
        int EA = effectiveAddress[0];
        setRegisterValue(RegisterType.MemoryAddressRegister, intToBinaryArrayShort(Integer.toBinaryString(EA)));
        gpr0.setRegisterValue(ir.getRegisterValue());
        pc.setRegisterValue(gpr3.getRegisterValue());
    }

    private void executeJZ(int[] effectiveAddress) {
        int EA = effectiveAddress[0];

        // Check if the E bit cc(4) of the condition code is 1
        if (getRegisterValue(RegisterType.ConditionCode)[3] == 1) {
            // Set the Program Counter (PC) to the specified address
            setRegisterValue(RegisterType.ProgramCounter, intToBinaryArrayShort(Integer.toBinaryString(EA)));
        } else {
            // If the E bit is not set, increment the Program Counter (PC) by 1
            incrementPCByOne();
        }
    }

    private void executeJNE(int[] effectiveAddress) {
        int EA = effectiveAddress[0];

        // Check if the E bit cc(3) of the condition code is 0
        if (getRegisterValue(RegisterType.ConditionCode)[3] == 0) {
            // Set the Program Counter (PC) to the specified address
            StringBuilder binaryBuilder = new StringBuilder();
            for (int i = 0; i < getMemoryValue(EA).length; i++) {
                binaryBuilder.append(getMemoryValue(EA)[i]);
            }
            setRegisterValue(RegisterType.ProgramCounter,intToBinaryArrayShort(binaryBuilder.toString()));
        } else {
            // If the E bit is not set, increment the Program Counter (PC) by 1
            incrementPCByOne();
        }
    }

    private void executeJCC(int[] effectiveAddress) {
        int EA = effectiveAddress[0];
        int ccBit = effectiveAddress[2];  // cc replaces r for this instruction

        // Check if the cc bit cc(cc) of the condition code is 1
        if (getRegisterValue(RegisterType.ConditionCode)[ccBit] == 1) {
            // Set the Program Counter (PC) to the specified address
            setRegisterValue(RegisterType.ProgramCounter, intToBinaryArrayShort(Integer.toBinaryString(EA)));
        } else {
            // If the E bit is not set, increment the Program Counter (PC) by 1
            incrementPCByOne();
        }
    }

    private void executeJMA(int[] effectiveAddress) {
        int EA = effectiveAddress[0];

        // Unconditional Jump
        // Set the Program Counter (PC) to the specified address
        setRegisterValue(RegisterType.ProgramCounter, getRegisterValue(RegisterType.MemoryAddressRegister));
    }

    private void executeJSR(int[] effectiveAddress) {
        int EA = effectiveAddress[0];
        int[] result = intToBinaryArray(Integer.toString(binaryArrayToInt(getRegisterValue(RegisterType.ProgramCounter)) + 1));
        GPRList.get(3).setRegisterValue(result);
        // Set the Program Counter (PC) to the specified address
        setRegisterValue(RegisterType.ProgramCounter, intToBinaryArrayShort(Integer.toBinaryString(EA)));
    }

    private void executeSOB(int[] effectiveAddress) {
        int EA = effectiveAddress[0];
        int R = effectiveAddress[2];

        int RValue = binaryArrayToInt(GPRList.get(R).getRegisterValue());
        int x = RValue - 1;
        int[] result = intToBinaryArray(Integer.toString(x));
        GPRList.get(R).setRegisterValue(result);

        if (x > 0) {
            // Set the Program Counter (PC) to the specified address
            setRegisterValue(RegisterType.ProgramCounter, intToBinaryArrayShort(Integer.toBinaryString(EA)));
        } else {
            // If the c(r) > 0, increment the Program Counter (PC) by 1
            incrementPCByOne();
        }
    }

    private void executeJGE(int[] effectiveAddress) {
        int EA = effectiveAddress[0];
        int R = effectiveAddress[2];

        int RValue = binaryArrayToInt(GPRList.get(R).getRegisterValue());

        if (RValue >= 0) {
            // Set the Program Counter (PC) to the specified address
            setRegisterValue(RegisterType.ProgramCounter, intToBinaryArrayShort(Integer.toBinaryString(EA)));
        } else {
            // If the c(r) > 0, increment the Program Counter (PC) by 1
            incrementPCByOne();
        }
    }

    private void executeAMR(int[] effectiveAddress) {
        int EA = effectiveAddress[0];
        int R = effectiveAddress[2];
        setRegisterValue(RegisterType.MemoryAddressRegister, intToBinaryArrayShort(Integer.toBinaryString(EA)));

        //Get value from Instruction Register
//        int irGetGPR1 = ir.getRegisterValue()[6];
//        int irGetGPR2 = ir.getRegisterValue()[7];
//        String irGetGPRStr = String.valueOf(irGetGPR1) + String.valueOf(irGetGPR2);
//        int irGetGPR = Integer.parseInt(irGetGPRStr, 2);
//        int[] GPRValue = GPRList.get(irGetGPR).getRegisterValue();

        int RValue = binaryArrayToInt(GPRList.get(R).getRegisterValue());
        int x = RValue + EA;

        String binaryString = Integer.toBinaryString(x);
        binaryString = String.format("%16s", binaryString).replace(' ', '0');
        int[] result = intToBinaryArray(binaryString);
        int[] currentCCValue = cc.getRegisterValue();

        if(x > 32767) {
            currentCCValue[0] = 1;   // overflow, condition code cc(1) -> 1
            cc.setRegisterValue(currentCCValue);
        }
        else {
            GPRList.get(R).setRegisterValue(result);
        }
    }

    private void executeSMR(int[] effectiveAddress) {
        int EA = effectiveAddress[0];
        int R = effectiveAddress[2];
        setRegisterValue(RegisterType.MemoryAddressRegister, intToBinaryArrayShort(Integer.toBinaryString(EA)));

        int RValue = binaryArrayToInt(GPRList.get(R).getRegisterValue());
        int x = RValue - EA;
        int[] result = intToBinaryArray(Integer.toString(x));
        int[] currentCCValue = cc.getRegisterValue();

        if(x < -32768) {
            currentCCValue[1] = 1;      // underflow, condition code cc(2) -> 1
            cc.setRegisterValue(currentCCValue);
        }
        else {
            GPRList.get(R).setRegisterValue(result);
        }
    }

    private void executeAIR(int[] effectiveAddress) {
        int EA = effectiveAddress[0];
        int R = effectiveAddress[2];
        setRegisterValue(RegisterType.MemoryAddressRegister, intToBinaryArrayShort(Integer.toBinaryString(EA)));

        int RValue = binaryArrayToInt(GPRList.get(R).getRegisterValue());
        int[] irValue = getRegisterValue(RegisterType.InstructionRegister);
        String addr = Arrays.toString(irValue).replaceAll("[^0-9]", "").substring(11);
        int immed = Integer.parseInt(addr,2);
        String binaryString = Integer.toBinaryString(RValue + immed);
        binaryString = String.format("%16s", binaryString).replace(' ', '0');
        int[] result = intToBinaryArray(binaryString);
        int[] currentCCValue = cc.getRegisterValue();
        if(immed != 0) {
            if(RValue + immed > 32767) {
                currentCCValue[0] = 1;      // overflow, condition code cc(1) -> 1
                cc.setRegisterValue(currentCCValue);
            }

            else{
                GPRList.get(R).setRegisterValue(result);
            }
        }
    }



    private void executeSIR(int[] effectiveAddress) {
        int EA = effectiveAddress[0];
        int R = effectiveAddress[2];
        setRegisterValue(RegisterType.MemoryAddressRegister, intToBinaryArrayShort(Integer.toBinaryString(EA)));

        int RValue = binaryArrayToInt(GPRList.get(R).getRegisterValue());
        int[] irValue = getRegisterValue(RegisterType.InstructionRegister);
        String addr = Arrays.toString(irValue).replaceAll("[^0-9]", "").substring(11);
        int immed = Integer.parseInt(addr,2);

        int x = RValue - immed;
        int[] result = intToBinaryArray(Integer.toString(x));
        int[] currentCCValue = cc.getRegisterValue();
        if(immed != 0) {
            if (x < -32768) {       // underflow, condition code cc(2) -> 1
                currentCCValue[1] = 1;
                cc.setRegisterValue(currentCCValue);
            }else {
                if (x == 0) {  //if c(r) = 0, loads r1 with –(Immed)
                    GPRList.get(R).setRegisterValue(result);
                }
            }
        }
    }

    private void executeMLT(int[] effectiveAddress) {
        int rx = effectiveAddress[2];
        int ry = effectiveAddress[3];

        int[] contentRx = GPRList.get(rx).getRegisterValue();
        int[] contentRy = GPRList.get(ry).getRegisterValue();
        BigInteger bigContentRx = BigInteger.valueOf(binaryArrayToInt(contentRx));
        BigInteger bigContentRy = BigInteger.valueOf(binaryArrayToInt(contentRy));
        BigInteger product = bigContentRx.multiply(bigContentRy);
        int[] currentCCValue = cc.getRegisterValue();

        if (product.abs().compareTo(maxINT) > 0) {    // product > maxINT
            // OVERFLOW, set CC1 to 1
            currentCCValue[0] = 1;
            cc.setRegisterValue(currentCCValue);
        }else if((rx == 0 || rx == 2) && (ry == 0 || ry == 2)) {
            int[] result = intToBinaryArray(Integer.toString(product.intValue()));
            int[] rxRes = new int[16];
            int[] rx1Res = new int[16];

            // Copy the high-order 8 bits to array x
            System.arraycopy(result, 0, rxRes, 0, 8);
            // Copy the low-order 8 bits to array y
            System.arraycopy(result, 8, rx1Res, 0, 8);

            GPRList.get(rx).setRegisterValue(rxRes);
            GPRList.get(rx + 1).setRegisterValue(rx1Res);
        }else {
            System.err.println("MLT instruction rx and ry must be 0 or 2.");
        }
    }

    private void executeDVD(int[] effectiveAddress) {
        int rx = effectiveAddress[2];
        int ry = effectiveAddress[3];

        int[] contentRx = GPRList.get(rx).getRegisterValue();
        int[] contentRy = GPRList.get(ry).getRegisterValue();
        int rxValue = binaryArrayToInt(contentRx);
        int ryValue = binaryArrayToInt(contentRy);
        int[] currentCCValue = cc.getRegisterValue();

        if (ryValue == 0) {
            // DIVZERO, set CC3 to 1
            currentCCValue[2] = 1;
            cc.setRegisterValue(currentCCValue);
        }else if((rx == 0 || rx == 2) && (ry == 0 || ry == 2)) {
            int[] rxRes = intToBinaryArray(Integer.toString(rxValue / ryValue));  //rx contains the quotient
            int[] rx1Res = intToBinaryArray(Integer.toString(rxValue % ryValue));  //rx+1 contains the remainder
            GPRList.get(rx).setRegisterValue(rxRes);
            GPRList.get(rx + 1).setRegisterValue(rx1Res);
        }else {
            System.err.println("DVD instruction rx and ry must be 0 or 2.");
        }
    }

    private void executeTRR(int[] effectiveAddress) {
        int rx = effectiveAddress[2];
        int ry = effectiveAddress[3];

        int[] contentRx = GPRList.get(rx).getRegisterValue();
        int[] contentRy = GPRList.get(ry).getRegisterValue();
        int[] currentValue = cc.getRegisterValue();

        // Compare each element of the arrays
        boolean allSame = true;
        for (int i = 0; i < contentRx.length; i++) {
            if (contentRx[i] != contentRy[i]) {
                allSame = false;
                break; // No need to continue checking if any element is different
            }
        }

        if (allSame) {
            // equal-or-not cc(4) -> 1
            currentValue[3] = 1;
        } else {
            // equal-or-not cc(4) -> 0
            currentValue[3] = 0;
        }
        cc.setRegisterValue(currentValue);
    }

    private void executeAND(int[] effectiveAddress) {
        int rx = effectiveAddress[2];
        int ry = effectiveAddress[3];

        int[] contentRx = GPRList.get(rx).getRegisterValue();
        int[] contentRy = GPRList.get(ry).getRegisterValue();

        // Perform bitwise AND operation element-wise
        int[] result = new int[16];
        for (int i = 0; i < result.length; i++) {
            result[i] = contentRx[i] & contentRy[i];
        }

        GPRList.get(rx).setRegisterValue(result);
    }

    private void executeORR(int[] effectiveAddress) {
        int rx = effectiveAddress[2];
        int ry = effectiveAddress[3];

        int[] contentRx = GPRList.get(rx).getRegisterValue();
        int[] contentRy = GPRList.get(ry).getRegisterValue();

        // Perform bitwise AND operation element-wise
        int[] result = new int[16];
        for (int i = 0; i < result.length; i++) {
            result[i] = contentRx[i] | contentRy[i];
        }

        GPRList.get(rx).setRegisterValue(result);
    }

    private void executeNOT(int[] effectiveAddress) {
        int rx = effectiveAddress[2];
        int[] contentRx = GPRList.get(rx).getRegisterValue();

        // Perform bitwise NOT operation element-wise
        int[] result = new int[16];
        for (int i = 0; i < result.length; i++) {
            result[i] = ~contentRx[i];
        }

        GPRList.get(rx).setRegisterValue(result);
    }

    private void executeSRC(int[] effectiveAddress) {
        int R = effectiveAddress[2];
        String contentR = GPRList.get(R).getStringValue();
        int count = effectiveAddress[4];
        int AL = effectiveAddress[5];
        int LR = effectiveAddress[6];

        String result = "";
        if (LR == 0) {	//right shift
            result = rightShift(contentR, count, AL);
        }else {	//left shift
            result = leftShift(contentR, count);
        }

        // Transform result from String to int[]
        int[] resultArr = new int[result.length()];
        for (int i = 0; i < result.length(); i++) {
            resultArr[i] = Character.getNumericValue(result.charAt(i));
        }

        GPRList.get(R).setRegisterValue(resultArr);
    }

    private void executeRRC(int[] effectiveAddress) {
        int R = effectiveAddress[2];
        String contentR = GPRList.get(R).getStringValue();
        int count = effectiveAddress[4];
        int LR = effectiveAddress[6];
        int rSize = GPRList.get(R).getRegisterSize();

        String result = "";
        if (LR == 0) {	//right rotate
            for (int i = 0; i < count; i++) {
                result = contentR.charAt(rSize - 1) + contentR.substring(0, rSize - 1);
            }
        }else {	//left rotate
            for (int i = 0; i < count; i++) {
                result = contentR.substring(1) + contentR.charAt(0);
            }
        }

        // Transform result from String to int[]
        int[] resultArr = new int[result.length()];
        for (int i = 0; i < result.length(); i++) {
            resultArr[i] = Character.getNumericValue(result.charAt(i));
        }

        GPRList.get(R).setRegisterValue(resultArr);
    }

    private void executeIN(int[] effectiveAddress) {
        System.out.println("IN instruction start");
        int R = effectiveAddress[2];
        int devID = Integer.parseInt(ir.getValue().substring(11), 2);
        if (devID == -1) {
            System.out.println("IN instruction finished without an action");
        } else {
            if (devID == 0) {
                CompletableFuture<String> future = new CompletableFuture<>();
                setExecuteInstructions(false); // Pause CPU execution
                Platform.runLater(() -> {
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Input Dialog");
                    dialog.setHeaderText("Please type in your input for keyboard.");
                    Optional<String> result = dialog.showAndWait();
                    result.ifPresent(future::complete);
                });

                future.thenAcceptAsync(value -> {
                    if (value.matches("\\d+")) {
                        int[] binaryArray = intToBinaryArray(value);
                        GPRList.get(R).setRegisterValue(binaryArray);
                        System.out.println("Value is : " + value);
                    } else {
                        System.out.println("Value with characters : " + value);
                        GPRList.get(R).setDeviceInput(1);
                        asciiValue.clear();
                        for (int i = 0; i < value.length(); i++) {
                            asciiValue.add((int) value.charAt(i));
                        }
                        int[] binaryArray = value.isEmpty() ? new int[0] : intToBinaryArray(String.valueOf(value.charAt(0)));
                        GPRList.get(R).setAsciiValue(asciiValue);
                        GPRList.get(R).setRegisterValue(binaryArray);
                    }
                    setExecuteInstructions(true);
                });
            }
            System.out.println("IN instruction end.");
        }
    }


    private void executeOUT(int[] effectiveAddress) {
        int R = effectiveAddress[2];
        String irValue = Arrays.toString(getRegisterValue(RegisterType.InstructionRegister)).replaceAll("[^0-9]", "");
        int devID = Integer.parseInt(irValue.substring(11), 2);
        ConsoleController consoleController = new ConsoleController();
        if(devID != 0) {
            int RValue = binaryArrayToInt(GPRList.get(R).getRegisterValue());
             if(devID == 1) {
                if (RValue < 10) {
                    System.out.println(RValue);
                    //consoleController.appendToPrinterTextArea(String.valueOf(RValue));  // Output the RValue to the printer console
                } else {
                    System.out.println((char) RValue);
                    //consoleController.appendToPrinterTextArea(String.valueOf((char) RValue));  // Output the RValue to the printer console
                }
            }
            System.out.println("OUT instruction end");
        }
        else{
            System.out.println("OUT instruction end without an action");
        }
    }
    private void executeSetCCE(int[] effectiveAddress) {
        //If c(r) = 0, the E bit of the condition code is set to 1, else the E bit of the condition code is set to 0
        int R = effectiveAddress[2];
        int[] currentCCValue = cc.getRegisterValue();
        if(R == 0) {
            currentCCValue[3] = 1;
        }

        else {
            currentCCValue[3] = 0;
        }
        cc.setRegisterValue(currentCCValue);
    }

    private void incrementPCByOne() {
        int currentPC = binaryArrayToInt(getRegisterValue(RegisterType.ProgramCounter));
        setRegisterValue(RegisterType.ProgramCounter, intToBinaryArrayShort(Integer.toBinaryString(currentPC + 1)));
    }

    public void setExecuteInstructions(boolean execute) {
        this.executeInstructions = execute;
    }

    public boolean getExecuteInstructions() {
        return this.executeInstructions;
    }

}