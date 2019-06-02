package thekineticz.vectool.gui;

import thekineticz.vectool.vec.common.ColourHexMatcher;
import java.awt.Color;

/**
 * Helper class for converting hex colour strings to/from java.awt.Color
 */
class ColourHexConverter {

    /**
     * Converts a 6-digit hexadecimal colour string to Color object.
     *
     * @param hex The 6-digit hexadecimal colour string.
     * @return The RGB Color object.
     */
    public static Color hex2rgb(String hex){
        if (ColourHexMatcher.isValid(hex)){
            return new Color(
                    Integer.valueOf(hex.substring(1, 3), 16),
                    Integer.valueOf(hex.substring(3, 5), 16),
                    Integer.valueOf(hex.substring(5, 7), 16));
        }
        else {
            throw new IllegalArgumentException("Invalid hex colour string.");
        }
    }

    /**
     * Converts an RGB colour object to a 6-digit hexadecimal colour string.
     *
     * @param colour The RGB Color object.
     * @return The 6-digit hexadecimal colour string.
     */
    public static String rgb2hex(Color colour){
        return String.format("#%s%s%s",
                Integer.toHexString(colour.getRed()),
                Integer.toHexString(colour.getGreen()),
                Integer.toHexString(colour.getBlue()));
    }
}
