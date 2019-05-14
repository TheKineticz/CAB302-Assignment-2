package com.thekineticz.vectool.vec;

import java.util.ArrayList;

/**
 * The internal representation of a VEC command that draws a figure.
 */
public class DrawCommand implements VecCommand{

    private CommandType commandType;
    private ArrayList<Double> args;
}
