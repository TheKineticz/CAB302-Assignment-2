package com.thekineticz.vectool.vec;

import java.util.ArrayList;

/**
 * The internal representation of a VEC file that contains the file's name and array of commands.
 * When creating or loading a VEC file, an instance of this will be generated for the program to work with internally.
 * When saved, an instance of this class will be exported in the standard VEC file format.
 */
public class VecFile {

    String filename;
    ArrayList<VecCommand> commands;
    String latestPenColour;
    String latestFillColour;

    /**
     * Creates a new internal VEC class with default values.
     *
     * @param filename The filename of the VEC file that is being created.
     */
    public VecFile(String filename){
        this.filename = filename;
        commands = new ArrayList<>();
        latestPenColour = "#000000";
        latestFillColour = "OFF";
    }

    /**
     * Adds a new command to the end of the command array.
     *
     * @param command The new command.
     */
    public void addCommand(VecCommand command){
        commands.add(command);

        if (command.getCommandType() == Commands.Type.PEN){
            latestPenColour = command.getArgs().get(0).toString();
        }

        else if (command.getCommandType() == Commands.Type.FILL){
            latestFillColour = command.getArgs().get(0).toString();
        }
    }

    /**
     * Removes the last command in the command array, including any colouring commands that prefixed it.
     */
    public void undoLatestCommand(){
        if (!commands.isEmpty()){
            commands.remove(commands.size() - 1);

            while (!commands.isEmpty() && commands.get(commands.size() - 1) instanceof ColourCommand){
                commands.remove(commands.size() - 1);
            }

            updateLatestColours();
        }
    }

    /**
     * Updates the latest colour fields with the current values derived from the commands array.
     */
    private void updateLatestColours(){
        int index = commands.size() - 1;
        boolean isPenColourFound = false;
        boolean isFillColourFound = false;

        while ( index >= 0 && (!isPenColourFound || !isFillColourFound)){
            VecCommand current = commands.get(index);

            if (current.getCommandType() == Commands.Type.PEN) {
                isPenColourFound = true;
                latestPenColour = current.getArgs().get(0).toString();
            }

            else if (current.getCommandType() == Commands.Type.FILL) {
                isFillColourFound = true;
                latestFillColour = current.getArgs().get(0).toString();
            }

            index--;
        }

        if (!isPenColourFound){
            latestPenColour = "#000000";
        }

        if (!isFillColourFound){
            latestFillColour = "OFF";
        }
    }

}
