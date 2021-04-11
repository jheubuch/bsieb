package kernel.memory.keyboard;

import kernel.io.Color;
import kernel.io.Output;

public class InputBufferManager {
    private static InputBuffer buffer = (InputBuffer) MAGIC.cast2Struct(0x08000);
    public static int writeIndex = 0;
    public static int readIndex = 0;
    public static int bytesToRead = 0;
    private static int accessPermission = 0; // 0 = permitted, 1 = denied

    public static void writeToBuffer(byte code) {
        while (!reserveAccessPermission());

        buffer.keyCodes[writeIndex] <<= 8;
        buffer.keyCodes[writeIndex] += code;

        if (bytesToRead == 0) {
            if (code <= 0xDF)               // only one byte to read
                completeWriteProgress();
            else if (code == 0xE0)          // read one more byte
                bytesToRead = 1;
            else if (code == 0xE1)          // read two more bytes
                bytesToRead = 2;
        } else {
            if (bytesToRead == 1)
                completeWriteProgress();

            bytesToRead--;
        }
    }

    public static int[] readFromBuffer() {
        while (!reserveAccessPermission());

        int count = writeIndex - readIndex;
        if (count < 0)
            count += 128;
        else if (count == 0) {
            releaseAccessPermission();
            return null;
        }

        int[] readKeyCodes = new int[count];
        for (int i = 0; i < count; i++) {
            readKeyCodes[i] = buffer.keyCodes[readIndex];
            incrementReadIndex();
        }

        releaseAccessPermission();
        return readKeyCodes;
    }

    private static boolean reserveAccessPermission() {
        if (accessPermission == 0) {
            accessPermission = 1;
            return true;
        }
        return false;
    }

    private static void releaseAccessPermission() {
        accessPermission = 0;
    }

    private static void completeWriteProgress() {
        if (writeIndex == 127)
            writeIndex = 0;
        else
            writeIndex++;
        releaseAccessPermission();
    }

    private static void incrementReadIndex() {
        if (readIndex == 127)
            readIndex = 0;
        else
            readIndex++;
    }
}
