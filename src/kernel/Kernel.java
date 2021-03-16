package kernel;

public class Kernel {
    private static int vidMem=0xB8000;
    public static void main() {
        print("Hello World");
        while(true);
    }
    public static void print(String str) {
        int i;
        for (i=0; i<str.length(); i++) print(str.charAt(i));
    }
    public static void print(char c) {
        MAGIC.wMem8(vidMem++, (byte)c);
        MAGIC.wMem8(vidMem++, (byte)0x07);
    }
}
