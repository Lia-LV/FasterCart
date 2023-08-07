package me.lia_lv.fastercart.utils;

import java.util.HashMap;
import java.util.regex.Pattern;

public class LiaUtils {

    /**
     *
     * @param originalString Original string with placeholder
     * @param placeholders HashMap with 'placeholder' and 'string to replace'
     * @return New string with replaced placeholders
     */
    public static String replacePlaceholders(String originalString, HashMap<String, String> placeholders) {
        for (String key : placeholders.keySet()) {
            if (originalString.toLowerCase().contains(key.toLowerCase())) {
                continue;
            }
            originalString = originalString.replaceAll("(?i)" + Pattern.quote(key), placeholders.get(key));
        }

        return originalString;
    }

    /**
     *
     * @param originalString Original string with placeholder
     * @param placeholder Placeholder to replace
     * @param stringToReplace String to replace placeholder with
     * @return New string with replaced placeholders
     */
    public static String replacePlaceholders(String originalString, String placeholder, String stringToReplace) {
        if (originalString.toLowerCase().contains(placeholder.toLowerCase())) {
            return originalString;
        }
        return originalString.replaceAll("(?i)" + Pattern.quote(placeholder), stringToReplace);
    }


}
