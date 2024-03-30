import gwu.csci6461.team4.CPU;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CPUTest {

    private CPU cpu;

    @BeforeEach
    public void setUp(){
        cpu = new CPU();
    }

    @Test
    public void testIntToBinaryArrayPositive() {
       int[] result =  cpu.intToBinaryArray("50");
       assertEquals(result[0],0);
    }

    @Test
    public void testIntToBinaryArrayNegative() {
        int[] result =  cpu.intToBinaryArray("-50");
        assertEquals(result[0],1);
    }

    @Test
    public void octalToBinaryArrayPositive(){
        int[] result = cpu.octalToBinaryArray("000024");
        assertArrayEquals(result, new int[]{0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0});
    }

    @Test
    public void octalToBinaryArrayNegative(){
        int[] result = cpu.octalToBinaryArray("-34");
        assertArrayEquals(result, new int[]{1,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0});
    }

    @Test
    public void binaryArrayToIntPositive() {
        int[] input = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1};
        int result = cpu.binaryArrayToInt(input);
        assertEquals(result,7);
    }

    @Test
    public void binaryArrayToIntNegative() {
        int[] input = new int[]{1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1};
        int result = cpu.binaryArrayToInt(input);
        assertEquals(result,-7);
    }

    @Test
    public void binaryToIntPositive() {
        int[] input = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1};
        int result = cpu.binaryArrayToInt(input);
        assertEquals(result,7);
    }

    @Test
    public void binaryToIntNegative() {
        int[] input = new int[]{1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1};
        int result = cpu.binaryArrayToInt(input);
        assertEquals(result,-7);
    }
}
