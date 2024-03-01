package gwu.csci6461.team4;

import gwu.csci6461.team4.registers.Register;
import gwu.csci6461.team4.registers.RegisterType;

import java.util.Arrays;

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
}