package kernel.io;

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

    @SJC.Inline
    public static void cursorUp() {
        if (actualVidPos <= 80)
            actualVidPos = 0;
        else
            actualVidPos -= 80;
    }

    @SJC.Inline
    public static void cursorDown() {
        if (actualVidPos >= ((25 * 80) - 81))
            actualVidPos = (25 * 80) - 1;
        else
            actualVidPos += 80;
    }

    @SJC.Inline
    public static void cursorLeft() {
        if (actualVidPos == 0)
            actualVidPos = (25 * 80) - 1;
        else
            actualVidPos--;
    }

    @SJC.Inline
    public static void cursorRight() {
        if (actualVidPos == (25 * 80) - 1)
            actualVidPos = 0;
        else
            actualVidPos++;
    }

    public static void backspace() {
        if (actualVidPos != 0) {
            actualVidPos--;
            vidMem.digit[actualVidPos].ascii = ' ';
        }
    }

    public static void initScreen() {
        // disable cursor
        MAGIC.wIOs8(0x03D4, (byte)0x0A);
        MAGIC.wIOs8(0x03D5, (byte)0x20);

        actualVidPos = 0;
        for (int i = 0; i < 2000; i++)
            print(' ', (byte) 0x01);
    }

    public static void setColor(int foreground, int background) {
        int clr = background;
        clr <<= 4;
        clr |= foreground;
        clr &= 0x77;
        color = (byte) clr;
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
        for (int j = 0; j < 4; j++)
            printHex((byte)((x >>> (24 - 8 * j)) & 0xFF));
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
        byte mask = (byte) 0x0F;
        byte leftNibble = (byte)((b >>> 4) & mask);
        byte rightNibble = (byte)(b & mask);

        print(Byte.toHex(leftNibble));
        print(Byte.toHex(rightNibble));
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
