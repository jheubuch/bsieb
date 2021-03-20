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

    public static String concat(String[] chars) {
        int length = 0;
        for (int i = 0; i < chars.length; i++)
            length += chars[i].length();

        int pointer = 0;
        char[] concat = new char[length];
        for (int i = 0; i < chars.length; i++)
            for (int j = 0; j < chars[i].length(); j++)
                concat[pointer++] = chars[i].charAt(j);

        return new String(concat);
    }
}