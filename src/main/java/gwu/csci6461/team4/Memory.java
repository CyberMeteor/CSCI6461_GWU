package gwu.csci6461.team4;

import java.util.Arrays;

public class Memory {

    private int[][] memoryData;

    public Memory(){
        this.memoryData = new int[2048][16];
    }

    public int[] getMemoryValue(int row){
        return Arrays.copyOfRange(this.memoryData[row],0,16);
    }

    public void setMemoryValue(int row, int[] value){
        System.arraycopy(value, 0, this.memoryData[row], 0, 16);
    }

}
