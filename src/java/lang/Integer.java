package java.lang;

public class Integer {
    private int value;

    public Integer(int val) {
        value = val;
    }

    public String toString() {
        return Integer.toString(value);
    }

    public static String toString(int value) {
        return Long.toString(value);
    }
}
