package com.thekineticz.vectool.vec;

import com.thekineticz.vectool.exception.VecCommandException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
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
    private String latestPenColour = DEFAULT_PEN_COLOUR;
    private String latestFillColour = DEFAULT_FILL_COLOUR;

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
        importFromFile(directory, filename);
        updateLatestColours();
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
    }

    /**
     * Adds a new command to the end of the command array.
     *
     * @param command The new command.
     */
    public void addCommand(VecCommand command){
        //If the command is a pen or fill command, record their colour values as latest
        if (command.getCommandType() == Commands.Type.PEN){
            String colour = command.getArgs();

            if (!latestPenColour.equals(colour)){
                latestPenColour = colour;
                commands.add(command);
            }
        }

        else if (command.getCommandType() == Commands.Type.FILL){
            String colour = command.getArgs();

            if (!latestFillColour.equals(colour)){
                latestFillColour = colour;
                commands.add(command);
            }
        }

        else {
            commands.add(command);
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
        exportToFile(directory, filename);
    }

    /**
     * Saves the current state of the VecFile to a new filepath.
     *
     * @param directory The directory where the file will be saved to.
     * @param filename The name of the file, not including extension.
     */
    public void saveAs(String directory, String filename){
        exportToFile(directory, filename);
    }

    /**
     * Writes the current state of the VecFile to a file.
     *
     * @param directory The directory where the file will be saved to.
     * @param filename The name of the file, not including extension.
     */
    private void exportToFile(String directory, String filename){
        String filePath = String.format("%s/%s.%s", directory, filename, FILE_EXTENSION);

        BufferedWriter writer = null;

        //Try to write all the commands in their string form to a new line in the file
        try {
            writer = new BufferedWriter(new FileWriter(filePath));

            if (!commands.isEmpty()){
                writer.write(commands.get(0).toString());

                for (int i = 1; i < commands.size(); i++){
                    writer.newLine();
                    writer.write(commands.get(i).toString());
                }
            }

            writer.close();
        }

        catch (IOException e){
            e.printStackTrace();
        }

        //Ensure the file is closed properly if an error occurs
        finally {
            try {
                if (writer != null){
                    writer.close();
                }
            }

            catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    /**
     * Generates a complete VecFile from an external file.
     *
     * @param directory The directory of the vec file.
     * @param filename The filename not including extension of the vec file.
     */
    private void importFromFile(String directory, String filename){
        String filePath = String.format("%s/%s.%s", directory, filename, FILE_EXTENSION);

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));

            String line = reader.readLine();
            while (line != null){
                try {
                    VecCommand command = generateVecCommandFromString(line);
                    commands.add(command);
                }
                catch (VecCommandException e){
                    e.printStackTrace();
                }

                line = reader.readLine();
            }
        }

        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Generates a VecCommand from a vec command in string form.
     *
     * @param commandString The full string of the command.
     * @return An initialised VecCommand.
     * @throws VecCommandException Thrown when some issue occurs when parsing.
     */
    private VecCommand generateVecCommandFromString(String commandString) throws VecCommandException {
        String[] commandArray = commandString.split(" ");

        if (commandArray.length == 0){
            throw new VecCommandException("Attempted to parse VecCommand from empty String.");
        }

        Commands.Type commandType;
        VecCommand command;

        //Try to get the command type from the string
        try {
            commandType = Commands.Type.valueOf(commandArray[0]);
        }
        catch (IllegalArgumentException e){
            throw new VecCommandException(String.format("Invalid value '%s' for VecCommand command type in file %s.'", commandArray[0], filename));
        }

        //Create VecCommand as DrawCommand if type is recognised as a draw command type.
        if (Commands.DRAW_COMMAND_TYPES.contains(commandType)){
            ArrayList<Double> positions = new ArrayList<>();
            for (int i = 1; i < commandArray.length; i++){
                String arg = commandArray[i];
                try {
                    positions.add(Double.valueOf(arg));
                }
                catch (NumberFormatException e){
                    throw new VecCommandException(String.format("Value '%s' for DrawCommand position could not be converted to type Double.", arg));
                }

            }

            command = new DrawCommand(commandType, positions);
        }

        //Create VecCommand as a ColourCommand if type is recognised as a colour command type.
        else if (Commands.COLOUR_COMMAND_TYPES.contains(commandType)){
            //Throw an error if the command string does not contain two discrete values.
            if (commandArray.length != 2){
                throw new VecCommandException(String.format("Command string '%s' contains an invalid amount of arguments for ColourCommand.", commandString));
            }

            command = new ColourCommand(commandType, commandArray[1]);
        }

        //If somehow a command type exists but does not belong to any set of types, throw an error.
        else {
            throw new VecCommandException(String.format("VecCommand type %s is not properly implemented.", commandType.name()));
        }

        return command;
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
