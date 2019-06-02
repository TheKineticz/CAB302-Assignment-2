package thekineticz.vectool.gui;

import javax.swing.*;
import java.awt.*;

class VecToolbar extends JToolBar {

    private VecToolSelector toolSelector;
    private VecColourSelector colourSelector;

    VecToolbar(){
        setFloatable(false);
        setLayout(new FlowLayout(FlowLayout.LEFT));

        //Set up tool selector
        toolSelector = new VecToolSelector();

        JPanel toolSelectorPanel = new JPanel();
        toolSelectorPanel.setPreferredSize(new Dimension(150, 80));
        toolSelectorPanel.setLayout(new BorderLayout());
        toolSelectorPanel.add(toolSelector, BorderLayout.CENTER);
        JLabel toolLabel = new JLabel("Tools", JLabel.CENTER);
        toolLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        toolSelectorPanel.add(toolLabel, BorderLayout.SOUTH);

        //Set up colour selector
        colourSelector = new VecColourSelector();

        JPanel colourSelectorPanel = new JPanel();
        colourSelectorPanel.setPreferredSize(new Dimension(100, 80));
        colourSelectorPanel.setLayout(new BorderLayout());
        colourSelectorPanel.add(colourSelector, BorderLayout.CENTER);
        JLabel colourLabel = new JLabel("Colours", JLabel.CENTER);
        colourLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        colourSelectorPanel.add(colourLabel, BorderLayout.SOUTH);

        add(toolSelectorPanel);
        addSeparator();
        add(colourSelectorPanel);

        setVisible(true);
    }
}
