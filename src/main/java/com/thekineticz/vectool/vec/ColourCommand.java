package com.thekineticz.vectool.vec;

import com.thekineticz.vectool.exception.VecCommandException;

/**
 * The internal representation of a VEC command that does not draw anything, but effects the colour of things drawn after it.
 */
public class ColourCommand extends VecCommand{

    private String colour;

    /**
     * Creates a new colour command.
     *
     * @param commandType The type of command to be represented. Must be either PEN or FILL.
     * @param colour The colour argument of the command. Must be a valid 6-digit hexadecimal string with leading #, or 'OFF' if the command is type FILL.
     * @throws VecCommandException If commandType is not PEN or FILL, or the value for arg is invalid.
     */
    public ColourCommand(Commands.Type commandType, String colour) throws VecCommandException{
        ColourValidator colourValidator = new ColourValidator();

        if (!Commands.COLOUR_COMMAND_TYPES.contains(commandType)){
            throw new VecCommandException(commandType.name() + " is an invalid command type for ColourCommand.");
        }

        if (commandType == Commands.Type.PEN){
            this.commandType = commandType;
            if(!colourValidator.isValid(colour)){
                throw new VecCommandException(colour + " is an invalid 6-digit hexadecimal colour string.");
            }
            this.colour = colour;
        }

        else if (commandType == Commands.Type.FILL){
            this.commandType = commandType;
            if(!colour.equals("OFF") || !colourValidator.isValid(colour)){
                throw new VecCommandException(colour + " is neither 'OFF' nor a valid 6-digit hexadecimal colour string.");
            }
            this.colour = colour;
        }

        this.args = colour;
    }

    /**
     * Gets the colour value used in the command.
     *
     * @return The colour string.
     */
    public String getColour(){
        return colour;
    }
}