package com.thekineticz.vectool.vec.common;

import com.thekineticz.vectool.exception.VecCommandException;

import java.util.ArrayList;

public class ShapeCommand extends VecCommand {

    protected ArrayList<Position<Double>> positions;

    public ShapeCommand(String commandName, ArrayList<Position<Double>> positions, int requiredPositions) throws VecCommandException {
        super(commandName);

        if (positions.size() != requiredPositions){
            throw new VecCommandException(String.format("%s command must contain %d positions.", commandName, requiredPositions));
        }

        this.positions = positions;
    }

    /**
     * Get the positions of the shape.
     *
     * @return The positions of the shape.
     */
    public ArrayList<Position<Double>> getPositions(){
        return positions;
    }

    /**
     * Get the position arguments of the shape command in string form.
     *
     * @return The position arguments of the shape command in string form.
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
}
