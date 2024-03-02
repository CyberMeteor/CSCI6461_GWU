package gwu.csci6461.team4;

import gwu.csci6461.team4.assembler.Assembler;
import gwu.csci6461.team4.registers.Register;
import gwu.csci6461.team4.registers.RegisterType;

import java.util.Arrays;
import java.util.Map;

public class CPU {

    Register mar = new Register(12);
    Register mbr = new Register(16);
    Register pc = new Register(12);
    Register x1 = new Register(16);
    Register x2 = new Register(16);
    Register x3 = new Register(16);
    Register gpr0 = new Register(16);
    Register gpr1 = new Register(16);
    Register gpr2 = new Register(16);
    Register gpr3 = new Register(16);
    Register cc = new Register(4);
    Register ir = new Register(16);
    Register mfr = new Register(4);
    Register hlt = new Register(1);

    Memory mem = new Memory();

    public void setRegisterValue(RegisterType registerType, int[] value) {
        if (registerType.getType().equals("MAR")) {
            mar.setRegisterValue(value);
        } else if (registerType.getType().equals("MBR")) {
            mbr.setRegisterValue(value);
        } else if (registerType.getType().equals("PC")) {
            if (binaryToInt(value) < 10) {
                int[] tempVal = {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0};
                pc.setRegisterValue(tempVal);
            } else {
                pc.setRegisterValue(value);
            }
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
        int[] registerValue;
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
        } else {
            registerValue = hlt.getRegisterValue();
        }
        return registerValue;
    }

    public int binaryToInt(int[] binary) {
        int returnVal;
        String binaryVal = Arrays.toString(binary);
        binaryVal = binaryVal.replace("[", "");
        binaryVal = binaryVal.replace("]", "");
        binaryVal = binaryVal.replace(",", "");
        binaryVal = binaryVal.replace(" ", "");
        returnVal = Integer.parseInt(binaryVal, 2);
        return returnVal;
    }

    //Converts a binary int value to binary array value specifically for the PC
    public int[] intToBinaryArrayShort(String value){
        int[] returnValue = new int[12];

        char[] arr = value.toCharArray();


        for (int i = 0; i < 12; i++) {
            if (i < 12 - arr.length){
                returnValue[i] = 0;
            }else{
                returnValue[i] = Character.getNumericValue(value.charAt(i-(12 - arr.length)));
            }

        }
        return returnValue;
    }

    public int octalToInt(String octal){
        int ret_val = Integer.parseInt(octal, 8);
        return ret_val;
    }

    // For MBR
    public int[] octalToBinaryArray(String octal){
        int[] ret_val = new int[16];
        octal = octal.replaceAll("0", "000");
        octal = octal.replaceAll("1", "001");
        octal = octal.replaceAll("2", "010");
        octal = octal.replaceAll("3", "011");
        octal = octal.replaceAll("4", "100");
        octal = octal.replaceAll("5", "101");
        octal = octal.replaceAll("6", "110");
        octal = octal.replaceAll("7", "111");

        char[] arr = octal.toCharArray();

        for (int i = 0; i < (octal.length()-2); i++) {
            ret_val[i] = Character.getNumericValue(octal.charAt(i+2));
        }
        return ret_val;
    }

    // For MAR
    public int[] octalToBinaryArrayShort(String octal){
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

        for (int i = 0; i < (octal.length()-6); i++) {
            ret_val[i] = Character.getNumericValue(octal.charAt(i+6));
        }
        return ret_val;
    }

    //Returns the memory value
    public int[] getMemoryValue(int row){
        if (row < 6){
            return new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

        }else{
            return mem.getMemoryValue(row);
        }
    }

    public void setMemoryValue(int row, int[] value){
        if (row < 6){
            int[] fault_code = new int[]{0,0,0,1};
            mfr.setRegisterValue(fault_code);
            int [] msg = new int[]{1};
            hlt.setRegisterValue(msg);
        }else{
            mem.setMemoryValue(row, value);
        }
    }

