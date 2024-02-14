package gwu.csci6461.team4.assembler;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

//Class Assembler
public class Assembler {

    private String encodedLocation;
    private Map<String , String> locToInstructionMap;
    public Assembler() {
        encodedLocation = "";
        locToInstructionMap = new LinkedHashMap<>();
    }

    public void assemble(String opCode, String remaining, String finalLocation) {
        if(opCode.equals("LOC")){
            if(!remaining.equals(finalLocation)) {
                encodedLocation = decimalToOctal(remaining);
                // first "LOC"
                locToInstructionMap.put("      ", "      ");
            } else {
                encodedLocation = decimalToOctal(finalLocation);
                // last "LOC"
                locToInstructionMap.put("       ", "     ");
            }
        }

        else{
            String encodedOpCode = encodeOpCode(opCode,remaining);
            String encodedRemaining = encodeRemaining(opCode, remaining, finalLocation);
            String encodedInstruction = binToOctal(encodedOpCode + encodedRemaining);
            encodedInstruction = String.format("%06d", Integer.parseInt(encodedInstruction));
            locToInstructionMap.put(encodedLocation, encodedInstruction);
            int decimalLocation = Integer.parseInt(encodedLocation,8);
            decimalLocation++;
            encodedLocation = decimalToOctal(String.valueOf(decimalLocation));
        }
        //Putting zeros at the beginning if the length is smaller than 6
        encodedLocation = String.format("%06d", Integer.parseInt(encodedLocation));
    }

    // Define the dictionary of Opcode using HasMap
    private String encodeOpCode(String opCode, String remaining) {
        HashMap<String, String> opCodeMap = new HashMap<>();
        opCodeMap.put("HLT", "000000");
        opCodeMap.put("LDR", "000001");
        opCodeMap.put("STR", "000010");
        opCodeMap.put("LDA", "000011");
        opCodeMap.put("LDX", "000100");
        opCodeMap.put("STX", "000101");
        opCodeMap.put("JZ", "000110");
        opCodeMap.put("JNE", "000111");
        opCodeMap.put("JCC", "001000");
        opCodeMap.put("JMA", "001001");
        opCodeMap.put("JSR", "001010");
        opCodeMap.put("RFS", "001011");
        opCodeMap.put("SOB", "001100");
        opCodeMap.put("JGE", "001101");
        opCodeMap.put("AMR", "001110");
        opCodeMap.put("SMR", "001111");
        opCodeMap.put("AIR", "010000");
        opCodeMap.put("SIR", "010001");
        opCodeMap.put("MLT", "010010");
        opCodeMap.put("DVD", "010011");
        opCodeMap.put("TRR", "010100");
        opCodeMap.put("AND", "010101");
        opCodeMap.put("ORR", "010110");
        opCodeMap.put("NOT", "010111");
        opCodeMap.put("SRC", "011000");
        opCodeMap.put("RRC", "011001");
        opCodeMap.put("IN", "011010");
        opCodeMap.put("OUT", "011011");
        opCodeMap.put("CHK", "011100");
        opCodeMap.put("FADD", "011101");
        opCodeMap.put("FSUB", "011110");
        opCodeMap.put("VADD", "011111");
        opCodeMap.put("VSUB", "100000");
        opCodeMap.put("CNVRT", "100001");
        opCodeMap.put("LDFR", "100010");
        opCodeMap.put("STFR", "100011");
        opCodeMap.put("SETCCE", "100100");
        opCodeMap.put("TRAP", "100101");
        opCodeMap.put("LOC", "");
        return opCodeMap.getOrDefault(opCode,"000000");
    }

    private String encodeData(String data){
      return decimalToBinary(data);
    }

    private String encodeDevice(String device){
        return decimalToBinary(device);
    }

    // Encode register
    private String encodeRegister(String register) {
        int reg = Integer.parseInt(register);
        if (reg == 0) {
            return "00";
        } else if (reg == 1) {
            return "01";
        } else if (reg == 2) {
            return "10";
        } else if (reg == 3) {
            return "11";
        } else {
            return "";
        }
    }

    //Encode Index Register
    private String encodeIndexRegister(String indexRegister) {
        int ix = Integer.parseInt(indexRegister);
        if (ix == 0) {
            return "00";
        } else if (ix == 1) {
            return "01";
        } else if (ix == 2) {
            return "10";
        } else if (ix == 3) {
            return "11";
        } else {
            return "";
        }
    }

    //Encode Indirect Addressing 
    private String encodeIndirectAddressing(String indirectAddress) {
        int i = Integer.parseInt(indirectAddress);
        if (i == 0) {
            return "0";
        } else if (i == 1) {
            return "1";
        } else {
            return "";
        }
    }

