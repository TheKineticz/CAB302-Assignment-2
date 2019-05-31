package thekineticz.vectool.vec.commands;

import thekineticz.vectool.exception.VecCommandException;

import thekineticz.vectool.vec.common.Position;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests functionality of PlotCommand class.
 */
public class PlotCommandTest {

    @Test
    public void testConstruction(){
        assertDoesNotThrow(() -> {
            PlotCommand pc1 = new PlotCommand(new Position<>(0.5, 0.5));
            PlotCommand pc2 = new PlotCommand(new Position<>(0.7, 0.3));

            assertNotEquals(pc1.getPosition(), pc2.getPosition());
        });
    }

    @Test
    public void testGetArgs(){
        PlotCommand pc1 = new PlotCommand(new Position<>(0.5, 0.5));
        PlotCommand pc2 = new PlotCommand(new Position<>(0.7, 0.3));

        assertEquals(pc1.getArgs(), "0.5 0.5");
        assertEquals(pc2.getArgs(), "0.7 0.3");
    }

    @Test
    public void testFromInvalidStrings(){
        assertThrows(VecCommandException.class, () -> {
            PlotCommand.fromString("");
        });

        assertThrows(VecCommandException.class, () -> {
            PlotCommand.fromString("PLOT");
        });

        assertThrows(VecCommandException.class, () -> {
            PlotCommand.fromString("PLOT 0.3");
        });

        assertThrows(VecCommandException.class, () -> {
            PlotCommand.fromString("PLOT 0.3 0.5 0.1");
        });

        assertThrows(VecCommandException.class, () -> {
            PlotCommand.fromString("PLOT garbage values");
        });

        assertThrows(VecCommandException.class, () -> {
            PlotCommand.fromString("kjsaldhflkjsah");
        });

        assertThrows(VecCommandException.class, () -> {
            PlotCommand.fromString("LINE 0.0 0.0 1.0 1.0");
        });

        assertThrows(VecCommandException.class, () -> {
            PlotCommand.fromString("plot 0.5 0.5");
        });
    }

    @Test
    public void testFromValidStrings(){
        assertDoesNotThrow(() -> {
            PlotCommand.fromString("PLOT 0 0");
        });

        assertDoesNotThrow(() -> {
            PlotCommand.fromString("PLOT 1 1");
        });

        assertDoesNotThrow(() -> {
            PlotCommand.fromString("PLOT 0.0 0.5");
        });

        assertDoesNotThrow(() -> {
            PlotCommand.fromString("PLOT 0.000001 0.999999");
        });

        assertDoesNotThrow(() -> {
            PlotCommand.fromString("PLOT 3.232 6.2342");
        });

        assertDoesNotThrow(() -> {
            PlotCommand.fromString("PLOT -0.2 1.2");
        });
    }

    @Test
    public void testFromStringConversion(){
        try {
            PlotCommand pc = PlotCommand.fromString("PLOT 0.5 0.5");
            assertEquals(0.5, pc.getPosition().getX());
            assertEquals(0.5, pc.getPosition().getY());

            PlotCommand pc2 = PlotCommand.fromString("PLOT 0 0");
            assertEquals(0.0, pc2.getPosition().getX());
            assertEquals(0.0, pc2.getPosition().getY());
        }
        catch (Exception e){
            e.printStackTrace();;
        }
    }

    @Test
    public void testToStringConversion(){
        try {
            PlotCommand pc = new PlotCommand(new Position<>(0.5, 0.5));
            assertEquals("PLOT 0.5 0.5", pc.toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
