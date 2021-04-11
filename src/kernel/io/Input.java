package kernel.io;

import kernel.interrupt.Interrupt;
import kernel.memory.keyboard.InputBufferManager;

public class Input {

    public static void printKeyStrokes() {
        while (true) {
            int[] readCodes = InputBufferManager.readFromBuffer();
            if (readCodes == null) {
                Interrupt.wait(1);
                continue;
            }
            for (int i = 0; i < readCodes.length; i++) {
                Output.directPrintHex(0, 0, (byte)readCodes[i], Color.GREEN);
            }
        }
    }
}
