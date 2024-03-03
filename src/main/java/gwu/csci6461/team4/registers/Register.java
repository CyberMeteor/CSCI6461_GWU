package gwu.csci6461.team4.registers;


public class Register {

    private int[] registerValue;
    private int registerSize = 0;

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
}