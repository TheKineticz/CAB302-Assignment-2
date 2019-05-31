package com.thekineticz.vectool.gui;

import com.thekineticz.vectool.vec.VecFile;
import com.thekineticz.vectool.vec.commands.*;
import com.thekineticz.vectool.vec.common.ColourHexMatcher;

import java.awt.*;

/**
 * Canvas class for drawing VEC images.
 */
public class VecCanvas extends Canvas {

    private VecFile vecFile;
    private Color penColour;
    private Color fillColour;

    /**
     * Creates a new VecCanvas.
     *
     * @param vecFile The VecFile to draw on the canvas.
     * @param size The size of the canvas
     */
    public VecCanvas(VecFile vecFile, int size){
        this.vecFile = vecFile;
        setBackground(Color.WHITE);
        setSize(size, size);
    }

    /**
     * Converts a 6-digit hex RGB string to a Color object.
     * @param colour The 6-digit hex RGB string, or disable fill command.
     * @return The Color object.
     * @throws IllegalArgumentException Thrown if the input string is invalid.
     */
    public static Color hex2rgb(String colour) throws IllegalArgumentException {
        if (colour.equals(FillCommand.FILL_OFF)){
            return null;
        }
        else if (ColourHexMatcher.isValid(colour)){
            return new Color(
                    Integer.valueOf(colour.substring(1, 3), 16),
                    Integer.valueOf(colour.substring(3, 5), 16),
                    Integer.valueOf(colour.substring(5, 7), 16)
            );
        }
        else {
            throw new IllegalArgumentException("Invalid 6-digit hex colour string.");
        }
    }

    /**
     * Plots a point given by a plot command.
     *
     * @param plotCommand The plot command.
     */
    public void plotPoint(PlotCommand plotCommand){

    }

    /**
     * Draws a line given by a line command.
     *
     * @param lineCommand The line command.
     */
    public void drawLine(LineCommand lineCommand){

    }

    /**
     * Draws a rectangle based on a rectangle command.
     *
     * @param rectangleCommand The rectangle command.
     */
    public void drawRectangle(RectangleCommand rectangleCommand){

    }

    /**
     * Draws an ellipse based on an ellipse command.
     *
     * @param ellipseCommand The ellipse command.
     */
    public void drawEllipse(EllipseCommand ellipseCommand){

    }

    /**
     * Draws a polygon based on a polygon command.
     *
     * @param polygonCommand The polygon command.
     */
    public void drawPolygon(PolygonCommand polygonCommand){

    }

    /**
     * Sets the pen colour based on a pen command.
     *
     * @param penCommand The pen command.
     */
    public void setPenColour(PenCommand penCommand){
        penColour = hex2rgb(penCommand.getColour());
    }

    /**
     * Sets the fill colour based on a fill command.
     *
     * @param fillCommand The fill command.
     */
    public void setFillColour(FillCommand fillCommand){
        fillColour = hex2rgb(fillCommand.getColour());
    }
}
