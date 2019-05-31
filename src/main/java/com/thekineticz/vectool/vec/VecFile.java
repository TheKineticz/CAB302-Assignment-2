package com.thekineticz.vectool.vec;

import com.thekineticz.vectool.exception.*;
import com.thekineticz.vectool.vec.common.*;
import com.thekineticz.vectool.vec.commands.*;

import java.io.*;
import java.util.ArrayList;

/**
 * The internal representation of a VEC file that contains the file's name and array of commands.
 * When creating or loading a VEC file, an instance of this will be generated for the program to work with internally.
 * When saved, an instance of this class will be exported in the standard VEC file format.
 */
public class VecFile {

    public static final String FILE_EXTENSION = "VEC";
    private static final String FILL_OFF = FillCommand.FILL_OFF;
    private static final String DEFAULT_PEN_COLOUR = "#000000";
    private static final String DEFAULT_FILL_COLOUR = FILL_OFF;

    private String directory;
    private String filename;
    private boolean isSaved;
    private ArrayList<VecCommand> commands;
    private String latestPenColour = DEFAULT_PEN_COLOUR;
    private String latestFillColour = DEFAULT_FILL_COLOUR;

    /**
     * Creates an internal VEC class from a file.
     *
     * @param file The file being imported.
     */
    public VecFile(File file) throws VecCommandException, IOException{
        this.directory = file.getParent();
        this.filename = file.getName().replaceFirst("[.][^.]+$", ""); //Removes the extension from the file name
        isSaved = true;
        commands = new ArrayList<>();
        open(file);
        updateLatestColours();
    }

    /**
     * Creates an internal VEC class from a file.
     *
     * @param directory The directory of the VEC file that is being imported.
     * @param filename The filename of the VEC file that is being imported.
     */
    public VecFile(String directory, String filename) throws VecCommandException, IOException{
        this.directory = directory;
        this.filename = filename;
        isSaved = true;
        commands = new ArrayList<>();
        open(directory, filename);
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
        isSaved = false;
        commands = new ArrayList<>();
    }

    /**
     * Adds a new command to the end of the command array.
     *
     * @param command The new command.
     */
    public void addCommand(VecCommand command){

        //Update the latest colour values if the command is a pen or fill type command
        if (command instanceof PenCommand){
            latestPenColour = ((PenCommand) command).getColour();
        }

        else if (command instanceof FillCommand){
            latestFillColour = ((FillCommand) command).getColour();
        }

        commands.add(command);
    }

    /**
     * Removes the last command in the command array.
     */
    public void undoLatestCommand(){
        if (!commands.isEmpty()){
            commands.remove(commands.size() - 1);
            updateLatestColours();
        }
    }

    /**
     * Saves the current state of the VecFile to it's stored filepath.
     *
     * @throws VecIOException Thrown if the function is called on a new VecFile that does not have an associated directory.
     * @throws IOException Thrown if an error occurs while writing to file.
     */
    public void save() throws VecIOException, IOException{
        if (directory != null){
            exportToFile(new File(String.format("%s/%s.%s", directory, filename, FILE_EXTENSION)));
            isSaved = true;
        }
        else {
            throw new VecIOException("New file must have a directory specified with saveAs.");
        }

    }

    /**
     * Saves the current state of the VecFile to a new filepath.
     *
     * @param directory The directory where the file will be saved to.
     * @param filename The name of the file, not including extension.
     */
    public void saveAs(String directory, String filename) throws IOException{
        exportToFile(new File(String.format("%s/%s.%s", directory, filename, FILE_EXTENSION)));
        isSaved = true;
    }

    /**
     * Saves the current state of the VecFile to a new filepath.
     *
     * @param file The file to be saved to.
     */
    public void saveAs(File file) throws IOException{
        if (file.toString().endsWith(VecFile.FILE_EXTENSION)){
            exportToFile(file);
        }
        else {
            exportToFile(new File(String.format("%s.%s", file.getAbsolutePath(), VecFile.FILE_EXTENSION)));
        }

        isSaved = true;
    }

