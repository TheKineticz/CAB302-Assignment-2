package com.thekineticz.vectool.vec;

import java.util.ArrayList;

/**
 * The internal representation of a VEC file that contains the file's name and array of commands.
 * When creating or loading a VEC file, an instance of this will be generated for the program to work with internally.
 * When saved, an instance of this class will be exported in the standard VEC file format.
 */
public class VecFile {

    private String filename;
    private ArrayList<VecCommand> commands;
    private String latestPenColour;
    private String latestFillColour;

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
            latestPenColour = command.getArgs();
        }

        else if (command.getCommandType() == Commands.Type.FILL){
            latestFillColour = command.getArgs();
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

        while (index >= 0 && (!isPenColourFound || !isFillColourFound)){
            VecCommand current = commands.get(index);

            if (current.getCommandType() == Commands.Type.PEN) {
                isPenColourFound = true;
                latestPenColour = current.getArgs();
            }

            else if (current.getCommandType() == Commands.Type.FILL) {
                isFillColourFound = true;
                latestFillColour = current.getArgs();
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

    /**
     * Gets the name of the VEC file.
     *
     * @return The name of the VEC file.
     */
    public String getFilename(){
        return filename;
    }

    /**
     * Gets the latest pen colour that was set, in 6-digit hexadecimal string form.
     *
     * @return The colour.
     */
    public String getLatestPenColour(){
        return latestPenColour;
    }

    /**
     * Gets the latest fill colour that was set as 'OFF' or a colour in 6-digit hexadecimal string form.
     *
     * @return The colour or the string 'OFF'.
     */
    public String getLatestFillColour(){
        return latestFillColour;
    }

}
