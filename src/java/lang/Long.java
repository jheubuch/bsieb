package java.lang;

public class Long {

    private static final int SIZE_BYTES = 8;
    private long value;

    public Long(long val) {
        value = val;
    }

    public String toString() {
        return Long.toString(value);
    }

    public String toHexString() {
        return Long.toHexString(value);
    }

    public static String toString(long value) {
        int digitCnt = 1;
        long val = value;
        if (val < 0)
            val *= -1;

        while (val >= 10) {
            val /= 10;
            digitCnt++;
        }

        char[] digitChars;
        if (value < 0)
            digitChars = new char[digitCnt + 1];
        else
            digitChars = new char[digitCnt];

        if (value < 0) {
            digitChars[0] = '-';
            value *= -1;
        }

        for (int i = digitChars.length - 1; i > (digitChars.length - digitCnt - 1); i--) {
            char toAdd = (char)(48 + (value % 10));
            digitChars[i] = toAdd;
            value /= 10;
        }

        return new String(digitChars);
    }

    public static String toHexString(long val) {
        String[] hexStrings = new String[SIZE_BYTES];

        for (int i = 0; i < SIZE_BYTES; i++) {
            hexStrings[SIZE_BYTES - i - 1] = Byte.toHexString((byte) val);
            val = val >>> 8;
        }

        return String.concat(hexStrings);
    }
}
