package com.thekineticz.vectool.vec;

import com.thekineticz.vectool.exception.VecCommandException;

import java.util.ArrayList;

/**
 * The internal representation of a VEC command that draws a figure.
 */
public class DrawCommand extends VecCommand{

    private ArrayList<Double> positions;

    /**
     * Creates a new draw command.
     *
     * @param commandType The type of command to be represented. Must be PLOT, LINE, RECTANGLE, ELLIPSE or POLYGON.
     * @param positions The position arguments of the object that is to be drawn.
     * @throws VecCommandException Thrown when commandType is invalid or when the number of position arguments does not match the specification for the given command.
     */
    public DrawCommand(Commands.Type commandType, ArrayList<Double> positions) throws VecCommandException {
        if (!Commands.DRAW_COMMAND_TYPES.contains(commandType)){
            throw new VecCommandException(commandType.name() + " is an invalid command type for DrawCommand.");
        }

        this.commandType = commandType;

        if (commandType == Commands.Type.POLYGON){
            if (positions.isEmpty() || positions.size() % 2 != 0){
                throw new VecCommandException("Draw command of type POLYGON must contain at least two position arguments, and must have an even number of arguments.");
            }
        }

        else {
            Integer nRequiredArguments = Commands.DRAW_COMMAND_POSITION_ARGUMENTS.get(commandType);
            if (positions.size() != nRequiredArguments){
                throw new VecCommandException(String.format("Draw command of type %s must contain exactly %d position arguments.", commandType.name(), nRequiredArguments));
            }
        }

        this.positions = positions;

        StringBuilder stringPositions = new StringBuilder(positions.get(0).toString());
        int i = 1;
        while (i < positions.size()){
            stringPositions.append(" ");
            stringPositions.append(positions.get(i).toString());
        }
        args = stringPositions.toString();
    }

    /**
     * Gets the position arguments array.
     *
     * @return The position arguments array.
     */
    public ArrayList<Double> getPositions(){
        return positions;
    }
}
