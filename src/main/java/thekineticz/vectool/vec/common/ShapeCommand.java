package thekineticz.vectool.vec.common;

import thekineticz.vectool.exception.VecCommandException;

import java.util.ArrayList;

/**
 * An extension of the VecCommand class for commands that take a fixed number of position arguments as their only arguments.
 */
public class ShapeCommand extends VecCommand {

    private ArrayList<Position> positions;

    /**
     * Construts a new shape command.
     *
     * @param commandName       The name of the command being created.
     * @param positions         The positions array of the command.
     * @param requiredPositions The amount of positions a command of the given type requires.
     * @throws VecCommandException Thrown if the amount of positions present in positions does not match requiredPositions.
     */
    public ShapeCommand(String commandName, ArrayList<Position> positions, int requiredPositions) throws VecCommandException {
        super(commandName);

        if (positions.size() != requiredPositions) {
            throw new VecCommandException(String.format("%s command must contain %d positions.", commandName, requiredPositions));
        }

        this.positions = positions;
    }

    /**
     * Get the positions of the shape.
     *
     * @return The positions of the shape.
     */
    public ArrayList<Position> getPositions() {
        return positions;
    }

    /**
     * Get the position arguments of the shape command in string form.
     *
     * @return The position arguments of the shape command in string form.
     */
    @Override
    public String getArgs() {
        StringBuilder string = new StringBuilder(positions.get(0).toString());
        for (int i = 1; i < positions.size(); i++) {
            string.append(" ");
            string.append(positions.get(i).toString());
        }

        return string.toString();
    }
}
