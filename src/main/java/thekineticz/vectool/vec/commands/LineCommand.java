package thekineticz.vectool.vec.commands;

import thekineticz.vectool.exception.VecCommandException;
import thekineticz.vectool.vec.common.Position;
import thekineticz.vectool.vec.common.ShapeCommand;

import java.util.ArrayList;

/**
 * The internal structure of a line vec command.
 */
public class LineCommand extends ShapeCommand {

    public static final String COMMAND_NAME = "LINE";
    private static final int REQUIRED_POSITION_VALUES = 4;
    private static final int REQUIRED_POSITIONS = 2;

    /**
     * Constructs a new LineCommand.
     *
     * @param positions The array of the line's endpoints.
     */
    public LineCommand(ArrayList<Position> positions) throws VecCommandException {
        super(COMMAND_NAME, positions, REQUIRED_POSITIONS);
    }

    /**
     * Constructs a LineCommand object from a line command string.
     *
     * @param command The command string.
     * @return The LineCommand object representing the command string.
     * @throws VecCommandException Thrown if an error occurs while parsing the string.
     */
    public static LineCommand fromString(String command) throws VecCommandException {
        String[] commandArray = command.split(" ");

        //Check the input string for valid amount of arguments
        if (commandArray.length - 1 != REQUIRED_POSITION_VALUES) {
            throw new VecCommandException(String.format("%s command string must contain %d arguments, separated by a space.", COMMAND_NAME, REQUIRED_POSITION_VALUES + 1));
        }

        //Check the input string for valid identifier
        if (!commandArray[0].equals(COMMAND_NAME)) {
            throw new VecCommandException(String.format("Attempted to generate %s command from string with incorrect identifier.", COMMAND_NAME));
        }

        ArrayList<Position> positions = new ArrayList<>();
        Double x, y;

        //Get positions from string arguments
        for (int i = 0; i < REQUIRED_POSITIONS; i++) {

            //Try to parse the position arguments
            try {
                x = Double.valueOf(commandArray[1 + i * 2]);
                y = Double.valueOf(commandArray[2 + i * 2]);
            } catch (Exception e) {
                throw new VecCommandException(String.format("Attempted to parse invalid argument for %s command position.", COMMAND_NAME), e);
            }
            positions.add(new Position(x, y));
        }

        return new LineCommand(positions);
    }
}
