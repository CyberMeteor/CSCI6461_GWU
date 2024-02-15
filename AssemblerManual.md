## Manual: Unloading and Running the Assembler

This documentation provides step-by-step instructions for unloading and running our Assembler. The Assembler is a Java application packaged in a JAR file, designed to assemble input files and produce two output files: `load.txt` and `listing.txt`. 

### Prerequisites:
1. Ensure you have Java Runtime Environment (JRE) installed on your system. You can download it from [Java's official website](https://www.java.com/en/download/).
2. Make sure you have both the Assembler JAR file (`Assembler.jar`) and the input text file (`input.txt`) in the same directory.

### Unloading and Running the Assembler:
1. **Navigate to the Directory:**
   - Open your terminal or command prompt.
   - Use the `cd` command to navigate to the directory where you have placed both the `Assembler.jar` file and the `input.txt` file. For example:
     ```
     cd /path/to/your/directory
     ```

2. **Execute the Assembler:**
   - Once you are in the directory containing the JAR file and input file, execute the following command to run the Assembler:
     ```
     java -jar Assembler.jar
     ```
   This command will execute the Java Virtual Machine (JVM) and run the Assembler application.

3. **Review Output Files:**
   - After the Assembler has finished processing, you should find two output files in the same directory:
     - `load.txt`: This file serves as input for the simulator in future work.
     - `listing.txt`: This file contains the result of input test cases.

4. **Verify Output:**
   - You can open and review the `load.txt` and `listing.txt` files to ensure that the Assembler has produced the desired output.

### Additional Notes:
- The output `listing.txt` file will only contain the results of the instructions processed by the Assembler. It will not include any explanatory sections from the project description examples to avoid unnecessary confusion for future work.
- Our team have tested the assembler by changing the input and attempting to run all the instructions in the project description, and some screenshots of our testing are included in the `testing_doc` file. To test additional instructions, simply modify the contents of the `input.txt` file to include the desired instructions.


By following these steps, you should be able to successfully unload and run our Assembler, generating the required output files.
