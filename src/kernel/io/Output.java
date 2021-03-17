package kernel.io;

import rte.VidMem;

public class Output {

    private static VidMem vidMem = (VidMem) MAGIC.cast2Struct(0xB8000);
    private static int vidPos;

    public static void print(String output, byte color) {
        for (int i = 0; i < output.length(); i++)
            print(output.charAt(i), color);
    }

    public static void print(String output) {
        for (int i = 0; i < output.length(); i++)
            print(output.charAt(i), (byte) 0x07);
    }

    private static void print(char c, byte color) {
        if (vidPos < 0 || vidPos >= 2000)
            vidPos = 0;

        vidMem.digit[vidPos].ascii = (byte) c;
        vidMem.digit[vidPos++].color = color;
    }
}
