package com.thekineticz.vectool.vec;

/**
 * Represents a command that can exist in a VEC file.
 */
abstract class VecCommand{

    Commands.Type commandType;
    String args;

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
    public String getArgs(){
        return args;
    }

    /**
     * Returns the command represented by the class as a fully-formed VEC command string.
     *
     * @return The fully-formed command represented by the class.
     */
    @Override
    public String toString(){
        return String.format("%s %s", commandType.name(), args);
    }
}
