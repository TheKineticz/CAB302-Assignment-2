package thekineticz.vectool.gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.*;

class VecColourSelector extends JToolBar {

    ColourPreviewButton penColourButton;
    ColourPreviewButton fillColourButton;

    VecColourSelector(){
        setFloatable(false);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.CENTER;

        penColourButton = new ColourPreviewButton("Pen", "Set the colour of the pen.", Color.BLACK, false);
        fillColourButton = new ColourPreviewButton("Fill", "Set the shape fill colour.", null, true);

        add(penColourButton, constraints);
        add(fillColourButton, constraints);
    }

    private class ColourPreviewButton extends JPanel implements ActionListener {

        private final Dimension BUTTON_SIZE = new Dimension(32, 32);
        private final Color DEFAULT_COLOUR;
        private Color currentColour;
        private boolean canBeNull;

        private final Border STANDARD_BUTTON_BORDER = BorderFactory.createCompoundBorder(new LineBorder(Color.BLACK, 1), new LineBorder(Color.WHITE, 1));
        private final Border HOVERED_BUTTON_BORDER = BorderFactory.createCompoundBorder(new LineBorder(Color.BLACK, 1), new LineBorder(Color.LIGHT_GRAY, 1));

        JButton button;
        JLabel label;

        ColourPreviewButton(String label, String tooltip, Color defaultColour, boolean canBeNull){
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(new EmptyBorder(5, 5, 5, 5));
            DEFAULT_COLOUR = defaultColour;
            this.canBeNull = canBeNull;

            //Set up colour preview/changer button
            button = new JButton();
            button.setToolTipText(String.format("<html><p>%s</p><p>Right-click to reset to default.</p></html>", tooltip));
            setColour(DEFAULT_COLOUR);

            button.setMinimumSize(BUTTON_SIZE);
            button.setMaximumSize(BUTTON_SIZE);
            button.setPreferredSize(BUTTON_SIZE);

            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
            button.setOpaque(true);
            button.setBorder(STANDARD_BUTTON_BORDER);
            button.setAlignmentX(JButton.CENTER_ALIGNMENT);

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

        public void setColour(Color colour){
            button.setBackground(colour);
            currentColour = colour;
        }

        public Color getColour(){
            return currentColour;
        }

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
