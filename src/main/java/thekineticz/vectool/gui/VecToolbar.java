package thekineticz.vectool.gui;

import javax.swing.*;
import java.awt.*;

class VecToolbar extends JToolBar {

    private VecToolSelector toolSelector;
    private VecColourSelector colourSelector;

    VecToolbar(){
        setFloatable(false);

        //Set up tool selector
        toolSelector = new VecToolSelector();

        JPanel toolSelectorPanel = new JPanel();
        toolSelectorPanel.setLayout(new BoxLayout(toolSelectorPanel, BoxLayout.Y_AXIS));
        toolSelectorPanel.add(toolSelector);
        toolSelectorPanel.add(new JLabel("Tools"));

        add(toolSelectorPanel);
        addSeparator();

        //Set up colour selector
        colourSelector = new VecColourSelector();

        JPanel colourSelectorPanel = new JPanel();
        colourSelectorPanel.setLayout(new BoxLayout(colourSelectorPanel, BoxLayout.Y_AXIS));
        colourSelectorPanel.add(colourSelector);
        colourSelectorPanel.add(new JLabel("Colours"));

        add(colourSelectorPanel);
        addSeparator();

        setVisible(true);
    }
}
