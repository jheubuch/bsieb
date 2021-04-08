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

    public static void printHex(int x) {
        print(Integer.toHexString(x));
    }

    // PRINT LONG
    public static void print(long x) {
        print(Long.toString(x));
    }

    public static void println(long x) {
        print(x);
        println();
    }

    public static void printHex(long x) {
        print(Long.toHexString(x));
    }

    // PRINT BYTE
    public static void printHex(byte b) {
        print(Byte.toHexString(b));
    }
    public static void directPrintHex(int x, int y, byte b, int color) {
        byte mask = (byte) 0x0F;
        byte leftNibble = (byte)((b >>> 4) & mask);
        byte rightNibble = (byte)(b & mask);

        int printPos = x + y * 80;
        vidMem.digit[printPos].ascii = (byte) Byte.toHex(leftNibble);
        vidMem.digit[printPos++].color = (byte) color;
        vidMem.digit[printPos].ascii = (byte) Byte.toHex(rightNibble);
        vidMem.digit[printPos].color = (byte) color;
    }

    // PRINT SHORT
    public static void print(short s) {
        print(Short.toString(s));
    }

    public static void println(short s) {
        print(s);
        println();
    }

    public static void printHex(short s) {
        print(Short.toHexString(s));
    }

    // PRINT STRING
    public static void directPrint(int x, int y, String output, int color) {
        int printPos = x + y * 80;
        for (int i = 0; i < output.length(); i++) {
            vidMem.digit[printPos + i].ascii = (byte) output.charAt(i);
            vidMem.digit[printPos + i].color = (byte) color;
        }
    }

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
    public static void directPrint(int x, int y, char c, int color) {
        int printPos = x + y * 80;
        vidMem.digit[printPos].ascii = (byte) c;
        vidMem.digit[printPos ].color = (byte) color;
    }

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
