package com.thekineticz.vectool.vec.commands;

import com.thekineticz.vectool.exception.VecCommandException;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class FillCommandTest {

    @Test
    public void testConstruction(){
        assertDoesNotThrow(() -> {
            FillCommand fc1 = new FillCommand("#FF00FF");
            FillCommand fc2 = new FillCommand("#FF0000");

            assertNotEquals(fc1.getColour(), fc2.getColour());
            assertDoesNotThrow(() -> new FillCommand(FillCommand.FILL_OFF));
        });
    }

    @Test
    public void testGetArgs(){
        assertDoesNotThrow(() -> {
            FillCommand fc1 = new FillCommand("#FF00FF");
            FillCommand fc2 = new FillCommand("#FF0000");
            FillCommand fc3 = new FillCommand(FillCommand.FILL_OFF);

            assertEquals(fc1.getArgs(), "#FF00FF");
            assertEquals(fc2.getArgs(), "#FF0000");
            assertEquals(fc3.getArgs(), FillCommand.FILL_OFF);
        });
    }

    @Test
    public void testFromInvalidStrings(){
        assertThrows(VecCommandException.class, () -> {
            FillCommand.fromString("");
        });

        assertThrows(VecCommandException.class, () -> {
            FillCommand.fromString("FILL");
        });

        assertThrows(VecCommandException.class, () -> {
            FillCommand.fromString("FILL 0.3");
        });

        assertThrows(VecCommandException.class, () -> {
            FillCommand.fromString("FILL 0.3 0.5 0.1");
        });

        assertThrows(VecCommandException.class, () -> {
            FillCommand.fromString("FILL garbage values");
        });

        assertThrows(VecCommandException.class, () -> {
            FillCommand.fromString("kjsaldhflkjsah");
        });

        assertThrows(VecCommandException.class, () -> {
            FillCommand.fromString("POLYGON 0.0 0.0 1.0 1.0");
        });

        assertThrows(VecCommandException.class, () -> {
            FillCommand.fromString("fill #FF0000");
        });

        assertThrows(VecCommandException.class, () -> {
            FillCommand.fromString("FILL #ab0v.4");
        });

        assertThrows(VecCommandException.class, () -> {
            FillCommand.fromString("FILL #012TYU");
        });

        assertThrows(VecCommandException.class, () -> {
            FillCommand.fromString("FILL off");
        });
    }

    @Test
    public void testFromValidStrings(){
        assertDoesNotThrow(() -> {
            FillCommand.fromString("FILL #FF00FA");
        });

        assertDoesNotThrow(() -> {
            FillCommand.fromString("FILL #000000");
        });

        assertDoesNotThrow(() -> {
            FillCommand.fromString("FILL #ff00fa");
        });

        assertDoesNotThrow(() -> {
            FillCommand.fromString("FILL #fF00Fa");
        });

        assertDoesNotThrow(() -> {
            FillCommand.fromString(String.format("FILL %s", FillCommand.FILL_OFF));
        });
    }

    @Test
    public void testFromStringConversion(){
        try {
            FillCommand fc = FillCommand.fromString("FILL #FF0000");
            assertEquals("#FF0000", fc.getColour());

            FillCommand fc2 = FillCommand.fromString("FILL #FFFFFF");
            assertEquals("#FFFFFF", fc2.getColour());

            FillCommand fc3 = FillCommand.fromString(String.format("FILL %s", FillCommand.FILL_OFF));
            assertEquals(FillCommand.FILL_OFF, fc3.getColour());
        }
        catch (Exception e){
            e.printStackTrace();;
        }
    }

    @Test
    public void testToStringConversion(){
        try {
            FillCommand pc = new FillCommand("#Ff0032");
            assertEquals("FILL #Ff0032", pc.toString());

            FillCommand pc2 = new FillCommand(FillCommand.FILL_OFF);
            assertEquals(String.format("FILL %s", FillCommand.FILL_OFF), pc2.toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
