package Utils;

import java.util.Arrays;

public class StringCheckingUtils {

    public static boolean stringContainsItemFromList(String inputStr, String[] items) {
        return Arrays.stream(items).anyMatch(inputStr::contains);
    }
}
