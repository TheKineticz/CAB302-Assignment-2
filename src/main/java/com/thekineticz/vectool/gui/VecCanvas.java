package com.thekineticz.vectool.gui;

import com.thekineticz.vectool.vec.VecFile;
import com.thekineticz.vectool.vec.commands.*;
import com.thekineticz.vectool.vec.common.ColourHexMatcher;
import com.thekineticz.vectool.vec.common.VecCommand;

import java.awt.*;

public class VecCanvas extends Canvas {

    private VecFile vecFile;
    private Color penColour;
    private Color fillColour;

    public VecCanvas(VecFile vecFile, int width, int height){
        this.vecFile = vecFile;
        setBackground(Color.WHITE);
        setSize(width, height);

        redraw();
    }

    public static Color hex2rgb(String colour){
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

    private void redraw(){
        for (VecCommand command : vecFile.getCommands()){
            if (command instanceof PlotCommand){
                plotPoint((PlotCommand) command);
            }
            else if (command instanceof LineCommand){
                drawLine((LineCommand) command);
            }
            else if (command instanceof RectangleCommand){
                drawRectangle((RectangleCommand) command);
            }
            else if (command instanceof EllipseCommand){
                drawEllipse((EllipseCommand) command);
            }
            else if (command instanceof PolygonCommand){
                drawPolygon((PolygonCommand) command);
            }
            else if (command instanceof PenCommand){
                setPenColour((PenCommand)command);
            }
            else if (command instanceof FillCommand){
                setFillColour((FillCommand) command);
            }
        }
    }

    public void plotPoint(PlotCommand plotCommand){

    }

    public void drawLine(LineCommand lineCommand){

    }

    public void drawRectangle(RectangleCommand rectangleCommand){

    }

    public void drawEllipse(EllipseCommand ellipseCommand){

    }

    public void drawPolygon(PolygonCommand polygonCommand){

    }

    public void setPenColour(PenCommand penCommand){
        penColour = hex2rgb(penCommand.getColour());
    }

    public void setFillColour(FillCommand fillCommand){
        fillColour = hex2rgb(fillCommand.getColour());
    }
}
