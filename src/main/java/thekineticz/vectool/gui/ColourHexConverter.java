package thekineticz.vectool.gui;

import thekineticz.vectool.vec.commands.FillCommand;
import thekineticz.vectool.vec.common.ColourHexMatcher;
import java.awt.Color;

/**
 * Helper class for converting hex colour strings to/from java.awt.Color
 */
class ColourHexConverter {

    /**
     * Converts a 6-digit hexadecimal colour string to Color object.
     *
     * @param hex The 6-digit hexadecimal colour string, or the FILL_OFF command.
     * @return The RGB Color object, null if the FILL_OFF command was registered.
     */
    static Color hex2rgb(String hex){
        if (ColourHexMatcher.isValid(hex)){
            return new Color(
                    Integer.valueOf(hex.substring(1, 3), 16),
                    Integer.valueOf(hex.substring(3, 5), 16),
                    Integer.valueOf(hex.substring(5, 7), 16));
        }
        else if (hex.equals(FillCommand.FILL_OFF)){
            return null;
        }
        else {
            throw new IllegalArgumentException("Invalid hex colour string.");
        }
    }

    /**
     * Converts an RGB colour object to a 6-digit hexadecimal colour string.
     *
     * @param colour The RGB Color object, or null.
     * @return The 6-digit hexadecimal colour string, or the FILL_OFF command if colour was null.
     */
    static String rgb2hex(Color colour){
        if (colour != null){
            return String.format("#%s%s%s",
                    String.format("%02X", colour.getRed()),
                    String.format("%02X", colour.getGreen()),
                    String.format("%02X", colour.getBlue()));
        }
        else {
            return FillCommand.FILL_OFF;
        }
    }
}
