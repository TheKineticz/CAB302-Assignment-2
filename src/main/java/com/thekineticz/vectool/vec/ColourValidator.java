package com.thekineticz.vectool.vec;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Regex pattern matcher for validating hexadecimal colour strings.
 */
public class ColourValidator{

    private Pattern pattern;
    private Matcher matcher;

    private static final String WEB_COLOUR_PATTERN = "^#([A-Fa-f0-9]{6})$";

    /**
     * Create a new ColourValidator.
     */
    public ColourValidator(){
        pattern = Pattern.compile(WEB_COLOUR_PATTERN);
    }

    /**
     * Validates that a given string is in the correct 6-digit hexadecimal format.
     *
     * @param colour The input string.
     * @return Whether the input string is valid.
     */
    public boolean validate(String colour){
        matcher = pattern.matcher(colour);
        return matcher.matches();
    }
}
