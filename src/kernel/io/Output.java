package kernel.io;

import rte.VidMem;

public class Output {

    private static VidMem vidMem = (VidMem) MAGIC.cast2Struct(0xB8000);
    private static int cursor = 0;
    private static int actualVidPos = 0;

    public static void setCursor(int x, int y) {
        if (x >= 80 || y >= 25) {
            x = 0;
            y = 0;
        }

        cursor = x + y * 80;
    }

    public static void print(String output, byte color) {
        for (int i = 0; i < output.length(); i++)
            print(output.charAt(i), color);
    }

    public static void print(String output) {
        actualVidPos = cursor;
        for (int i = 0; i < output.length(); i++)
            print(output.charAt(i), (byte) 0x07);
    }

    public static void print(char c, byte color) {
        if (actualVidPos < 0 || actualVidPos >= 2000)
            setCursor(0, 0);

        vidMem.digit[actualVidPos].ascii = (byte) c;
        vidMem.digit[actualVidPos++].color = color;
    }

    public static void initScreen() {
        for (int i = 0; i < 2000; i++)
            print(' ', (byte) 0x01);
    }
}
