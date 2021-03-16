package kernel;

public class Kernel {
    private final static VidMem vidMem = (VidMem) MAGIC.cast2Struct(0xB8000);
    private static int vidPos;

    public static void main() {
        print("Hello OS world!");
        while(true);
    }
    public static void print(String str) {
        for (int i = 0; i < str.length(); i++)
            print(str.charAt(i));
    }
    public static void print(char c) {
        if (vidPos < 0 || vidPos > 2000)
            vidPos = 0;

        vidMem.digit[vidPos].ascii = (byte) c;
        vidMem.digit[vidPos].color = 0x02;
        vidPos++;
    }
}