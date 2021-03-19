package java.lang;

public class Long {

    private long value;

    public Long(long val) {
        value = val;
    }

    public String toString() {
        return Long.toString(value);
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
}
