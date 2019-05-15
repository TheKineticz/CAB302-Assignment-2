package com.thekineticz.vectool.vec;

/**
 * Represents a command that can exist in a VEC file.
 */
public abstract class VecCommand {

    private Commands.Type commandType;

    /**
     * Creates a new VecCommand with the command type initialised.
     *
     * @param commandType The type of command to be represented.
     */
    VecCommand(Commands.Type commandType){
        this.commandType = commandType;
    }

    /**
     * Gets the type of command represented by the class.
     *
     * @return The command type represented by the class.
     */
    public Commands.Type getCommandType(){
        return commandType;
    }

    /**
     * Gets the arguments of the command represented by the class in string form.
     *
     * @return The arguments of the command represented by the class in string form.
     */
    public abstract String getArgs();

    /**
     * Returns the command represented by the class as a fully-formed VEC command string.
     *
     * @return The fully-formed command represented by the class.
     */
    @Override
    public String toString(){
        return String.format("%s %s", commandType.name(), getArgs());
    }
}
