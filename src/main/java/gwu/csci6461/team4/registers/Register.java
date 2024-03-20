package gwu.csci6461.team4.registers;


import java.util.ArrayList;

public class Register {

    private int[] registerValue;
    private int registerSize = 0;
    private int deviceInput;
    private ArrayList<Integer> asciiValue = new ArrayList<Integer>();


    public Register(int size){
        initRegisterSize(size);
    }

    public int[] getRegisterValue(){
        return registerValue;
    }

    public String getStringValue() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < registerSize; i++) {
            stringBuilder.append(registerValue[i]);
        }
        return stringBuilder.toString();
    }

    public int getRegisterSize(){
        return registerSize;
    }

    public void setRegisterValue(int[] new_value){
        if (this.registerSize == 0){
            System.out.print("Error -- Register size is zero!");
        }
        else{
            this.registerValue = new_value;
        }

    }

    private void initRegisterSize(int register_size){
        this.registerSize = register_size;
        this.registerValue = new int[register_size];

    }

    public void setDeviceInput(int number) {
        deviceInput = number;
    }

    public int getDeviceInput() {
        return deviceInput;
    }

    public void setAsciiValue(ArrayList<Integer> asciiValue){
        this.asciiValue = asciiValue;
    }

    public ArrayList<Integer> getAsciiValue(){
        return asciiValue;
    }

}