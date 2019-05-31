package com.thekineticz.vectool.gui;

import com.thekineticz.vectool.VecTool;
import com.thekineticz.vectool.exception.*;
import com.thekineticz.vectool.vec.VecFile;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * VecTool GUI main class.
 */
public class VecToolGUI extends JFrame {

    private static final String TITLE = "VECtor Design Tool";
    private static final String DEFAULT_FILENAME = "untitled";

    private VecToolGUIMenuBar menuBar;

    private VecFile vecFile = null;

    /**
     * Creates and shows the VecTool GUI.
     * For safety, this should be invoked from the event-dispatching thread.
     */
    public VecToolGUI() {
        new VecTool();

        //Setup frame
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle(TITLE);

        //Setup menu bar
        menuBar = new VecToolGUIMenuBar();
        setJMenuBar(menuBar);

        //Override window close operation to custom function.
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitGUI();
            }
        });

        pack();
        setVisible(true);
    }

    /**
     * Create a dialog prompting the user to save.
     *
     * @return Whether the process can proceed.
     */
    private boolean promptToSave(){
        Object[] options = {"Save", "Don't Save", "Cancel"};
        int choice = JOptionPane.showOptionDialog(
                this,
                String.format("Do you want to save changes to %s?", vecFile.getFilename()),
                TITLE,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                options,
                options[0]
        );

        //Ready to proceed if file successfully saves or user chooses not to save.
        if (choice == JOptionPane.OK_OPTION){
            return vecFile.isNewFile() ? saveVecFileAs() : saveVecFile();
        }
        else return choice != JOptionPane.CANCEL_OPTION;
    }

    /**
     * Prompt to save existing file, the deregister the GUI instance and close the application.
     */
    private void exitGUI(){
        //Intercept the window close operation if we have an unsaved file.
        if (vecFile != null && !vecFile.isSaved()){
            if (!promptToSave()){
                return;
            }
        }

        VecTool.close();
        System.exit(0);
    }

    /**
     * Saves the VecFile to it's original file.
     *
     * @return Whether the operation was successful.
     */
    private boolean saveVecFile(){
        try {
            vecFile.save();
            return true;
        }
        catch (IOException e){
            JOptionPane.showMessageDialog(
                    this,
                    "An error occurred while trying to save the file.\nPlease save to a new location.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            saveVecFileAs();
        }

        return false;
    }

    /**
     * Saves the VecFile to a new file.
     *
     * @return Whether the operation was successful.
     */
    private boolean saveVecFileAs(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooser.setSelectedFile(new File(String.format("%s.%s", vecFile.getFilename(), VecFile.FILE_EXTENSION)));
        fileChooser.setFileFilter(new FileNameExtensionFilter("VEC File", VecFile.FILE_EXTENSION));

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();

            try {
                vecFile.saveAs(file);
                return true;
            }
            catch (IOException e){
                JOptionPane.showMessageDialog(
                        this,
                        "IO Error while writing to file.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        }

        return false;
    }

    /**
     * Prompt to save existing file and create a new VecFile.
     */
    private void createNewVecFile(){
        if (vecFile != null && !vecFile.isSaved()){
            if (!promptToSave()){
                return;
            }
        }

        vecFile = new VecFile(DEFAULT_FILENAME);
        setTitle(String.format("%s - %s", vecFile.getFilename(), TITLE));
        menuBar.closeFileButton.setEnabled(true);
        menuBar.saveFileButton.setEnabled(false);
        menuBar.saveAsFileButton.setEnabled(true);
        if (vecFile.getCommands().isEmpty()){
            menuBar.undoLastButton.setEnabled(false);
        }
        else {
            menuBar.undoLastButton.setEnabled(true);
        }
    }

    /**
     * Prompt to save existing file and start prompt to open an existing vec file.
     */
    private void openVecFile(){
        if (vecFile != null && !vecFile.isSaved()){
            if (!promptToSave()){
                return;
            }
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        fileChooser.setFileFilter(new FileNameExtensionFilter("VEC File", VecFile.FILE_EXTENSION));

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();

            try {
                vecFile = new VecFile(file);
                setTitle(String.format("%s - %s", vecFile.getFilename(), TITLE));

                menuBar.closeFileButton.setEnabled(true);
                menuBar.saveFileButton.setEnabled(true);
                menuBar.saveAsFileButton.setEnabled(true);
                if (vecFile.getCommands().isEmpty()){
                    menuBar.undoLastButton.setEnabled(false);
                }
                else {
                    menuBar.undoLastButton.setEnabled(true);
                }
            }
            catch (VecCommandException e){
                JOptionPane.showMessageDialog(
                        this,
                        String.format("Invalid line in file.\nError: %s", e.getMessage()),
                        "Error in file",
                        JOptionPane.ERROR_MESSAGE
                );
            }
            catch (IOException e){
                JOptionPane.showMessageDialog(
                        this,
                        "IO Error while reading from file.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    /**
     * Prompt to save existing file and close file.
     */
    private void closeVecFile(){
        if (vecFile != null && !vecFile.isSaved()){
            if (!promptToSave()){
                return;
            }
        }

        vecFile = null;
        setTitle(TITLE);
        menuBar.closeFileButton.setEnabled(false);
        menuBar.saveFileButton.setEnabled(false);
        menuBar.saveAsFileButton.setEnabled(false);
        menuBar.undoLastButton.setEnabled(false);
    }

    /**
     * Inner class for the menu bar of the VecToolGUI.
     */
    private class VecToolGUIMenuBar extends JMenuBar implements ActionListener {

        public JMenu fileMenu;
        public JMenuItem newFileButton;
        public JMenuItem openFileButton;
        public JMenuItem closeFileButton;
        public JMenuItem saveFileButton;
        public JMenuItem saveAsFileButton;
        public JMenuItem exitButton;

        public JMenu editMenu;
        public JMenuItem undoLastButton;

        /**
         * Creates a new menu bar.
         */
        public VecToolGUIMenuBar(){
            //Setup File menu
            fileMenu = new JMenu("File");
            fileMenu.setMnemonic(KeyEvent.VK_F);

            newFileButton = new JMenuItem("New");
            newFileButton.setMnemonic(KeyEvent.VK_N);
            newFileButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
            newFileButton.addActionListener(this );

            openFileButton = new JMenuItem("Open");
            openFileButton.setMnemonic(KeyEvent.VK_O);
            openFileButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
            openFileButton.addActionListener(this );

            closeFileButton = new JMenuItem("Close File");
            closeFileButton.setMnemonic(KeyEvent.VK_C);
            closeFileButton.addActionListener(this );
            closeFileButton.setEnabled(false);

            saveFileButton = new JMenuItem("Save");
            saveFileButton.setMnemonic(KeyEvent.VK_S);
            saveFileButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
            saveFileButton.addActionListener(this);
            saveFileButton.setEnabled(false);

            saveAsFileButton = new JMenuItem("Save As");
            saveAsFileButton.setMnemonic(KeyEvent.VK_A);
            saveAsFileButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
            saveAsFileButton.addActionListener(this);
            saveAsFileButton.setEnabled(false);

            exitButton = new JMenuItem("Exit");
            exitButton.setMnemonic(KeyEvent.VK_X);
            exitButton.addActionListener(this );

            fileMenu.add(newFileButton);
            fileMenu.add(openFileButton);
            fileMenu.add(closeFileButton);
            fileMenu.add(new JSeparator());
            fileMenu.add(saveFileButton);
            fileMenu.add(saveAsFileButton);
            fileMenu.add(new JSeparator());
            fileMenu.add(exitButton);

            //Setup Edit menu
            editMenu = new JMenu("Edit");
            editMenu.setMnemonic(KeyEvent.VK_E);

            undoLastButton = new JMenuItem("Undo latest command");
            undoLastButton.setMnemonic(KeyEvent.VK_U);
            undoLastButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
            undoLastButton.addActionListener(this);
            undoLastButton.setEnabled(false);

            editMenu.add(undoLastButton);

            //Setup menu bar
            add(fileMenu); add(editMenu);
        }

        public void actionPerformed(ActionEvent e){
            if (e.getSource() == newFileButton){
                createNewVecFile();
            }
            else if (e.getSource() == openFileButton){
                openVecFile();
            }
            else if (e.getSource() == closeFileButton){
                closeVecFile();
            }
            else if (e.getSource() == saveFileButton){
                saveVecFile();
            }
            else if (e.getSource() == saveAsFileButton){
                saveVecFileAs();
            }
            else if (e.getSource() == undoLastButton){
                vecFile.undoLatestCommand();
                if (vecFile.getCommands().isEmpty()){
                    undoLastButton.setEnabled(false);
                }
            }
            else if (e.getSource() == exitButton){
                exitGUI();
            }
        }
    }
}
