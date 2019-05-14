package com.thekineticz.vectool.vec;

import java.util.ArrayList;

/**
 * Represents a command that can exist in a VEC file.
 *
 * @param <T> The type of argument passed to the command.
 */
public interface VecCommand<T> {

    /**
     * Gets the type of command represented by the class.
     *
     * @return The command type represented by the class.
     */
    Commands.Type getCommandType();

    /**
     * Gets the arguments of the command represented by the class.
     *
     * @return The arguments of the command represented by the class.
     */
    ArrayList<T> getArgs();

    /**
     * Returns the command represented by the class as a fully-formed VEC command string.
     *
     * @return The fully-formed command represented by the class.
     */
    String toString();
}
