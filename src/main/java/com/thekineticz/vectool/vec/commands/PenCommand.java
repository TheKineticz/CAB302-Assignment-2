package com.thekineticz.vectool.vec.commands;

import com.thekineticz.vectool.exception.VecCommandException;

/**
 * The internal structure of a pen vec command.
 */
public class PenCommand extends VecCommand {

    public static final String COMMAND_NAME = "PEN";
    private static final int REQUIRED_ARGUMENTS = 1;

    private String colour;

    /**
     * Constructs a new pen command.
     *
     * @param colour The 6-digit hexadecimal colour value for the pen.
     * @throws VecCommandException Thrown if an invalid value is entered for the colour.
     */
    public PenCommand(String colour) throws VecCommandException {
        super(COMMAND_NAME);

        if (!ColourHexMatcher.isValid(colour)){
            throw new VecCommandException(String.format("Invalid colour argument '%s' for %s command.", colour, COMMAND_NAME));
        }

        this.colour = colour;
    }

    /**
     * Gets the colour of the pen.
     *
     * @return The colour of the pen.
     */
    public String getColour(){
        return colour;
    }

    /**
     * Inherited alias to getColour.
     *
     * @return The colour of the pen.
     */
    public String getArgs(){
        return getColour();
    }

    /**
     * Constructs a new pen command from a vec command string.
     *
     * @param command The vec command string.
     * @return The PenCommand object representing the command string.
     * @throws VecCommandException Thrown if an error occurs while parsing the string.
     */
    public static PenCommand fromString(String command) throws VecCommandException {
        String[] commandArray = command.split(" ");

        if (commandArray.length - 1 != REQUIRED_ARGUMENTS){
            throw new VecCommandException(String.format("%s command string must contain exactly %d arguments", COMMAND_NAME, REQUIRED_ARGUMENTS));
        }

        if (!commandArray[0].equals(COMMAND_NAME)){
            throw new VecCommandException("Attempted to generate PenCommand from string with incorrect identifier.");
        }

        return new PenCommand(commandArray[1]);
    }
}
