package thekineticz.vectool.gui;

import thekineticz.vectool.vec.VecFile;
import thekineticz.vectool.vec.commands.*;
import thekineticz.vectool.vec.common.VecCommand;

import javax.swing.*;
import java.awt.*;

class VecCanvas extends JPanel {

    private VecFile vecFile;

    VecCanvas(VecFile vecFile){
        this.vecFile = vecFile;
    }

    @Override
    public Dimension getPreferredSize(){
        Dimension dim = super.getPreferredSize();
        Container parent = getParent();
        if (parent != null){
            dim = parent.getSize();
        }

        int width = (int) dim.getWidth();
        int height = (int) dim.getHeight();
        int size = (width < height ? width : height);
        return new Dimension(size, size);
    }

    @Override
    public void paint(Graphics g){
        //Fill background colour
        g.setColor(Color.WHITE);
        g.fillRect(0 ,0, getWidth(), getHeight());

        Color penColour = Color.BLACK;
        Color fillColour = null;

        for (VecCommand command : vecFile.getCommands()){
            if (command instanceof PlotCommand){
                plotPoint(g, (PlotCommand)command, penColour);
            }
            else if (command instanceof LineCommand){
                drawLine(g, (LineCommand)command, penColour);
            }
            else if (command instanceof RectangleCommand){
                drawRectangle(g, (RectangleCommand)command, penColour, fillColour);
            }
            else if (command instanceof EllipseCommand){
                drawEllipse(g, (EllipseCommand)command, penColour, fillColour);
            }
            else if (command instanceof PolygonCommand){
                drawPolygon(g, (PolygonCommand)command, penColour, fillColour);
            }
            else if (command instanceof PenCommand){
                penColour = ColourHexConverter.hex2rgb(((PenCommand) command).getColour());
            }
            else if (command instanceof FillCommand){
                fillColour = ColourHexConverter.hex2rgb(((FillCommand) command).getColour());
            }
        }
    }

    private void plotPoint(Graphics g, PlotCommand command, Color penColour){

    }

    private void drawLine(Graphics g, LineCommand command, Color penColour){

    }

    private void drawRectangle(Graphics g, RectangleCommand command, Color penColour, Color fillColour){

    }

    private void drawEllipse(Graphics g, EllipseCommand command, Color penColour, Color fillColour){

    }

    private void drawPolygon(Graphics g, PolygonCommand command, Color penColour, Color fillColour){

    }
}
