package java.lang;
public class String {
    private char[] value;
    private int count;

    public String(char[] chars) {
        value = chars;
        count = chars.length;
    }

    @SJC.Inline
    public int length() {
        return count;
    }
    @SJC.Inline
    public char charAt(int i) {
        return value[i];
    }
}