    // Encode Address
    private String encodeAddress(String address) {
        String binaryAddress = decimalToBinary(address);
        if (binaryAddress.length() == 5) {
            return binaryAddress;
        } else if (binaryAddress.length() > 5) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            while (sb.length() < 5 - binaryAddress.length()) {
                sb.append('0');
            }
            sb.append(binaryAddress);
            return sb.toString();
        }
    }

    // Decimal to Binary Conversion
    private String decimalToBinary(String decimalAddress) {
        if (decimalAddress == null) {
            decimalAddress = "0";
        }
        return new BigInteger(decimalAddress, 10).toString(2);
    }

    //Decimal to Octal conversion
    private String decimalToOctal(String decimalAddress) {
        if (decimalAddress == null) {
            decimalAddress = "0";
        }
        return new BigInteger(decimalAddress, 10).toString(8);
    }

    //TODO CHANGE THE NAME OF THE FUNCTION FOR LR AND AL
    private String encode(String st) {
        return decimalToBinary(st);
    }

    private String encodeCount(String count) {
        String cnt = decimalToBinary(count);
        switch (cnt.length()) {
            case 1:
                return "000" + cnt;
            case 2:
                return "00" + cnt;
            case 3:
                return "0" + cnt;
            default:
                return cnt;
        }
    }

    private String encodeTRP(String str) {
        int trp = Integer.parseInt(str);
        if (trp == 0) {
            return "00";
        } else if (trp == 1) {
            return "01";
        } else if (trp == 2) {
            return "10";
        } else {
            return "";
        }
    }

    private String encodeRemaining(String opCode, String remaining, String finalLocation) {
        StringBuilder remainingBuilder = new StringBuilder();
        String[] splitted = remaining.split(",");

        if (opCode.equals("LDR")) {
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append(encodeIndexRegister(splitted[1]));
            if (splitted.length == 4) {
                remainingBuilder.append(encodeIndirectAddressing(splitted[3]));
                remainingBuilder.append(encodeAddress(splitted[2]));
            } else {
                remainingBuilder.append("0");
                remainingBuilder.append(encodeAddress(splitted[2]));
            }
        } else if (opCode.equals("LDA")) {
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append(encodeIndexRegister(splitted[1]));
            if (splitted.length == 4) {
                remainingBuilder.append(encodeIndirectAddressing(splitted[2]));
                remainingBuilder.append(encodeAddress(splitted[3]));
            } else {
                remainingBuilder.append("0");
                remainingBuilder.append(encodeAddress(splitted[2]));
            }
        } else if (opCode.equals("LDX")) {
            remainingBuilder.append("00");
            remainingBuilder.append(encodeIndexRegister(splitted[0]));
            if (splitted.length == 3) {
                remainingBuilder.append(encodeIndirectAddressing(splitted[1]));
                remainingBuilder.append(encodeAddress(splitted[2]));
            } else {
                remainingBuilder.append("0");
                remainingBuilder.append(encodeAddress(splitted[1]));
            }
        } else if (opCode.equals("STR")) {
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append(encodeIndexRegister(splitted[1]));
            if (splitted.length == 4) {
                remainingBuilder.append(encodeIndirectAddressing(splitted[2]));
                remainingBuilder.append(encodeAddress(splitted[3]));
            } else {
                remainingBuilder.append("0");
                remainingBuilder.append(encodeAddress(splitted[2]));
            }
        } else if (opCode.equals("STX")) {
            remainingBuilder.append("00");
            remainingBuilder.append(encodeIndexRegister(splitted[0]));
            if (splitted.length == 3) {
                remainingBuilder.append(encodeIndirectAddressing(splitted[1]));
                remainingBuilder.append(encodeAddress(splitted[2]));
            } else {
                remainingBuilder.append("0");
                remainingBuilder.append(encodeAddress(splitted[1]));
            }
        } else if (opCode.equals("JZ")) {
            remainingBuilder.append(encodeRegister("0"));
            remainingBuilder.append(encodeIndexRegister(splitted[0]));
            remainingBuilder.append(encodeIndirectAddressing("0"));
            remainingBuilder.append(encodeAddress(splitted[1]));
        } else if (opCode.equals("JNE")) {
            remainingBuilder.append(encodeRegister("0"));
            remainingBuilder.append(encodeIndexRegister(splitted[0]));
            remainingBuilder.append(encodeIndirectAddressing("0"));
            remainingBuilder.append(encodeAddress(splitted[1]));
        } else if (opCode.equals("JCC")) {
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append(encodeIndexRegister(splitted[1]));
            if (splitted.length == 4) {
                remainingBuilder.append(encodeIndirectAddressing(splitted[2]));
                remainingBuilder.append(encodeAddress(splitted[3]));
            } else {
                remainingBuilder.append("0");
                remainingBuilder.append(encodeAddress(splitted[2]));
            }
        } else if (opCode.equals("JMA")) {// CHECK
            remainingBuilder.append("00");
            remainingBuilder.append(encodeIndexRegister(splitted[0]));
            if (splitted.length == 3) {
                remainingBuilder.append(encodeIndirectAddressing(splitted[1]));
                remainingBuilder.append(encodeAddress(splitted[2]));
            } else {
                remainingBuilder.append("0");
                remainingBuilder.append(encodeAddress(splitted[1]));
            }
        } else if (opCode.equals("JSR")) {
            remainingBuilder.append("00");
            remainingBuilder.append(encodeIndexRegister(splitted[0]));
            if (splitted.length == 3) {
                remainingBuilder.append(encodeIndirectAddressing(splitted[1]));
                remainingBuilder.append(encodeAddress(splitted[2]));
            } else {
                remainingBuilder.append("0");
                remainingBuilder.append(encodeAddress(splitted[1]));
            }
        } else if (opCode.equals("RFS")) {
            for (int i = 0; i < 10 - (decimalToBinary(splitted[0])).length(); i++) {
                remainingBuilder.append("0");
            }
            remainingBuilder.append(decimalToBinary(splitted[0]));
        } else if (opCode.equals("SOB")) {
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append(encodeIndexRegister(splitted[1]));
            if (splitted.length == 4) {
                remainingBuilder.append(encodeIndirectAddressing(splitted[2]));
                remainingBuilder.append(encodeAddress(splitted[3]));
            } else {
                remainingBuilder.append("0");
                remainingBuilder.append(encodeAddress(splitted[2]));
            }
        } else if (opCode.equals("JGE")) {
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append(encodeIndexRegister(splitted[1]));
            if (splitted.length == 4) {
                remainingBuilder.append(encodeIndirectAddressing(splitted[2]));
                remainingBuilder.append(encodeAddress(splitted[3]));
            } else {
                remainingBuilder.append("0");
                remainingBuilder.append(encodeAddress(splitted[2]));
            }
        } else if (opCode.equals("AMR")) {
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append(encodeIndexRegister(splitted[1]));
            if (splitted.length == 4) {
                remainingBuilder.append(encodeIndirectAddressing(splitted[2]));
                remainingBuilder.append(encodeAddress(splitted[3]));
            } else {
                remainingBuilder.append("0");
                remainingBuilder.append(encodeAddress(splitted[2]));
            }
        } else if (opCode.equals("SMR")) {
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append(encodeIndexRegister(splitted[1]));
            if (splitted.length == 4) {
                remainingBuilder.append(encodeIndirectAddressing(splitted[2]));
                remainingBuilder.append(encodeAddress(splitted[3]));
            } else {
                remainingBuilder.append("0");
                remainingBuilder.append(encodeAddress(splitted[2]));
            }
        } else if (opCode.equals("AIR")) {
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append("000");
            for (int i = 0; i < 5 - (decimalToBinary(splitted[1])).length(); i++) {
                remainingBuilder.append("0");
            }
            remainingBuilder.append(decimalToBinary(splitted[1]));
        } else if (opCode.equals("SIR")) {
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append("000");
            for (int i = 0; i < 5 - (decimalToBinary(splitted[1])).length(); i++) {
                remainingBuilder.append("0");
            }
            remainingBuilder.append(decimalToBinary(splitted[1]));
        } else if (opCode.equals("MLT")) {
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append(encodeRegister(splitted[1]));
            remainingBuilder.append("000000");
        } else if (opCode.equals("DVD")) {
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append(encodeRegister(splitted[1]));
            remainingBuilder.append("000000");
        } else if (opCode.equals("TRR")) {
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append(encodeRegister(splitted[1]));
            remainingBuilder.append("000000");
        } else if (opCode.equals("AND")) {
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append(encodeRegister(splitted[1]));
            remainingBuilder.append("000000");
        } else if (opCode.equals("ORR")) {
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append(encodeRegister(splitted[1]));
            remainingBuilder.append("000000");
        } else if (opCode.equals("NOT")) {
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append("00000000");
        } else if (opCode.equals("SRC")) {
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append(encode(splitted[3]));
            remainingBuilder.append(encode(splitted[2]));
            remainingBuilder.append("00");
            remainingBuilder.append(encodeCount(splitted[1]));
        } else if (opCode.equals("RRC")) {
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append(splitted[3]);
            remainingBuilder.append(splitted[2]);
            remainingBuilder.append("00");
            remainingBuilder.append(encodeCount(splitted[1]));
        } else if (opCode.equals("TRAP")) {
            remainingBuilder.append(encodeTRP(splitted[0]));
            remainingBuilder.append("00000000");
        } else if (opCode.equals("IN")) {
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append("000");
            for (int i = 0; i < 5 - (encodeDevice(splitted[1])).length(); i++) {
                remainingBuilder.append("0");
            }
            remainingBuilder.append(encodeDevice(splitted[1]));
        } else if (opCode.equals("OUT")) {
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append("000");
            for (int i = 0; i < 5 - (encodeDevice(splitted[1])).length(); i++) {
                remainingBuilder.append("0");
            }
            remainingBuilder.append(encodeDevice(splitted[1]));
        } else if (opCode.equals(("CHK"))){
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append("000");
            for (int i = 0; i < 5 - (encodeDevice(splitted[1])).length(); i++) {
                remainingBuilder.append("0");
            }
            remainingBuilder.append(encodeDevice(splitted[1]));
        } else if (opCode.equals("VADD")) {
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append(encodeIndexRegister(splitted[1]));
            if (splitted.length == 4) {
                remainingBuilder.append(encodeIndirectAddressing(splitted[2]));
                remainingBuilder.append(encodeAddress(splitted[3]));
            } else {
                remainingBuilder.append("0");
                remainingBuilder.append(encodeAddress(splitted[2]));
            }
        } else if (opCode.equals("VSUB")) {
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append(encodeIndexRegister(splitted[1]));
            if (splitted.length == 4) {
                remainingBuilder.append(encodeIndirectAddressing(splitted[2]));
                remainingBuilder.append(encodeAddress(splitted[3]));
            } else {
                remainingBuilder.append("0");
                remainingBuilder.append(encodeAddress(splitted[2]));
            }
        } else if (opCode.equals("FADD")) {
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append(encodeIndexRegister(splitted[1]));
            if (splitted.length == 4) {
                remainingBuilder.append(encodeIndirectAddressing(splitted[2]));
                remainingBuilder.append(encodeAddress(splitted[3]));
            } else {
                remainingBuilder.append("0");
                remainingBuilder.append(encodeAddress(splitted[2]));
            }
        } else if (opCode.equals("FSUB")) {
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append(encodeIndexRegister(splitted[1]));
            if (splitted.length == 4) {
                remainingBuilder.append(encodeIndirectAddressing(splitted[2]));
                remainingBuilder.append(encodeAddress(splitted[3]));
            } else {
                remainingBuilder.append("0");
                remainingBuilder.append(encodeAddress(splitted[2]));
            }
        } else if (opCode.equals("CNVRT")) {
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append(encodeIndexRegister(splitted[1]));
            if (splitted.length == 4) {
                remainingBuilder.append(encodeIndirectAddressing(splitted[2]));
                remainingBuilder.append(encodeAddress(splitted[3]));
            } else {
                remainingBuilder.append("0");
                remainingBuilder.append(encodeAddress(splitted[2]));
            }
        } else if (opCode.equals("LDFR")) {
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append(encodeIndexRegister(splitted[1]));
            if (splitted.length == 4) {
                remainingBuilder.append(encodeIndirectAddressing(splitted[2]));
                remainingBuilder.append(encodeAddress(splitted[3]));
            } else {
                remainingBuilder.append("0");
                remainingBuilder.append(encodeAddress(splitted[2]));
            }
        } else if (opCode.equals("STFR")) {
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append(encodeIndexRegister(splitted[1]));
            if (splitted.length == 4) {
                remainingBuilder.append(encodeIndirectAddressing(splitted[2]));
                remainingBuilder.append(encodeAddress(splitted[3]));
            } else {
                remainingBuilder.append("0");
                remainingBuilder.append(encodeAddress(splitted[2]));
            }
        }

        else if (opCode.equals("SETCCE")) {
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append("00000000");
        }

        else if (opCode.equals("Data")){
            if(remaining.equals("End")){
                remainingBuilder.append(encodeData(finalLocation));
            }

            else{
                remainingBuilder.append(encodeData(remaining));
            }
        }

        return remainingBuilder.toString();
    }

    private String binToOctal(String s) {
        // Converts Binary value to Octal format
        int decimalValue = Integer.parseInt(s, 2);
        return Integer.toOctalString(decimalValue);
    }

    public Map<String, String> getLocToInstructionMap() {
        return locToInstructionMap;
    }
}
