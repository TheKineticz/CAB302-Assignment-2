package com.thekineticz.vectool.vec.commands;

/**
 * The basic interface for the internal representation of a line in a vec file.
 */
public interface VecCommand{

    /**
     * Takes a vec command string input and gets the internal VecCommand object representing it.
     *
     * @param command The vec command in string form.
     * @return The internal VecCommand object representing the input command.
     */
    VecCommand fromString(String command);

    /**
     * Gets the command in its string form.
     *
     * @return The VecCommand in its string form.
     */
    @Override
    String toString();
}
