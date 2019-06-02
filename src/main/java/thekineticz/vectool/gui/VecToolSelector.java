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
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.CENTER;

        ButtonGroup tools = new ButtonGroup();

        plotToolButton = new JToggleButton(new ImageIcon(getClass().getResource("plot.png")));
        plotToolButton.setFocusPainted(false);
        plotToolButton.setToolTipText("Plot");
        tools.add(plotToolButton);
        add(plotToolButton, constraints);

        lineToolButton = new JToggleButton(new ImageIcon(getClass().getResource("line.png")));
        lineToolButton.setFocusPainted(false);
        lineToolButton.setToolTipText("Line");
        tools.add(lineToolButton);
        add(lineToolButton, constraints);

        rectangleToolButton = new JToggleButton(new ImageIcon(getClass().getResource("rectangle.png")));
        rectangleToolButton.setFocusPainted(false);
        rectangleToolButton.setToolTipText("Rectangle");
        tools.add(rectangleToolButton);
        add(rectangleToolButton, constraints);

        ellipseToolButton = new JToggleButton(new ImageIcon(getClass().getResource("ellipse.png")));
        ellipseToolButton.setFocusPainted(false);
        ellipseToolButton.setToolTipText("Ellipse");
        tools.add(ellipseToolButton);
        add(ellipseToolButton, constraints);

        polygonToolButton = new JToggleButton(new ImageIcon(getClass().getResource("polygon.png")));
        polygonToolButton.setFocusPainted(false);
        polygonToolButton.setToolTipText("Polygon");
        tools.add(polygonToolButton);
        add(polygonToolButton, constraints);
    }
}
