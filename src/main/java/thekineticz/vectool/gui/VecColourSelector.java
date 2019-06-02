package thekineticz.vectool.gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.*;

/**
 * Tool bar class for selecting colours.
 */
class VecColourSelector extends JToolBar {

    ColourSelectorButton penColourButton;
    ColourSelectorButton fillColourButton;

    /**
     * Constructs a new VecColourSelector bar.
     */
    VecColourSelector(){
        //Set up layout
        setFloatable(false);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.CENTER;

        //Set up buttons
        penColourButton = new ColourSelectorButton("Pen", "Set the colour of the pen.", Color.BLACK, false);
        fillColourButton = new ColourSelectorButton("Fill", "Set the shape fill colour.", null, true);

        add(penColourButton, constraints);
        add(fillColourButton, constraints);
    }

    /**
     * Resets the colour selector buttons to their default colours.
     */
    void reset(){
        setPenColour(penColourButton.DEFAULT_COLOUR);
        setFillColour(fillColourButton.DEFAULT_COLOUR);
    }

    /**
     * Sets the pen colour.
     *
     * @param colour The colour.
     */
    void setPenColour(Color colour){
        penColourButton.setColour(colour);
    }

    /**
     * Sets the fill colour.
     *
     * @param colour The colour.
     */
    void setFillColour(Color colour){
        penColourButton.setColour(colour);
    }

    /**
     * Inner class for the custom colour preview/selector buttons.
     */
    private class ColourSelectorButton extends JPanel implements ActionListener {

        private final Dimension BUTTON_SIZE = new Dimension(32, 32);
        private final Color DEFAULT_COLOUR;
        private Color currentColour;
        private boolean canBeNull;

        private final Border STANDARD_BUTTON_BORDER = BorderFactory.createCompoundBorder(new LineBorder(Color.BLACK, 1), new LineBorder(Color.WHITE, 1));
        private final Border HOVERED_BUTTON_BORDER = BorderFactory.createCompoundBorder(new LineBorder(Color.BLACK, 1), new LineBorder(Color.LIGHT_GRAY, 1));

        JButton button;
        JLabel label;

        /**
         * Create a new ColourSelectorButton.
         *
         * @param label The label that will be drawn under the colour button.
         * @param tooltip The tooltip that will be displayed when hovering over the button.
         * @param defaultColour The default colour for the object.
         * @param canBeNull Whether the colour argument can be null (OFF).
         */
        ColourSelectorButton(String label, String tooltip, Color defaultColour, boolean canBeNull){

            DEFAULT_COLOUR = defaultColour;
            this.canBeNull = canBeNull;

            //Set up layout
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(new EmptyBorder(5, 5, 5, 5));
            setOpaque(false);

            //Set up colour preview/changer button
            button = new JButton();
            button.setToolTipText(String.format("<html><p>%s</p><p>Right-click to reset to default.</p></html>", tooltip));
            setColour(DEFAULT_COLOUR);

            //Force size
            button.setMinimumSize(BUTTON_SIZE);
            button.setMaximumSize(BUTTON_SIZE);
            button.setPreferredSize(BUTTON_SIZE);

            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
            button.setOpaque(true);
            button.setBorder(STANDARD_BUTTON_BORDER);
            button.setAlignmentX(JButton.CENTER_ALIGNMENT);

            //Add custom mouse listeners for visual effects
            button.addActionListener(this);
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setBorder(HOVERED_BUTTON_BORDER);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    button.setBorder(STANDARD_BUTTON_BORDER);
                }

                @Override
                public void mouseClicked(MouseEvent e){
                    if (SwingUtilities.isRightMouseButton(e)){
                        setColour(DEFAULT_COLOUR);
                    }
                }
            });

            //Set up label
            this.label = new JLabel(label, JLabel.CENTER);
            this.label.setAlignmentX(JLabel.CENTER_ALIGNMENT);

            add(button);
            add(this.label);
        }

        /**
         * Set the colour of the colour selector.
         *
         * @param colour The colour.
         */
        public void setColour(Color colour){
            button.setBackground(colour);
            currentColour = colour;
        }

        /**
         * Gets the colour of the colour selector.
         *
         * @return The colour of the colour selector.
         */
        public Color getColour(){
            return currentColour;
        }

        /**
         * Custom action listener for starting colour selection dialog on button click.
         *
         * @param event The event that occurred.
         */
        public void actionPerformed(ActionEvent event){
            Color newColour = JColorChooser.showDialog(null, "Select colour", button.getBackground());
            if (newColour != null){
                if (newColour instanceof ColorUIResource && canBeNull){
                    setColour(null);
                }
                else {
                    setColour(newColour);
                }
            }
        }
    }
}
