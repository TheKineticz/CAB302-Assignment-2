package com.thekineticz.vectool.gui;

import com.thekineticz.vectool.VecTool;

import java.awt.*;
import javax.swing.*;

/**
 * VecTool GUI container class and program entry point.
 */
public class VecToolGui {

    private static final String TITLE = "VECtor Design Tool";
    private JFrame frame;

    public VecToolGui(){
        new VecTool();

        //Form setup
        frame = new JFrame();
        frame.setTitle(TITLE);

        //Frame setup
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * The entry point for the GUI Application.
     * 
     * @param args String array arguments (Ignored)
     */
    public static void main(String[] args){
        new VecToolGui();
    }
}
