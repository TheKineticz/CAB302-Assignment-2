package com.thekineticz.vectool.vec.commands;

import com.thekineticz.vectool.exception.VecCommandException;

import java.util.ArrayList;

public class RectangleCommand extends VecCommand {

    private static final String COMMAND_NAME = "RECTANGLE";
    private static final int REQUIRED_POSITION_VALUES = 4;
    private static final int REQUIRED_POSITIONS = 2;

    private ArrayList<Position<Double>> positions;

    /**
     * Constructs a new RectangleCommand.
     *
     * @param positions The array of the rectangle's top-left and bottom-right points.
     */
    public RectangleCommand(ArrayList<Position<Double>> positions) throws VecCommandException {
        super(COMMAND_NAME);

        if (positions.size() != REQUIRED_POSITIONS){
            throw new VecCommandException(String.format("%s command must contain %d positions.", COMMAND_NAME, REQUIRED_POSITIONS));
        }

        this.positions = positions;
    }

    /**
     * Get the positions of the rectangle.
     *
     * @return The positions of the rectangle.
     */
    public ArrayList<Position<Double>> getPositions(){
        return positions;
    }

    /**
     * Get the position arguments of the rectangle command in string form.
     *
     * @return The position arguments of the rectangle command in string form.
     */
    @Override
    public String getArgs(){
        StringBuilder string = new StringBuilder(positions.get(0).toString());
        for (int i = 1; i < positions.size(); i++){
            string.append(" ");
            string.append(positions.get(i).toString());
        }

        return string.toString();
    }

    /**
     * Constructs a RectangleCommand object from a line command string.
     *
     * @param command The command string.
     * @return The RectangleCommand object representing the command string.
     * @throws VecCommandException Thrown if an error occurs while parsing the string.
     */
    public static RectangleCommand fromString(String command) throws VecCommandException {
        String[] commandArray = command.split(" ");

        //Check the input string for valid amount of arguments
        if (commandArray.length - 1 != REQUIRED_POSITION_VALUES){
            throw new VecCommandException(String.format("%s command string must contain %d arguments, separated by a space.", COMMAND_NAME, REQUIRED_POSITION_VALUES + 1));
        }

        //Check the input string for valid identifier
        if (!commandArray[0].equals(COMMAND_NAME)){
            throw new VecCommandException("Attempted to generate RectangleCommand from string with incorrect identifier.");
        }

        ArrayList<Position<Double>> positions = new ArrayList<>();
        Double x;
        Double y;

        //Get positions from string arguments
        for (int i = 0; i < REQUIRED_POSITIONS; i++){

            //Try to parse the position arguments
            try {
                x = Double.valueOf(commandArray[1 + i * 2]);
                y = Double.valueOf(commandArray[2 + i * 2]);
            }
            catch (Exception e){
                throw new VecCommandException(String.format("Attempted to parse invalid argument for %s command position.", COMMAND_NAME), e);
            }
            positions.add(new Position<>(x, y));
        }

        return new RectangleCommand(positions);
    }
}
