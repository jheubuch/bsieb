package java.lang;

public class Short {

    public static final int SIZE_BYTES = 2;
    private short value;

    public Short(short val) {
        value = val;
    }

    public String toString() {
        return Short.toString(value);
    }

    public String toHexString() {
        return Short.toHexString(value);
    }

    public static String toString(short value) {
        return Long.toString(value);
    }

    public static String toHexString(short val) {
        String[] hexStrings = new String[SIZE_BYTES];

        for (int i = 0; i < SIZE_BYTES; i++) {
            hexStrings[SIZE_BYTES - i - 1] = Byte.toHexString((byte) val);
            val = (short)(val >>> 8);
        }

        return String.concat(hexStrings);
    }
}
