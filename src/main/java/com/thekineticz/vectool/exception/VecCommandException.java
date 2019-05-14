package com.thekineticz.vectool.exception;

/**
 * Indicates there was some issue constructing the vec command.
 */
public class VecCommandException extends  Exception{

    /**
     * Creates a new VecCommandException with a custom error message.
     *
     * @param message The error message.
     */
    public VecCommandException(String message){
        super(message);
    }
}
