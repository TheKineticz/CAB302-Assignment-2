package com.thekineticz.vectool.gui;

import com.thekineticz.vectool.VecTool;
import com.thekineticz.vectool.exception.VecIOException;
import com.thekineticz.vectool.vec.VecFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * VecTool GUI container class and program entry point.
 */
public class VecToolGUI {

    private static final String TITLE = "VECtor Design Tool";
    private static final String DEFAULT_FILENAME = "Untitled";

    private JFrame frame;
    private JMenuBar menuBar;

    private VecFile vecFile = null;

    /**
     * Creates and shows the VecTool GUI.
     * For safety, this should be invoked from the event-dispatching thread.
     */
    public VecToolGUI(){
        new VecTool();

        //Create and setup the window
        frame = new JFrame();
        frame.setTitle(TITLE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (vecFile != null && !vecFile.isSaved()){
                    int choice = promptSave();
                    if (choice == 0){
                        try {
                            vecFile.save();
                            System.exit(0);
                        }
                        catch (Exception exception){
                            JOptionPane.showMessageDialog(frame, "Error occurred while saving file.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else if (choice == 1){
                        System.exit(0);
                    }
                }
                else {
                    System.exit(0);
                }
            }
        });

        //Create and setup the menu bar
        constructMenuBar();

        //Display the window
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Creates and loads a new blank vec file.
     */
    private void loadNewVecFile(){
        vecFile = new VecFile(DEFAULT_FILENAME);
        frame.setTitle(String.format("%s - %s", DEFAULT_FILENAME, TITLE));
    }

    private void saveVecFile(){

    }

    private void constructMenuBar(){
        menuBar = new JMenuBar();
        menuBar.setOpaque(true);
        menuBar.setPreferredSize(new Dimension(frame.getWidth(), 20));

        //Create File tab
        JMenu fileTab = new JMenu("File");
        fileTab.setMnemonic(KeyEvent.VK_F);

        JMenuItem createNewButton = new JMenuItem("New");
        fileTab.add(createNewButton);
        createNewButton.addActionListener(new createNewFileListener());

        JMenuItem openButton = new JMenuItem("Open...");
        fileTab.add(openButton);

        fileTab.addSeparator();

        JMenuItem saveButton = new JMenuItem("Save");
        fileTab.add(saveButton);
        JMenuItem saveAsButton = new JMenuItem("Save As");
        fileTab.add(saveAsButton);

        //Create Edit tab
        JMenu editTab = new JMenu("Edit");
        fileTab.setMnemonic(KeyEvent.VK_E);

        JMenuItem undoLatestButton = new JMenuItem("Undo Latest Action");
        editTab.add(undoLatestButton);

        menuBar.add(fileTab);
        menuBar.add(editTab);

        frame.setJMenuBar(menuBar);
    }

    private int promptSave(){
        Object[] options = {"Save", "Don't Save", "Cancel"};

        return JOptionPane.showOptionDialog(frame,
                String.format("Do you want to save changes to %s?", vecFile.getFilename()),
                TITLE,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                options,
                options[0]);
    }

    /**
     * The entry point for the GUI Application.
     *
     * @param args String array arguments (Ignored)
     */
    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(VecToolGUI::new);
    }

    private class createNewFileListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            if (vecFile != null && !vecFile.isSaved()){
                promptSave();
            }
            loadNewVecFile();
        }
    }
}
