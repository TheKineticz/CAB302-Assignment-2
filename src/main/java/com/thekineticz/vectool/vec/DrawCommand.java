package com.thekineticz.vectool.vec;

import com.thekineticz.vectool.exception.VecCommandException;

import java.util.ArrayList;

/**
 * The internal representation of a VEC command that draws a figure.
 */
public class DrawCommand implements VecCommand{

    private CommandType commandType;
    private ArrayList<Double> args;

    /**
     * Creates a new draw command.
     *
     * @param commandType The type of command to be represented. Must be PLOT, LINE, RECTANGLE, ELLIPSE or POLYGON.
     * @param args The position arguments of the object that is to be drawn.
     * @throws VecCommandException Thrown when commandType is invalid or when the number of position arguments does not match the specification for the given command.
     */
    public DrawCommand(CommandType commandType, ArrayList<Double> args) throws VecCommandException {
        if (commandType == CommandType.PLOT){
            this.commandType = commandType;
            if (args.size() != 2){
                throw new VecCommandException("Command of type PLOT must have exactly 2 position arguments.");
            }
            this.args = args;
        }

        else if (commandType == CommandType.LINE){
            this.commandType = commandType;
            if (args.size() != 4){
                throw new VecCommandException("Command of type LINE must have exactly 4 position arguments.");
            }
            this.args = args;
        }

        else if (commandType == CommandType.RECTANGLE){
            this.commandType = commandType;
            if (args.size() != 4){
                throw new VecCommandException("Command of type RECTANGLE must have exactly 4 position arguments.");
            }
            this.args = args;
        }

        else if (commandType == CommandType.ELLIPSE){
            this.commandType = commandType;
            if (args.size() != 4){
                throw new VecCommandException("Command of type ELLIPSE must have exactly 4 position arguments.");
            }
            this.args = args;
        }

        else if (commandType == CommandType.POLYGON){
            this.commandType = commandType;
            if (args.size() % 2 != 0){
                throw new VecCommandException("Command of type POLYGON must have an even number of position arguments.");
            }
            this.args = args;
        }

        else {
            throw new VecCommandException(commandType.name() + " is an invalid command type for DrawCommand.");
        }
    }

    /**
     * Gets the type of draw command represented by the class.
     *
     * @return The type of draw command.
     */
    public CommandType getCommandType(){
        return commandType;
    }

    /**
     * Gets the position arguments array.
     *
     * @return The position arguments array.
     */
    public ArrayList<Double> getArgs(){
        return args;
    }

    /**
     * Gets the command as a fully-formed VEC string.
     *
     * @return The fully-formed VEC string.
     */
    @Override
    public String toString(){
        String str = commandType.name();

        for (Double position : args){
            str = str + " " + position.toString();
        }

        return str;
    }
}
