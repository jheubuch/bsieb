package java.lang;

public class Integer {
    private static final int SIZE_BYTES = 4;
    private int value;

    public Integer(int val) {
        value = val;
    }

    public String toString() {
        return Integer.toString(value);
    }

    public String toHexString() {
        return Integer.toHexString(value);
    }

    public static String toString(int value) {
        return Long.toString(value);
    }

    public static String toHexString(int val) {
        String[] hexStrings = new String[SIZE_BYTES];

        for (int i = 0; i < SIZE_BYTES; i++) {
            hexStrings[SIZE_BYTES - i - 1] = Byte.toHexString((byte) val);
            val = val >>> 8;
        }

        return String.concat(hexStrings);
    }
}
