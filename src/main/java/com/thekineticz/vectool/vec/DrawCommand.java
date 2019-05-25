package com.thekineticz.vectool.vec;

import com.thekineticz.vectool.exception.VecCommandException;

import java.util.ArrayList;

/**
 * The internal representation of a VEC command that draws a figure.
 */
public class DrawCommand extends VecCommand {

    private ArrayList<Double> positions;

    /**
     * Creates a new draw command.
     *
     * @param commandType The type of command to be represented. Must be PLOT, LINE, RECTANGLE, ELLIPSE or POLYGON.
     * @param positions The position arguments of the object that is to be drawn.
     * @throws VecCommandException Thrown when commandType is invalid, when the number of position arguments does not match the specification for the given command or when a command specification is not implemented.
     */
    public DrawCommand(Commands.Type commandType, ArrayList<Double> positions) throws VecCommandException {
        super(commandType);

        if (!Commands.DRAW_COMMAND_TYPES.contains(commandType)){
            throw new VecCommandException(commandType.name() + " is an invalid command type for DrawCommand.");
        }

        if (commandType == Commands.Type.POLYGON){
            if (positions.isEmpty() || positions.size() % 2 != 0){
                throw new VecCommandException("Draw command of type POLYGON must contain at least two position arguments, and must have an even number of arguments.");
            }
        }

        else if (Commands.DRAW_COMMAND_POSITION_ARGUMENTS.containsKey(commandType)){
            Integer nRequiredArguments = Commands.DRAW_COMMAND_POSITION_ARGUMENTS.get(commandType);
            if (positions.size() != nRequiredArguments){
                throw new VecCommandException(String.format("Draw command of type %s must contain exactly %d position arguments.", commandType.name(), nRequiredArguments));
            }
        }

        else {
            throw new VecCommandException(String.format("Draw command of type %s has no defined rule for validating position arguments. Please define a rule.", commandType.name()));
        }

        this.positions = positions;
    }

    /**
     * Gets the position arguments array.
     *
     * @return The position arguments array.
     */
    public ArrayList<Double> getPositions(){
        return positions;
    }

    /**
     * Gets the position arguments in string form.
     *
     * @return The position arguments in string form.
     */
    public String getArgs(){
        StringBuilder stringPositions = new StringBuilder();

        if (!positions.isEmpty()){
            stringPositions.append(positions.get(0).toString());

            for (int i = 1; i < positions.size(); i++){
                stringPositions.append(" ");
                stringPositions.append(positions.get(i).toString());
            }
        }

        return stringPositions.toString();
    }
}
