package kernel.memory.keyboard;

import kernel.GlobalAddresses;

public class InputBufferManager {
    private static InputBuffer buffer = (InputBuffer) MAGIC.cast2Struct(GlobalAddresses.INPUT_BUFFER_START);
    public static final int BUFFER_SIZE = 128;
    public static int writeIndex = 0;
    public static int readIndex = 0;
    public static int bytesToRead = 0;
    private static int accessPermission = 0; // 0 = permitted, 1 = denied

    public static void writeToBuffer(byte code) {
        while (!reserveAccessPermission());

        if (bytesToRead == 0)
            buffer.keyCodes[writeIndex] = 0;

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
            count += BUFFER_SIZE;
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
        if (writeIndex == (BUFFER_SIZE - 1))
            writeIndex = 0;
        else
            writeIndex++;
        releaseAccessPermission();
    }

    private static void incrementReadIndex() {
        if (readIndex == (BUFFER_SIZE - 1))
            readIndex = 0;
        else
            readIndex++;
    }
}
