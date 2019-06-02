package thekineticz.vectool.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Tool Bar class for selecting which drawing tool you want to use.
 */
class VecToolSelector extends JToolBar {

    ButtonGroup tools = new ButtonGroup();
    JToggleButton plotToolButton;
    JToggleButton lineToolButton;
    JToggleButton rectangleToolButton;
    JToggleButton ellipseToolButton;
    JToggleButton polygonToolButton;

    /**
     * Creates a new VecToolSelector bar.
     */
    VecToolSelector(){
        //Set up layout
        setFloatable(false);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.CENTER;

        //Set up plot button
        plotToolButton = new JToggleButton(new ImageIcon(getClass().getResource("plot.png")));
        plotToolButton.setFocusPainted(false);
        plotToolButton.setToolTipText("Plot");
        tools.add(plotToolButton);
        add(plotToolButton, constraints);

        //Set up line button
        lineToolButton = new JToggleButton(new ImageIcon(getClass().getResource("line.png")));
        lineToolButton.setFocusPainted(false);
        lineToolButton.setToolTipText("Line");
        tools.add(lineToolButton);
        add(lineToolButton, constraints);

        //Set up rectangle button
        rectangleToolButton = new JToggleButton(new ImageIcon(getClass().getResource("rectangle.png")));
        rectangleToolButton.setFocusPainted(false);
        rectangleToolButton.setToolTipText("Rectangle");
        tools.add(rectangleToolButton);
        add(rectangleToolButton, constraints);

        //Set up ellipse button
        ellipseToolButton = new JToggleButton(new ImageIcon(getClass().getResource("ellipse.png")));
        ellipseToolButton.setFocusPainted(false);
        ellipseToolButton.setToolTipText("Ellipse");
        tools.add(ellipseToolButton);
        add(ellipseToolButton, constraints);

        //Set up polygon button
        polygonToolButton = new JToggleButton(new ImageIcon(getClass().getResource("polygon.png")));
        polygonToolButton.setFocusPainted(false);
        polygonToolButton.setToolTipText("Polygon");
        tools.add(polygonToolButton);
        add(polygonToolButton, constraints);
    }
}
