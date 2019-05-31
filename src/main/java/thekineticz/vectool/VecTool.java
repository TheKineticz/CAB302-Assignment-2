package thekineticz.vectool;

import thekineticz.vectool.gui.VecToolGUI;

import javax.swing.*;

/**
 * Instance manager and entry point for the VecTool application.
 */
public class VecTool {

    private static VecTool applicationInstance;

    /**
     * Registers a new VecTool application instance as running.
     *
     * @throws IllegalStateException Thrown if an instance of the VecTool application is already running.
     */
    public VecTool(){
        if (applicationInstance != null){
            throw new IllegalStateException("Application is already running.");
        }

        applicationInstance = this;
    }

    /**
     * Deregister the instance.
     */
    public static void close(){
        applicationInstance = null;
    }

    /**
     * The entry point for the Application.
     *
     * @param args String array arguments (Ignored)
     */
    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        javax.swing.SwingUtilities.invokeLater(VecToolGUI::new);
    }
}
