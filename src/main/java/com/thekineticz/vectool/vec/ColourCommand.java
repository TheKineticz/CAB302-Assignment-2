package com.thekineticz.vectool.vec;

import com.thekineticz.vectool.exception.VecCommandException;

import java.util.ArrayList;

/**
 * The internal representation of a VEC command that does not draw anything, but effects the colour of things drawn after it.
 */
public class ColourCommand implements VecCommand<String>{

    private Commands.Type commandType;
    private ArrayList<String> args = new ArrayList<>();
    private ColourValidator colourValidator = new ColourValidator();

    /**
     * Creates a new colour command.
     *
     * @param commandType The type of command to be represented. Must be either PEN or FILL.
     * @param arg The colour argument of the command. Must be a valid 6-digit hexadecimal string with leading #, or 'OFF' if the command is type FILL.
     * @throws VecCommandException If commandType is not PEN or FILL, or the value for arg is invalid.
     */
    public ColourCommand(Commands.Type commandType, String arg) throws VecCommandException{
        if (!Commands.COLOUR_COMMAND_TYPES.contains(commandType)){
            throw new VecCommandException(commandType.name() + " is an invalid command type for ColourCommand.");
        }

        if (commandType == Commands.Type.PEN){
            this.commandType = commandType;
            if(!colourValidator.validate(arg)){
                throw new VecCommandException(arg + " is an invalid 6-digit hexadecimal colour string.");
            }
            args.add(arg);
        }

        else if (commandType == Commands.Type.FILL){
            this.commandType = commandType;
            if(arg != "OFF" || !colourValidator.validate(arg)){
                throw new VecCommandException(arg + " is neither 'OFF' nor a valid 6-digit hexadecimal colour string.");
            }
            args.add(arg);
        }
    }

    /**
     * Gets the type of colour command represented by the class.
     *
     * @return The command type.
     */
    public Commands.Type getCommandType(){
        return commandType;
    }

    /**
     * Gets the arguments of the colour command.
     *
     * @return The arguments of the command.
     */
    public ArrayList<String> getArgs(){
        return args;
    }

    /**
     * Gets the command as a fully-formed VEC string.
     *
     * @return The command as a fully-formed VEC string.
     */
    @Override
    public String toString(){
        return String.format("%s %s", commandType.name(), args.get(0));
    }
}