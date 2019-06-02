package thekineticz.vectool.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Container class for the window tool bar.
 */
class VecToolbar extends JToolBar {

    VecToolSelector toolSelector;
    VecColourSelector colourSelector;
    private static final int HEIGHT = 80;

    /**
     * Constructs a new VecToolbar.
     */
    VecToolbar(){
        setFloatable(false);
        setLayout(new FlowLayout(FlowLayout.LEFT));

        //Set up tool selector
        toolSelector = new VecToolSelector();

        JPanel toolSelectorPanel = new JPanel();
        toolSelectorPanel.setPreferredSize(new Dimension(150, HEIGHT));
        toolSelectorPanel.setLayout(new BorderLayout());
        toolSelectorPanel.add(toolSelector, BorderLayout.CENTER);
        JLabel toolLabel = new JLabel("Tools", JLabel.CENTER);
        toolLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        toolSelectorPanel.add(toolLabel, BorderLayout.SOUTH);

        //Set up colour selector
        colourSelector = new VecColourSelector();

        JPanel colourSelectorPanel = new JPanel();
        colourSelectorPanel.setPreferredSize(new Dimension(100, HEIGHT));
        colourSelectorPanel.setLayout(new BorderLayout());
        colourSelectorPanel.add(colourSelector, BorderLayout.CENTER);
        JLabel colourLabel = new JLabel("Colours", JLabel.CENTER);
        colourLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        colourSelectorPanel.add(colourLabel, BorderLayout.SOUTH);

        //Set up tool bar
        add(toolSelectorPanel);
        addSeparator();
        add(colourSelectorPanel);
    }
}
