package com.thekineticz.vectool.vec;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The internal representation of a VEC file that contains the file's name and array of commands.
 * When creating or loading a VEC file, an instance of this will be generated for the program to work with internally.
 * When saved, an instance of this class will be exported in the standard VEC file format.
 */
public class VecFile {

    private static final String FILE_EXTENSION = "vec";
    private static final String FILL_OFF = "OFF";
    private static final String DEFAULT_PEN_COLOUR = "#000000";
    private static final String DEFAULT_FILL_COLOUR = FILL_OFF;

    private String directory;
    private String filename;
    private ArrayList<VecCommand> commands;
    private String latestPenColour;
    private String latestFillColour;

    /**
     * Creates an internal VEC class from a file.
     *
     * @param directory The directory of the VEC file that is being imported.
     * @param filename The filename of the VEC file that is being imported.
     */
    public VecFile(String directory, String filename){
        this.directory = directory;
        this.filename = filename;
        commands = new ArrayList<>();
        //TODO: Implement vec file importing
    }

    /**
     * Creates a brand new internal VEC class.
     *
     * @param filename The filename of the VEC file that is being created.
     */
    public VecFile(String filename){
        this.directory = null;
        this.filename = filename;
        commands = new ArrayList<>();
        latestPenColour = DEFAULT_PEN_COLOUR;
        latestFillColour = DEFAULT_FILL_COLOUR;
    }

    /**
     * Adds a new command to the end of the command array.
     *
     * @param command The new command.
     */
    public void addCommand(VecCommand command){
        commands.add(command);

        //If the command is a pen or fill command, record their colour values as latest
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

            //Remove any colour commands at the end of the command list
            while (!commands.isEmpty() && commands.get(commands.size() - 1) instanceof ColourCommand){
                commands.remove(commands.size() - 1);
            }

            //Ensure the latest colour values are updated after removing colour commands
            updateLatestColours();
        }
    }

    /**
     * Saves the current state of the VecFile to it's stored filepath.
     */
    public void save(){
        writeToFile(directory, filename);
    }

    /**
     * Saves the current state of the VecFile to a new filepath.
     *
     * @param directory The directory where the file will be saved to.
     * @param filename The name of the file, not including extension.
     */
    public void saveAs(String directory, String filename){
        writeToFile(directory, filename);
    }

    /**
     * Writes the current state of the VecFile to a file.
     *
     * @param directory The directory where the file will be saved to.
     * @param filename The name of the file, not including extension.
     */
    private void writeToFile(String directory, String filename){
        String filePath = String.format("%s/%s.%s", directory, filename, FILE_EXTENSION);

        BufferedWriter bufferedWriter = null;

        //Try to write all the commands in their string form to a new line in the file
        try{
            bufferedWriter = new BufferedWriter(new FileWriter(filePath));

            if (!commands.isEmpty()){
                bufferedWriter.write(commands.get(0).toString());

                for (int i = 1; i < commands.size(); i++){
                    bufferedWriter.newLine();
                    bufferedWriter.write(commands.get(i).toString());
                }
            }

            bufferedWriter.close();
        }

        catch (IOException e){
            e.printStackTrace();
        }

        //Ensure the file is closed properly if an error occurs
        finally{
            try{
                bufferedWriter.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    /**
     * Updates the latest colour fields with the current values derived from the commands array.
     */
    private void updateLatestColours(){
        int index = commands.size() - 1;
        boolean isPenColourFound = false;
        boolean isFillColourFound = false;

        //Iterates backwards through the commands array and records the first pen and fill colours located
        //Early exits if pen and fill colours are found before reaching the end of the array
        while (index >= 0 && !(isPenColourFound && isFillColourFound)){
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

        //If no pen or fill command was located, reset the latest colours to defaults
        if (!isPenColourFound){
            latestPenColour = DEFAULT_PEN_COLOUR;
        }

        if (!isFillColourFound){
            latestFillColour = DEFAULT_FILL_COLOUR;
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
