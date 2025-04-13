package net.refinedsolution.util;

import org.jetbrains.annotations.NotNull;

public class StringUtils {
    /**
     * Returns a string of fixed length. If the string is shorter than the specified length, it will be padded with spaces.
     * @param str the string to be fixed
     * @param length the length to be fixed to
     * @return the fixed length string
     */
    public static @NotNull String fixedLength(String str, int length) {
        if (str.length() > length) {
            return str.substring(0, length);
        } else if (str.length() < length) {
            StringBuilder sb = new StringBuilder(str);
            while (sb.length() < length) {
                sb.append(" ");
            }
            return sb.toString();
        }
        return str;
    }

    /**
     * Returns a string with a length multiple of the given length. If the string is shorter than the specified length, it will be padded with spaces.
     * @param str the string to be fixed
     * @param length the length to be fixed to
     * @return the fixed length string
     */
    public static @NotNull String fixedXLength(String str, int length) {
        float multiple = (float) str.length() / length;
        if (multiple > (int) multiple) {
            int newLength = (int) (multiple + 1) * length;
            return fixedLength(str, newLength);
        } else {
            return str;
        }
    }
}