    public void execute () {
        //We first get the instruction location and save it to IR from PC
        int[] instructionAddress = getRegisterValue(RegisterType.ProgramCounter);
        int intAddress = binaryToInt(instructionAddress);
        setRegisterValue(RegisterType.InstructionRegister,getMemoryValue(intAddress));

        //Increment PC
        int[] pc = getRegisterValue(RegisterType.ProgramCounter);
        int transformedPC = binaryToInt(pc);
        transformedPC = transformedPC+1;
        int[] latestPC = intToBinaryArrayShort(Integer.toBinaryString(transformedPC));
        setRegisterValue(RegisterType.ProgramCounter, latestPC);

        //Read and decode instruction
        int[]instructionBinary = getMemoryValue(intAddress);
        int[] opCode = Arrays.copyOfRange(instructionBinary, 0, 6);
        String instruction = decodeOPCode(opCode);
        int[] result = computeEffectiveAddr(instructionBinary);
        executeInstruction(instruction,result);

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

        if(instruction.equals("LDR")){
            int EA = effectiveAddress[0];
            int R= effectiveAddress[2];
            setRegisterValue(RegisterType.MemoryAddressRegister, intToBinaryArrayShort(Integer.toBinaryString(EA)));
            switch(R) {
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
    }

    public int[] computeEffectiveAddr(int[] instruction){
        String strInstruction = Arrays.toString(instruction);
        strInstruction = strInstruction.replace("[", "");
        strInstruction = strInstruction.replace("]", "");
        strInstruction = strInstruction.replace(",", "");
        strInstruction = strInstruction.replace(" ", "");

        //Calculate I
        int I;
        if (strInstruction.charAt(10) == '0'){
            I = 0;
        }else{
            I = 1;
        }

        //Calculate R
        int R;
        if(strInstruction.charAt(6) == '0' && strInstruction.charAt(7) == '0'){
            R = 0;
        }else if(strInstruction.charAt(6) == '0' && strInstruction.charAt(7) == '1'){
            R = 1;
        }
        else if(strInstruction.charAt(6) == '1' && strInstruction.charAt(7) == '0'){
            R = 2;
        }else{
            R = 3;
        }

        //Calculate IX
        int IX;
        if(strInstruction.charAt(8) == '0' && strInstruction.charAt(9) == '0'){
            IX = 0;
        }else if(strInstruction.charAt(8) == '0' && strInstruction.charAt(9) == '1'){
            IX = 1;
        }
        else if(strInstruction.charAt(8) == '1' && strInstruction.charAt(9) == '0'){
            IX = 2;
        }else{
            IX = 3;
        }

        //Calculate Address Field
        int[] addressField = Arrays.copyOfRange(instruction, 11, 16);

        //Calculate EA using I,R,IX,and address
        int EA;
        int[] tmp_var;
        if (I == 0){
            if (IX == 0){
                //EA is the whole Address Field
                EA = binaryToInt(addressField);

            }else {
                //Get IX Register value
                int ix_value;
                if (IX == 1){
                    ix_value = binaryToInt(getRegisterValue(RegisterType.IndexRegister1));
                }else if (IX == 2){
                    ix_value = binaryToInt(getRegisterValue(RegisterType.IndexRegister2));
                }else{
                    ix_value = binaryToInt(getRegisterValue(RegisterType.IndexRegister2));
                }
                EA = binaryToInt(addressField) + ix_value;

            }
        }else{
            if (IX == 0) {
                //Get memory index from Address Field
                int tmp_var2 = binaryToInt(addressField);

                tmp_var = getMemoryValue(tmp_var2);

                //Get address value from earlier address
                tmp_var2 = binaryToInt(tmp_var);

                tmp_var = getMemoryValue(tmp_var2);
                EA = binaryToInt(tmp_var);

            }else{
                //Get Value in index register
                int IX_value;
                if (IX == 1){
                    IX_value = binaryToInt(getRegisterValue(RegisterType.IndexRegister1));
                }else if (IX == 2){
                    IX_value = binaryToInt(getRegisterValue(RegisterType.IndexRegister2));
                }else{
                    IX_value = binaryToInt(getRegisterValue(RegisterType.IndexRegister2));
                }

                int tmp_var2 = binaryToInt(addressField);
                //Get memory location of EA
                int tmp_var3 = tmp_var2 + IX_value;
                EA = binaryToInt(getMemoryValue(tmp_var3));
            }
        }
        int ret[] = {EA,I,R,IX, binaryToInt(addressField)};
        return ret;
    }



}