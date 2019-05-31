package com.thekineticz.vectool.gui;

import com.thekineticz.vectool.VecTool;
import com.thekineticz.vectool.vec.VecFile;

import java.awt.*;
import javax.swing.*;

/**
 * VecTool GUI container class and program entry point.
 */
public class VecToolGUI {

    private static final String TITLE = "VECtor Design Tool";
    private VecFile vecFile = null;

    private JFrame frame;

    /**
     * Creates and shows the VecTool GUI.
     * For safety, this should be invoked from the event-dispatching thread.
     */
    public VecToolGUI() {
        new VecTool();

        frame = new JFrame();
        frame.setTitle(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    /**
     * The entry point for the GUI Application.
     *
     * @param args String array arguments (Ignored)
     */
    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(VecToolGUI::new);
    }
}
