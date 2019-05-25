package com.thekineticz.vectool.vec;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

/**
 * Container for command-related objects.
 */
public class Commands {

    /**
     *  The types of commands that could be represented by a VecCommand class.
     */
    public enum Type{
        PLOT, LINE, RECTANGLE, ELLIPSE, POLYGON, PEN, FILL
    }

    /**
     * Constructs a set containing all the commands of draw type.
     */
    private static final Type[] DRAW_COMMAND_TYPES_VALUES = new Type[] {Type.PLOT, Type.LINE, Type.RECTANGLE, Type.ELLIPSE, Type.POLYGON};
    static final Set<Type> DRAW_COMMAND_TYPES = new HashSet<>(Arrays.asList(DRAW_COMMAND_TYPES_VALUES));

    /**
     * Constructs a set containing all the commands of colour type.
     */
    private static final Type[] COLOUR_COMMAND_TYPES_VALUES = new Type[] {Type.PEN, Type.FILL};
    static final Set<Type> COLOUR_COMMAND_TYPES = new HashSet<>(Arrays.asList(COLOUR_COMMAND_TYPES_VALUES));

    /**
     * Constructs a map containing the required amount of position arguments for each corresponding draw command.
     */
    static final Map<Type, Integer> DRAW_COMMAND_POSITION_ARGUMENTS = Map.ofEntries(
            Map.entry(Type.PLOT, 2),
            Map.entry(Type.LINE, 4),
            Map.entry(Type.RECTANGLE, 4),
            Map.entry(Type.ELLIPSE, 4)
    );
}
