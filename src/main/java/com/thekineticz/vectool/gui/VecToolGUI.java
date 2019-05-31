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

    private VecFile vecFile = null;

    /**
     * Creates and shows the VecTool GUI.
     * For safety, this should be invoked from the event-dispatching thread.
     */
    public VecToolGUI() {
        new VecTool();

        //Setup JFrame
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle(TITLE);

        //Override window close operation to custom function.
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitGUI();
            }
        });

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
                JOptionPane.YES_NO_CANCEL_OPTION,
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
        catch (VecIOException e){
            return saveVecFileAs();
        }
        catch (IOException e){
            JOptionPane.showMessageDialog(
                    this,
                    "Error while writing to file.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
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
            }
            catch (VecCommandException e){
                JOptionPane.showMessageDialog(
                        this,
                        e.getMessage(),
                        "Invalid File",
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
}
