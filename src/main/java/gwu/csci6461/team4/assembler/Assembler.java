package gwu.csci6461.team4.assembler;

import java.math.BigInteger;
import java.util.HashMap;

public class Assembler {

    public void assemble(String opCode, String remaining) {
        String encodedOpCode = encodeOpCode(opCode,remaining);
        String encodedRemaining = encodeRemaining(opCode, remaining);
        String encodedInstruction = binToOctal(encodedOpCode + encodedRemaining);
        System.out.println(encodedInstruction);
    }

    private String encodeOpCode(String opCode, String remaining) {
        HashMap<String, String> opCodeMap = new HashMap<>();
        opCodeMap.put("LDR", "000001");
        opCodeMap.put("LDX", "101001");
        opCodeMap.put("LDA", "000011");
        opCodeMap.put("STX", "101010");
        opCodeMap.put("STR", "000010");
        opCodeMap.put("JNE", "001011");
        opCodeMap.put("JZ", "001010");
        opCodeMap.put("JCC", "001100");
        opCodeMap.put("AIR", "000110");
        opCodeMap.put("JSR", "001110");
        opCodeMap.put("JNE", "001011");
        opCodeMap.put("JZ", "001010");
        opCodeMap.put("JCC", "001100");
        opCodeMap.put("AIR", "000110");
        opCodeMap.put("JSR", "001110");
        opCodeMap.put("RFS", "001111");
        opCodeMap.put("JMA", "001101");
        opCodeMap.put("SOB", "110000");
        opCodeMap.put("JGE", "110001");
        opCodeMap.put("AMR", "000100");
        opCodeMap.put("SMR", "000101");
        opCodeMap.put("SIR", "000111");
        opCodeMap.put("MLT", "010100");
        opCodeMap.put("DVD", "010101");
        opCodeMap.put("TRR", "010110");
        opCodeMap.put("AND", "010111");
        opCodeMap.put("LDFR", "110010");
        opCodeMap.put("ORR", "011000");
        opCodeMap.put("NOT", "011001");
        opCodeMap.put("SRC", "011111");
        opCodeMap.put("RRC", "100000");
        opCodeMap.put("TRAP", "100100");
        opCodeMap.put("IN", "111101");
        opCodeMap.put("OUT", "111110");
        opCodeMap.put("FADD", "100001");
        opCodeMap.put("FSUB", "100010");
        opCodeMap.put("CNVRT", "100101");
        opCodeMap.put("STFR", "110011");
        opCodeMap.put("VADD", "100110");
        opCodeMap.put("VSUB", "100111");
        //TODO We need to decide whether the LOC is first or last
        //opCodeMap.put("LOC", encodeLOC(remaining));
        //opCodeMap.put("DATA" , encodeData(remaining));
        return opCodeMap.get(opCode);
    }

    private String encodeLOC(String remaining){
       return decimalToBinary(remaining);
    }

    private String encodeData(String data){
      return decimalToBinary(data);
    }

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

    private String decimalToBinary(String decimalAddress) {
        if (decimalAddress == null) {
            decimalAddress = "0";
        }
        return new BigInteger(decimalAddress, 10).toString(2);
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

    private String encodeRemaining(String opCode, String remaining) {
        StringBuilder remainingBuilder = new StringBuilder();
        String[] splitted = remaining.split(",");

        if (opCode.equals("LDR")) {
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append(encodeIndexRegister(splitted[1]));
            if (splitted.length == 4) {
                remainingBuilder.append(encodeIndirectAddressing(splitted[2]));
                remainingBuilder.append(encodeAddress(splitted[3]));
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
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append(encodeIndexRegister(splitted[1]));
            if (splitted.length == 4) {
                remainingBuilder.append(encodeIndirectAddressing(splitted[2]));
                remainingBuilder.append(encodeAddress(splitted[3]));
            } else {
                remainingBuilder.append("0");
                remainingBuilder.append(encodeAddress(splitted[2]));
            }
        } else if (opCode.equals("JNE")) {
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append(encodeIndexRegister(splitted[1]));
            if (splitted.length == 4) {
                remainingBuilder.append(encodeIndirectAddressing(splitted[2]));
                remainingBuilder.append(encodeAddress(splitted[3]));
            } else {
                remainingBuilder.append("0");
                remainingBuilder.append(encodeAddress(splitted[2]));
            }
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
        } else if (opCode.equals("JSR")) {// CHECK
            remainingBuilder.append("00");
            remainingBuilder.append(encodeIndexRegister(splitted[0]));
            if (splitted.length == 3) {
                remainingBuilder.append(encodeIndirectAddressing(splitted[1]));
                remainingBuilder.append(encodeAddress(splitted[2]));
            } else {
                remainingBuilder.append("0");
                remainingBuilder.append(encodeAddress(splitted[1]));
            }
        } else if (opCode.equals("RFS")) {// CHECK
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
        } else if (opCode.equals("AIR")) {// CHECK
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append("000");
            for (int i = 0; i < 5 - (decimalToBinary(splitted[1])).length(); i++) {
                remainingBuilder.append("0");
            }
            remainingBuilder.append(decimalToBinary(splitted[1]));
        } else if (opCode.equals("SIR")) {// CHECK
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
            remainingBuilder.append("00000000");
        } else if (opCode.equals("OUT")) {
            remainingBuilder.append(encodeRegister(splitted[0]));
            remainingBuilder.append("00000000");
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
        return remainingBuilder.toString();
    }

    private String binToOctal(String s) {
        // Converts Binary value to Octal format
        int decimal = Integer.parseInt(s, 2);
        String octalStr = Integer.toString(decimal, 8);
        return octalStr;
    }
}
