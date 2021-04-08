package kernel.io;

import kernel.memory.keyboard.InputBuffer;

public class InputBufferManager {
    private static InputBuffer buffer = (InputBuffer) MAGIC.cast2Struct(0x08000);

    public static void writeToBuffer(byte code) {
        if (buffer.bytesToRead == 0) {
            if (code <= 0xDF) {
                // nur dieses byte zu lesen
                buffer.keyCodes[buffer.currentIndex].regular = code;
                increaseIndex();
            } else if (code == 0xE0) {
                // ein weiteres byte zu lesen
                buffer.keyCodes[buffer.currentIndex].extension0 = code;
                buffer.bytesToRead = 1;
            } else if (code == 0xE1) {
                // zwei weitere bytes lesen
                buffer.keyCodes[buffer.currentIndex].extension1 = code; // E1 E0 0D
                buffer.bytesToRead = 2;
            }
        } else {
            if (buffer.bytesToRead == 2) {
                buffer.keyCodes[buffer.currentIndex].extension0 = code;
            } else if (buffer.bytesToRead == 1) {
                buffer.keyCodes[buffer.currentIndex].regular = code;
                increaseIndex();
            }
            buffer.bytesToRead--;
        }
    }

    private static void increaseIndex() {
        if (buffer.currentIndex == 127)
            buffer.currentIndex = 0;
        else
            buffer.currentIndex++;
    }
}
