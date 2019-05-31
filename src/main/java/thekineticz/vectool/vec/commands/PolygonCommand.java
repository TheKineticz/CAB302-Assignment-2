package thekineticz.vectool.vec.commands;

import thekineticz.vectool.exception.VecCommandException;
import thekineticz.vectool.vec.common.*;

import java.util.ArrayList;

/**
 * The internal structure of a polygon vec command.
 */
public class PolygonCommand extends VecCommand {

    public static final String COMMAND_NAME = "POLYGON";

    private ArrayList<Position<Double>> vertices;

    /**
     * Constructs a new PolygonCommand.
     *
     * @param vertices The array of the polygon's vertices.
     */
    public PolygonCommand(ArrayList<Position<Double>> vertices) throws VecCommandException {
        super(COMMAND_NAME);
        if (vertices.isEmpty()){
            throw new VecCommandException(String.format("%s command must contain at least one vertex.", COMMAND_NAME));
        }
        this.vertices = vertices;
    }

    /**
     * Gets the vertices array of the polygon.
     *
     * @return The vertices array of the polygon.
     */
    public ArrayList<Position<Double>> getVertices(){
        return vertices;
    }

    /**
     * Gets the vertices of the polygon in string form.
     *
     * @return The vertices of the polygon in string form.
     */
    public String getArgs(){
        StringBuilder string = new StringBuilder(vertices.get(0).toString());
        for (int i = 1; i < vertices.size(); i++){
            string.append(" ");
            string.append(vertices.get(i).toString());
        }

        return string.toString();
    }

    /**
     * Constructs a new PolygonCommand from a polygon command string.
     *
     * @param command The command string.
     * @return The PolygonCommand object representing the command string.
     * @throws VecCommandException Thrown if an error occurs while parsing the string.
     */
    public static PolygonCommand fromString(String command) throws VecCommandException {
        String[] commandArray = command.split(" ");

        //Check whether the input string is empty
        if (commandArray.length == 0){
            throw new VecCommandException(String.format("Attempted to create %s from empty string.", COMMAND_NAME));
        }

        //Check whether the input string is empty
        if (!commandArray[0].equals(COMMAND_NAME)){
            throw new VecCommandException("Attempted to generate PolygonCommand from string with incorrect identifier.");
        }

        //Check whether the input string has any vertex arguments
        if (commandArray.length == 1){
            throw new VecCommandException(String.format("Attempted to create %s from string with no vertex arguments.", COMMAND_NAME));
        }

        //Check if the input string has a valid count of vertex commands for creating position pairs
        if ((commandArray.length - 1) % 2 != 0){
            throw new VecCommandException(String.format("Attempted to create %s from command string an invalid amount of position arguments.", COMMAND_NAME));
        }

        ArrayList<Position<Double>> positions = new ArrayList<>();
        Double x, y;

        //Try to parse the position arguments
        for (int i = 0; i < commandArray.length / 2; i++){
            try {
                x = Double.valueOf(commandArray[1 + i * 2]);
                y = Double.valueOf(commandArray[2 + i * 2]);
            }
            catch (Exception e){
                throw new VecCommandException(String.format("Attempted to parse invalid argument for %s command position.", COMMAND_NAME), e);
            }
            positions.add(new Position<>(x, y));
        }

        return new PolygonCommand(positions);
    }
}
