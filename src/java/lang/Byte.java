package java.lang;

public class Byte {
    private byte value;

    public Byte(byte val) {
        value = val;
    }

    public String toHexString() {
        return Byte.toHexString(value);
    }

    public static String toHexString(byte b) {
        byte mask = (byte) 0x0F;
        byte leftNibble = (byte)((b >>> 4) & mask);
        byte rightNibble = (byte)(b & mask);

        char[] hexChars = new char[2];
        hexChars[0] = toHex(leftNibble);
        hexChars[1] = toHex(rightNibble);

        return new String(hexChars);
    }

    public static char toHex(byte val) {
        if (val >= (byte) 0 && val <= (byte) 9)
            return (char)(val + 48);
        else if (val >= (byte) 10 && val <= (byte) 15)
            return (char)(val + 55);
        return 'Z';
    }
}
