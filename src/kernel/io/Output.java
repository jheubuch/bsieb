package kernel.io;

import rte.VidMem;

public class Output {

    private static VidMem vidMem = (VidMem) MAGIC.cast2Struct(0xB8000);
    private static int actualVidPos = 0;
    private static byte color = (byte) 0x07;

    public static void setCursor(int x, int y) {
        if (x >= 80 || y >= 25) {
            x = 0;
            y = 0;
        }

        actualVidPos = x + y * 80;
    }

    public static void initScreen() {
        actualVidPos = 0;
        for (int i = 0; i < 2000; i++)
            print(' ', (byte) 0x01);
    }

    public static void setColor(int foreground, int background) {
        color = (byte)(background * 16 + foreground);
    }

    // PRINT INT
    public static void print(int x) {
        print(Integer.toString(x));
    }

    public static void println(int x) {
        print(x);
        println();
    }

    // PRINT LONG
    public static void print(long x) {
        print(Long.toString(x));
    }

    public static void println(long x) {
        print(x);
        println();
    }

    // PRINT STRING
    public static void print(String output) {
        for (int i = 0; i < output.length(); i++)
            print(output.charAt(i), color);
    }

    public static void println(String output) {
        print(output);
        println();
    }

    // PRINTLN
    public static void println() {
        actualVidPos += 80;
        actualVidPos -= (actualVidPos % 80);
    }

    // PRINT CHAR
    public static void print(char c) {
        print(c, color);
    }

    public static void println(char c) {
        print(c);
        println();
    }

    public static void print(char c, byte color) {
        if (actualVidPos < 0 || actualVidPos >= 2000)
            actualVidPos = 0;

        vidMem.digit[actualVidPos].ascii = (byte) c;
        vidMem.digit[actualVidPos++].color = color;
    }
}
