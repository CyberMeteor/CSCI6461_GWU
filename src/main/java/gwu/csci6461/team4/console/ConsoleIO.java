package gwu.csci6461.team4.console;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class ConsoleIO {
    private ConsoleController consoleController;

    public ConsoleIO(ConsoleController consoleController) {
        this.consoleController = consoleController;
    }

    // Custom PrintStream to redirect System.out
    private class ConsolePrintStream extends PrintStream {
        public ConsolePrintStream(OutputStream out) {
            super(out);
        }

        @Override
        public void write(byte[] buf, int off, int len) {
            String output = new String(buf, off, len);
            // Append the output to the TextArea
            consoleController.appendToOutputTextArea(output);
        }

        @Override
        public void write(int b) {
            // Append the output to the TextArea
            consoleController.appendToOutputTextArea(String.valueOf((char) b));
        }
    }

    // Method to redirect System.out to OutputTextArea
    public void redirectSystemOut() {
        ConsolePrintStream printStream = new ConsolePrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                // Not used
            }
        });
        System.setOut(printStream);
    }
}
