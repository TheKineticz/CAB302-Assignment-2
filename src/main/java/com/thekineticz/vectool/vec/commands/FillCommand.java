package com.thekineticz.vectool.vec.commands;

import com.thekineticz.vectool.exception.VecCommandException;
import com.thekineticz.vectool.vec.common.*;

/**
 * The internal structure of a fill vec command.
 */
public class FillCommand extends VecCommand {

    public static final String COMMAND_NAME = "FILL";
    private static final String FILL_OFF = "OFF";
    private static final int REQUIRED_ARGUMENTS = 1;

    private String colour;

    /**
     * Constructs a new fill command.
     *
     * @param colour The 6-digit hexadecimal colour value for the fill, or the OFF command.
     * @throws VecCommandException Thrown if the colour arguments is an invalid value.
     */
    public FillCommand(String colour) throws VecCommandException {
        super(COMMAND_NAME);

        if (!(colour.equals(FILL_OFF) || ColourHexMatcher.isValid(colour))){
            throw new VecCommandException(String.format("Invalid colour argument '%s' for %s command.", colour, COMMAND_NAME));
        }

        this.colour = colour;
    }

    /**
     * Gets the fill colour.
     *
     * @return The fill colour.
     */
    public String getColour(){
        return colour;
    }

    /**
     * Inherited alias to getColour.
     *
     * @return The fill colour.
     */
    public String getArgs(){
        return getColour();
    }

    /**
     * Constructs a new fill command from a vec command string.
     *
     * @param command The vec command string.
     * @return The FillCommand object representing the command string.
     * @throws VecCommandException Thrown if an error occurs while parsing the string.
     */
    public static PenCommand fromString(String command) throws VecCommandException {
        String[] commandArray = command.split(" ");

        if (commandArray.length - 1 != REQUIRED_ARGUMENTS){
            throw new VecCommandException(String.format("%s command string must contain exactly %d arguments", COMMAND_NAME, REQUIRED_ARGUMENTS));
        }

        if (!commandArray[0].equals(COMMAND_NAME)){
            throw new VecCommandException("Attempted to generate FillCommand from string with incorrect identifier.");
        }

        return new PenCommand(commandArray[1]);
    }
}
