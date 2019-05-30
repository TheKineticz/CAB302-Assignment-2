package com.thekineticz.vectool;

/**
 * Instance manager for the VecTool application.
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

    public void close(){
        applicationInstance = null;
    }
}
