package net.thesimpleteam.jrevolt.utils;

public class StringUtils {

    private StringUtils() {}

    public static String wordUpperCase(String str) {
        return wordUpperCase(str, false);
    }

    public static String wordUpperCase(String str, boolean space) {
        char[] charArray = str.toCharArray();
        StringBuilder builder = new StringBuilder();
        builder.append(str.substring(0, 1).toUpperCase());
        for (int i = 1; i < charArray.length; i++) {
            if(charArray[i] == '_') {
                if(space) {
                    builder.append(' ');
                }
                builder.append(String.valueOf(charArray[++i]).toUpperCase());
            } else {
                builder.append(charArray[i]);
            }
        }
        return builder.toString();
    }
}
