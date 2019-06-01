package thekineticz.vectool.gui;

import javax.swing.*;
import java.awt.*;

class VecToolSelector extends JToolBar {

    JToggleButton plotToolButton;
    JToggleButton lineToolButton;
    JToggleButton rectangleToolButton;
    JToggleButton ellipseToolButton;
    JToggleButton polygonToolButton;

    VecToolSelector(){
        setFloatable(false);

        ButtonGroup tools = new ButtonGroup();

        plotToolButton = new JToggleButton("Plot");
        plotToolButton.setFocusPainted(false);
        tools.add(plotToolButton);
        add(plotToolButton);

        lineToolButton = new JToggleButton("Line");
        lineToolButton.setFocusPainted(false);
        tools.add(lineToolButton);
        add(lineToolButton);

        rectangleToolButton = new JToggleButton("Rectangle");
        rectangleToolButton.setFocusPainted(false);
        tools.add(rectangleToolButton);
        add(rectangleToolButton);

        ellipseToolButton = new JToggleButton("Ellipse");
        ellipseToolButton.setFocusPainted(false);
        tools.add(ellipseToolButton);
        add(ellipseToolButton);

        polygonToolButton = new JToggleButton("Polygon");
        polygonToolButton.setFocusPainted(false);
        tools.add(polygonToolButton);
        add(polygonToolButton);
    }
}