    /**
     * Writes the current state of the VecFile to a file.
     *
     * @param file The file to be saved to.
     * @throws IOException Thrown if an error occurs while writing to file.
     */
    private void exportToFile(File file) throws IOException{
        //Try to write all the commands in their string form to a new line in the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            if (!commands.isEmpty()) {
                writer.write(commands.get(0).toString());

                for (int i = 1; i < commands.size(); i++) {
                    writer.newLine();
                    writer.write(commands.get(i).toString());
                }
            }
        }

    }

    /**
     * Imports a vec file.
     *
     * @param file The file being imported.
     * @throws VecCommandException Thrown if an issue occurs while parsing a command.
     * @throws IOException Thrown if an issue occurs during file IO.
     */
    private void open(File file) throws VecCommandException, IOException {
        importFromFile(file);
    }

    /**
     * Imports a vec file.
     *
     * @param directory The directory file being imported.
     * @param filename The name without extension of the file being imported.
     * @throws VecCommandException Thrown if an issue occurs while parsing a command.
     * @throws IOException Thrown if an issue occurs during file IO.
     */
    private void open(String directory, String filename) throws VecCommandException, IOException {
        importFromFile(new File(String.format("%s/%s.%s", directory, filename, FILE_EXTENSION)));
    }

    /**
     * Generates a complete VecFile from an external file.
     *
     * @param file The vec file.
     * @throws VecCommandException Thrown if an issue occurs while parsing a command.
     * @throws IOException Thrown if an issue occurs during file IO.
     */
    private void importFromFile(File file) throws VecCommandException, IOException{
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line = reader.readLine();

            while (line != null){
                VecCommand command = parseVecCommand(line);
                commands.add(command);
                line = reader.readLine();
            }
        }
    }

    /**
     * Generates a VecCommand from a vec command in string form.
     *
     * @param commandString The full string of the command.
     * @return An initialised VecCommand.
     * @throws VecCommandException Thrown if an invalid vec command string is input.
     */
    private VecCommand parseVecCommand(String commandString) throws VecCommandException {
        if (commandString.startsWith(PlotCommand.COMMAND_NAME)){
            return PlotCommand.fromString(commandString);
        }

        else if (commandString.startsWith(LineCommand.COMMAND_NAME)){
            return LineCommand.fromString(commandString);
        }

        else if (commandString.startsWith(RectangleCommand.COMMAND_NAME)){
            return RectangleCommand.fromString(commandString);
        }

        else if (commandString.startsWith(EllipseCommand.COMMAND_NAME)){
            return EllipseCommand.fromString(commandString);
        }

        else if (commandString.startsWith(PolygonCommand.COMMAND_NAME)){
            return PolygonCommand.fromString(commandString);
        }

        else if (commandString.startsWith(PenCommand.COMMAND_NAME)){
            return PenCommand.fromString(commandString);
        }

        else if (commandString.startsWith(FillCommand.COMMAND_NAME)){
            return FillCommand.fromString(commandString);
        }

        else {
            throw new VecCommandException("Attempted to convert string without a valid identifier to VecCommand.");
        }
    }

    /**
     * Updates the latest colour fields with the current values derived from the commands array.
     */
    private void updateLatestColours(){
        boolean isPenColourFound = false;
        boolean isFillColourFound = false;

        for (int i = commands.size() - 1; i >= 0; i--){
            VecCommand command = commands.get(i);

            if (!isPenColourFound && command instanceof PenCommand){
                latestPenColour = ((PenCommand) command).getColour();
                isPenColourFound = true;
            }

            else if (!isFillColourFound && command instanceof FillCommand){
                latestFillColour = ((FillCommand) command).getColour();
                isFillColourFound = true;
            }

            if (isPenColourFound && isFillColourFound){
                break;
            }
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
     * Gets the commands of the VEC file.
     *
     * @return The commands of the VEC file.
     */
    public ArrayList<VecCommand> getCommands(){
        return commands;
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

    /**
     * Gets whether the external file is up-to-date with the internal file.
     *
     * @return The boolean corresponding to whether the saved file is up-to-date.
     */
    public boolean isSaved(){
        return isSaved;
    }

}