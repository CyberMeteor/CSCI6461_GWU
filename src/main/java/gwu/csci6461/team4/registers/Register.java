package gwu.csci6461.team4.registers;


public class Register {

    private int[] register_value;
    private int register_size = 0;

    public Register(int size){
        initRegisterSize(size);
    }

    public int[] getRegisterValue(){
        return register_value;
    }

    public int getRegisterSize(){
        return register_size;
    }

    public void setRegisterValue(int[] new_value){
        if (this.register_size == 0){
            System.out.print("Error -- Register size is zero!");
        }
        else{
            this.register_value = new_value;
        }

    }

    private void initRegisterSize(int register_size){
        this.register_size = register_size;
        this.register_value = new int[register_size];

    }
}