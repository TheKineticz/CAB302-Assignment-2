package thekineticz.vectool.gui;

import thekineticz.vectool.vec.VecFile;
import thekineticz.vectool.vec.commands.*;
import thekineticz.vectool.vec.common.Position;
import thekineticz.vectool.vec.common.VecCommand;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Custom square JPanel for drawing a VEC image.
 */
class VecCanvas extends JPanel {

    private VecFile vecFile;

    /**
     * Create a new VecCanvas tied to a VecFile.
     *
     * @param vecFile The VecFile.
     */
    VecCanvas(VecFile vecFile){
        this.vecFile = vecFile;
    }

    /**
     * Overrides the getPreferredSize function to make the VecCanvas fill the container it's in while preserving a square shape.
     *
     * @return The preferred size of the canvas.
     */
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

    /**
     * Overrides the paint function to draw a VecFile on the canvas during repainting.
     *
     * @param g Graphics parameter; automatically passed during repainting.
     */
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

    /**
     * Plots a single pixel on the canvas.
     *
     * @param g Graphics parameter from paint function.
     * @param command PlotCommand reference.
     * @param penColour The colour of the pixel being plotted.
     */
    private void plotPoint(Graphics g, PlotCommand command, Color penColour){
        Position<Double> position = command.getPosition();
        int x = (int)Math.round(getWidth() * position.getX());
        int y = (int)Math.round(getHeight() * position.getY());

        g.setColor(penColour);
        g.drawLine(x, y, x, y);
    }

    /**
     * Draws a line on the canvas.
     *
     * @param g Graphics parameter from paint function.
     * @param command LineCommand reference.
     * @param penColour The colour of the line being drawn.
     */
    private void drawLine(Graphics g, LineCommand command, Color penColour){
        ArrayList<Position<Double>> positions = command.getPositions();
        int x1 = (int)Math.round(getWidth() * positions.get(0).getX());
        int y1 = (int)Math.round(getHeight() * positions.get(0).getY());
        int x2 = (int)Math.round(getWidth() * positions.get(1).getX());
        int y2 = (int)Math.round(getHeight() * positions.get(1).getY());

        g.setColor(penColour);
        g.drawLine(x1, y1, x2, y2);
    }

    /**
     * Draws a rectangle on the canvas.
     *
     * @param g Graphics parameter from paint function.
     * @param command The RectangleCommand reference.
     * @param penColour The colour of the rectangle border.
     * @param fillColour The fill colour of the rectangle.
     */
    private void drawRectangle(Graphics g, RectangleCommand command, Color penColour, Color fillColour){
        ArrayList<Position<Double>> positions = command.getPositions();
        int x = (int)Math.round(getWidth() * positions.get(0).getX());
        int y = (int)Math.round(getHeight() * positions.get(0).getY());
        int width = (int)Math.round(getWidth() * positions.get(1).getX()) - x;
        int height = (int)Math.round(getHeight() * positions.get(1).getY()) - y;

        if (fillColour != null){
            g.setColor(fillColour);
            g.fillRect(x, y, width, height);
        }
        g.setColor(penColour);
        g.drawRect(x, y, width, height);
    }

    /**
     * Draws an ellipse on the canvas.
     *
     * @param g Graphics parameter from paint function.
     * @param command The EllipseCommand reference.
     * @param penColour The colour of the ellipse border.
     * @param fillColour The fill colour of the ellipse.
     */
    private void drawEllipse(Graphics g, EllipseCommand command, Color penColour, Color fillColour){
        ArrayList<Position<Double>> positions = command.getPositions();
        int x = (int)Math.round(getWidth() * positions.get(0).getX());
        int y = (int)Math.round(getHeight() * positions.get(0).getY());
        int width = (int)Math.round(getWidth() * positions.get(1).getX()) - x;
        int height = (int)Math.round(getHeight() * positions.get(1).getY()) - y;

        if (fillColour != null){
            g.setColor(fillColour);
            g.fillOval(x, y, width, height);
        }
        g.setColor(penColour);
        g.drawOval(x, y, width, height);
    }

    /**
     * Draws a polygon on the canvas.
     *
     * @param g Graphics parameter from paint function.
     * @param command The PolygonCommand reference.
     * @param penColour The colour of the polygon border.
     * @param fillColour The fill colour of the polygon.
     */
    private void drawPolygon(Graphics g, PolygonCommand command, Color penColour, Color fillColour){
        ArrayList<Position<Double>> vertices = command.getVertices();
        int n = vertices.size();
        int[] xPoints = new int[n];
        int[] yPoints = new int[n];

        for (int i = 0; i < n; i++){
            Position<Double> vertex = vertices.get(i);
            xPoints[i] = (int)Math.round(getWidth() * vertex.getX());
            yPoints[i] = (int)Math.round(getHeight() * vertex.getY());
        }

        Polygon polygon = new Polygon(xPoints, yPoints, n);

        if (fillColour != null){
            g.setColor(fillColour);
            g.fillPolygon(polygon);
        }
        g.setColor(penColour);
        g.drawPolygon(polygon);
    }
}
