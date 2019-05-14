package com.thekineticz.vectool.vec;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Regex pattern matcher for validating hexadecimal colour strings.
 */
class ColourValidator{

    private Pattern pattern;

    private static final String WEB_COLOUR_PATTERN = "^#([A-Fa-f0-9]{6})$";

    /**
     * Create a new ColourValidator.
     */
    ColourValidator(){
        pattern = Pattern.compile(WEB_COLOUR_PATTERN);
    }

    /**
     * Validates that a given string is in the correct 6-digit hexadecimal format.
     *
     * @param colour The input string.
     * @return Whether the input string is valid.
     */
    boolean isValid(String colour){
        Matcher matcher;
        matcher = pattern.matcher(colour);
        return matcher.matches();
    }
}
