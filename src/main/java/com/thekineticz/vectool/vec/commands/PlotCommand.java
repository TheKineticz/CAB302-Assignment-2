package com.thekineticz.vectool.vec.commands;

import com.thekineticz.vectool.exception.VecCommandException;
import com.thekineticz.vectool.vec.common.*;

/**
 * The internal structure of a plot vec command.
 */
public class PlotCommand extends VecCommand {

    public static final String COMMAND_NAME = "PLOT";
    private static final int REQUIRED_POSITION_VALUES = 2;

    private Position<Double> position;

    /**
     * Constructs a new PlotCommand.
     *
     * @param position The position of the plot.
     */
    public PlotCommand(Position<Double> position){
        super(COMMAND_NAME);
        this.position = position;
    }

    /**
     * Get the position of the plot.
     *
     * @return The position of the plot.
     */
    public Position getPositions(){
        return position;
    }

    /**
     * Get the arguments of the plot command.
     *
     * @return The position of the plot in string form.
     */
    public String getArgs(){
        return position.toString();
    }

    /**
     * Constructs a PlotCommand object from a plot command string.
     *
     * @param command The command string.
     * @return The PlotCommand object representing the command string.
     * @throws VecCommandException Thrown if an error occurs while parsing the string.
     */
    public static PlotCommand fromString(String command) throws VecCommandException {
        String[] commandArray = command.split(" ");

        //Check the input string for valid amount of arguments
        if (commandArray.length - 1 != REQUIRED_POSITION_VALUES){
            throw new VecCommandException(String.format("%s command string must contain %d arguments, separated by a space.", COMMAND_NAME, REQUIRED_POSITION_VALUES + 1));
        }

        //Check the input string for valid identifier
        if (!commandArray[0].equals(COMMAND_NAME)){
            throw new VecCommandException("Attempted to generate PlotCommand from string with incorrect identifier.");
        }

        Double x, y;

        //Try to parse the position arguments
        try {
            x = Double.valueOf(commandArray[1]);
            y = Double.valueOf(commandArray[2]);
        }
        catch (Exception e){
            throw new VecCommandException(String.format("Attempted to parse invalid argument for %s command position.", COMMAND_NAME), e);
        }

        return new PlotCommand(new Position<>(x, y));
    }
}
