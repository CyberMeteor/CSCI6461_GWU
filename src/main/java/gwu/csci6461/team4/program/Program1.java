package gwu.csci6461.team4.program;

import gwu.csci6461.team4.CPU;
import gwu.csci6461.team4.cache.Cache;

public class Program1 {

    CPU cpu;
    Cache cache;
    public void inputAndStoreNumbers(int[] decimalNumbers, int memoryAddressStartPoint) {
        for (int i = 0; i < decimalNumbers.length; i++) {
            // Get input
            cpu.executeIN(new int[]{0, 0, 0}); // Use appropriate argument structure

            // Store the value
            cpu.executeSTR(new int[]{memoryAddressStartPoint + i, 0, 0});  // Adjust arguments
        }
    }

    public int findClosestNumber(int numberToBeComparedWith, int totalNumbers, int memoryAddressStartPoint) {
        int minDiff = Integer.MAX_VALUE;
        int closestNumber = 0; // Assuming 'closestNumber' will be the final value

        for (int i = 0; i < totalNumbers; i++) {
            int diff = findDifference(numberToBeComparedWith, memoryAddressStartPoint + i);

            if (diff < minDiff) {
                minDiff = diff;
                Object Cache;
                closestNumber = cache.getWordDecimalValue(new Address(memoryAddressStartPoint + i));
            }
        }

        return closestNumber;
    }

    public int findDifference(int numberToBeComparedWith, int address) {
        // Input the comparison number
        executeIN(new int[]{0, 0, 0}); // Adapt the arguments as needed

        // Calculate difference using SMR
        executeSMR(new int[]{address, 0, 0}); // Modify the argument structure

        // Get the absolute difference
        int difference = Math.abs(binaryArrayToInt(GPRList.get(0).getRegisterValue()));

        return difference;
    }

    // Main execution
    public static void main(String[] args) {
        Program1 program = new Program1();

        // Input your 20 numbers
        int[] inputNumbers = { /* 20 numbers here */ };
        int startingMemoryAddress = /* starting memory address */ ;
        program.inputAndStoreNumbers(inputNumbers, startingMemoryAddress);

        // Get the number to compare
        executeIN(new int[]{0, 0, 0}); // Adapt the arguments as needed
        int comparisonNumber = binaryArrayToInt(GPRList.get(0).getRegisterValue());

        // Find the closest number
        int closestNumber =  program.findClosestNumber(comparisonNumber, 20, startingMemoryAddress);

        // Output the result
        executeOut(new int[]{0, 0, 0}); // Modify argument structure
    }

}
