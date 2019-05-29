package com.thekineticz.vectool.vec.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Regex pattern matcher for validating hexadecimal colour strings.
 */
class ColourHexMatcher{

    private static final String WEB_COLOUR_PATTERN = "^#([A-Fa-f0-9]{6})$";
    private static final Pattern pattern = Pattern.compile(WEB_COLOUR_PATTERN);

    /**
     * Validates that a given string is in the correct 6-digit hexadecimal format.
     *
     * @param colour The input string.
     * @return Whether the input string is valid.
     */
    public static boolean isValid(String colour){
        Matcher matcher;
        matcher = pattern.matcher(colour);
        return matcher.matches();
    }
}
