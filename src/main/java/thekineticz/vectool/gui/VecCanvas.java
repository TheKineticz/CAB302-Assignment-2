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
    private VecToolGUI.VecCanvasEditor editor;

    /**
     * Create a new VecCanvas tied to a VecFile.
     *
     * @param vecFile The VecFile.
     */
    VecCanvas(VecFile vecFile, VecToolGUI.VecCanvasEditor editor) {
        this.vecFile = vecFile;
        this.editor = editor;
    }

    /**
     * Overrides the getPreferredSize function to make the VecCanvas fill the container it's in while preserving a square shape.
     *
     * @return The preferred size of the canvas.
     */
    @Override
    public Dimension getPreferredSize() {
        Dimension dim = super.getPreferredSize();
        Container parent = getParent();
        if (parent != null) {
            dim = parent.getSize();
        }

        int width = (int) dim.getWidth();
        int height = (int) dim.getHeight();
        int size = (width < height ? width : height);
        return new Dimension(size, size);
    }

    /**
     * Overrides the paint function to draw a VecFile on the canvas during repainting.
     * If an editor action is currently underway, a preview will also be drawn.
     *
     * @param g Graphics parameter; automatically passed during repainting.
     */
    @Override
    public void paint(Graphics g) {
        //Fill background colour
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        Color penColour = Color.BLACK;
        Color fillColour = null;

        //Draw the shapes as defined by the VEC file
        for (VecCommand command : vecFile.getCommands()) {
            if (command instanceof PlotCommand) {
                plotPoint(g, (PlotCommand) command, penColour);
            } else if (command instanceof LineCommand) {
                drawLine(g, ((LineCommand) command).getPositions(), penColour);
            } else if (command instanceof RectangleCommand) {
                drawRectangle(g, ((RectangleCommand) command).getPositions(), penColour, fillColour);
            } else if (command instanceof EllipseCommand) {
                drawEllipse(g, ((EllipseCommand) command).getPositions(), penColour, fillColour);
            } else if (command instanceof PolygonCommand) {
                drawPolygon(g, ((PolygonCommand) command).getVertices(), penColour, fillColour);
            } else if (command instanceof PenCommand) {
                penColour = ColourHexConverter.hex2rgb(((PenCommand) command).getColour());
            } else if (command instanceof FillCommand) {
                fillColour = ColourHexConverter.hex2rgb(((FillCommand) command).getColour());
            }
        }

        //Preview edits from the VecCanvasEditor
        if (!editor.getPositionBuffer().isEmpty()) {
            if (editor.getActiveTool() == LineCommand.class) {
                ArrayList<Position> positions = new ArrayList<>();
                positions.add(editor.getPositionBuffer().get(0));
                positions.add(editor.getMousePosition());

                drawLine(g, positions, editor.getNextPenColour());
            } else if (editor.getActiveTool() == RectangleCommand.class) {
                ArrayList<Position> positions = new ArrayList<>();
                positions.add(editor.getPositionBuffer().get(0));
                positions.add(editor.getMousePosition());

                drawRectangle(g, positions, editor.getNextPenColour(), editor.getNextFillColour());
            } else if (editor.getActiveTool() == EllipseCommand.class) {
                ArrayList<Position> positions = new ArrayList<>();
                positions.add(editor.getPositionBuffer().get(0));
                positions.add(editor.getMousePosition());

                drawEllipse(g, positions, editor.getNextPenColour(), editor.getNextFillColour());
            } else if (editor.getActiveTool() == PolygonCommand.class) {
                ArrayList<Position> positions = new ArrayList<>(editor.getPositionBuffer());
                positions.add(editor.getMousePosition());

                for (int i = 1; i < positions.size(); i++) {
                    drawLine(g, new ArrayList<>(positions.subList(i - 1, i + 1)), editor.getNextPenColour());
                }
            }
        }
    }

    /**
     * Plots a single pixel on the canvas.
     *
     * @param g         Graphics parameter from paint function.
     * @param command   PlotCommand reference.
     * @param penColour The colour of the pixel being plotted.
     */
    private void plotPoint(Graphics g, PlotCommand command, Color penColour) {
        Position position = command.getPosition();
        int x = (int) Math.round(getWidth() * position.getX());
        int y = (int) Math.round(getHeight() * position.getY());

        g.setColor(penColour);
        g.drawLine(x, y, x, y);
    }

    /**
     * Draws a line on the canvas.
     *
     * @param g         Graphics parameter from paint function.
     * @param positions The line endpoints.
     * @param penColour The colour of the line being drawn.
     */
    private void drawLine(Graphics g, ArrayList<Position> positions, Color penColour) {
        int x1 = (int) Math.round(getWidth() * positions.get(0).getX());
        int y1 = (int) Math.round(getHeight() * positions.get(0).getY());
        int x2 = (int) Math.round(getWidth() * positions.get(1).getX());
        int y2 = (int) Math.round(getHeight() * positions.get(1).getY());

        g.setColor(penColour);
        g.drawLine(x1, y1, x2, y2);
    }

    /**
     * Draws a rectangle on the canvas.
     *
     * @param g          Graphics parameter from paint function.
     * @param positions  The rectangle corners.
     * @param penColour  The colour of the rectangle border.
     * @param fillColour The fill colour of the rectangle.
     */
    private void drawRectangle(Graphics g, ArrayList<Position> positions, Color penColour, Color fillColour) {
        int x1 = (int) Math.round(getWidth() * positions.get(0).getX());
        int y1 = (int) Math.round(getHeight() * positions.get(0).getY());
        int x2 = (int) Math.round(getWidth() * positions.get(1).getX());
        int y2 = (int) Math.round(getHeight() * positions.get(1).getY());

        //Force (x1, y1) to be top-left and (x2, y2) to be bottom-right
        if (x1 > x2) {
            //Swap x1 and x2
            x1 += x2;
            x2 = x1 - x2;
            x1 -= x2;
        }

        if (y1 > y2) {
            //Swap y1 and y2
            y1 += y2;
            y2 = y1 - y2;
            y1 -= y2;
        }

        if (fillColour != null) {
            g.setColor(fillColour);
            g.fillRect(x1, y1, x2 - x1, y2 - y1);
        }
        g.setColor(penColour);
        g.drawRect(x1, y1, x2 - x1, y2 - y1);
    }

    /**
     * Draws an ellipse on the canvas.
     *
     * @param g          Graphics parameter from paint function.
     * @param positions  The ellipse corners.
     * @param penColour  The colour of the ellipse border.
     * @param fillColour The fill colour of the ellipse.
     */
    private void drawEllipse(Graphics g, ArrayList<Position> positions, Color penColour, Color fillColour) {
        int x1 = (int) Math.round(getWidth() * positions.get(0).getX());
        int y1 = (int) Math.round(getHeight() * positions.get(0).getY());
        int x2 = (int) Math.round(getWidth() * positions.get(1).getX());
        int y2 = (int) Math.round(getHeight() * positions.get(1).getY());

        //Force (x1, y1) to be top-left and (x2, y2) to be bottom-right
        if (x1 > x2) {
            //Swap x1 and x2
            x1 += x2;
            x2 = x1 - x2;
            x1 -= x2;
        }

        if (y1 > y2) {
            //Swap y1 and y2
            y1 += y2;
            y2 = y1 - y2;
            y1 -= y2;
        }

        if (fillColour != null) {
            g.setColor(fillColour);
            g.fillOval(x1, y1, x2 - x1, y2 - y1);
        }
        g.setColor(penColour);
        g.drawOval(x1, y1, x2 - x1, y2 - y1);
    }

    /**
     * Draws a polygon on the canvas.
     *
     * @param g          Graphics parameter from paint function.
     * @param vertices   The polygon vertices.
     * @param penColour  The colour of the polygon border.
     * @param fillColour The fill colour of the polygon.
     */
    private void drawPolygon(Graphics g, ArrayList<Position> vertices, Color penColour, Color fillColour) {
        int n = vertices.size();
        int[] xPoints = new int[n];
        int[] yPoints = new int[n];

        for (int i = 0; i < n; i++) {
            Position vertex = vertices.get(i);
            xPoints[i] = (int) Math.round(getWidth() * vertex.getX());
            yPoints[i] = (int) Math.round(getHeight() * vertex.getY());
        }

        Polygon polygon = new Polygon(xPoints, yPoints, n);

        if (fillColour != null) {
            g.setColor(fillColour);
            g.fillPolygon(polygon);
        }
        g.setColor(penColour);
        g.drawPolygon(polygon);
    }
}
