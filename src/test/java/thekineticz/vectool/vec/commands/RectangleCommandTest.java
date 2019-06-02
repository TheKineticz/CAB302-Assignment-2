package thekineticz.vectool.vec.commands;

import thekineticz.vectool.exception.VecCommandException;
import thekineticz.vectool.vec.common.Position;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

/**
 * Tests the functionality of the RectangleCommand class.
 */
public class RectangleCommandTest {

    @Test
    public void testConstruction(){
        assertDoesNotThrow(() -> {
            ArrayList<Position> pc1Pos = new ArrayList<>(){{
                add(new Position(0.2, 0.2));
                add(new Position(0.8, 0.8));
            }};

            ArrayList<Position> pc2Pos = new ArrayList<>(){{
                add(new Position(0.5, 0.5));
                add(new Position(0.5, 0.3));
            }};

            RectangleCommand pc1 = new RectangleCommand(pc1Pos);
            RectangleCommand pc2 = new RectangleCommand(pc2Pos);

            assertNotEquals(pc1.getPositions(), pc2.getPositions());
        });
    }

    @Test
    public void testValidPositions(){
        assertDoesNotThrow(() ->
                new RectangleCommand(new ArrayList<>(){{
                    add(new Position(0.2, 0.2));
                    add(new Position(0.8, 0.8));
                }})
        );

        assertDoesNotThrow(() ->
                new RectangleCommand(new ArrayList<>(){{
                    add(new Position(0.0, 0.0));
                    add(new Position(1.0, 1.0));
                }})
        );
    }

    @Test
    public void testInvalidPositions(){
        assertThrows(VecCommandException.class, () -> new RectangleCommand(new ArrayList<>()));

        assertThrows(VecCommandException.class, () ->
                new RectangleCommand(new ArrayList<>(){{
                    add(new Position(0.0, 0.0));
                }})
        );

        assertThrows(VecCommandException.class, () ->
                new RectangleCommand(new ArrayList<>(){{
                    add(new Position(0.0, 0.0));
                    add(new Position(0.2, 0.2));
                    add(new Position(0.5, 0.5));
                }})
        );
    }

    @Test
    public void testFromInvalidStrings(){
        assertThrows(VecCommandException.class, () -> {
            RectangleCommand.fromString("");
        });

        assertThrows(VecCommandException.class, () -> {
            RectangleCommand.fromString("RECTANGLE");
        });

        assertThrows(VecCommandException.class, () -> {
            RectangleCommand.fromString("RECTANGLE 0.3");
        });

        assertThrows(VecCommandException.class, () -> {
            RectangleCommand.fromString("RECTANGLE 0.3 0.5 0.1");
        });

        assertThrows(VecCommandException.class, () -> {
            RectangleCommand.fromString("RECTANGLE 0.5 0.5 0.7 0.7 0.2");
        });

        assertThrows(VecCommandException.class, () -> {
            RectangleCommand.fromString("RECTANGLE these are garbage values");
        });

        assertThrows(VecCommandException.class, () -> {
            RectangleCommand.fromString("kjsaldhflkjsah");
        });

        assertThrows(VecCommandException.class, () -> {
            RectangleCommand.fromString("LINE 0.5 0.5 0.2 0.2");
        });

        assertThrows(VecCommandException.class, () -> {
            RectangleCommand.fromString("rectangle 0.5 0.5 0.7 0.7");
        });
    }

    @Test
    public void testFromValidStrings(){
        assertDoesNotThrow(() -> {
            RectangleCommand.fromString("RECTANGLE 0 0 1 1");
        });

        assertDoesNotThrow(() -> {
            RectangleCommand.fromString("RECTANGLE 0.0 0.5 0.2 0.3");
        });

        assertDoesNotThrow(() -> {
            RectangleCommand.fromString("RECTANGLE 0.000001 0.999999 0.121233 0.43242");
        });

        assertDoesNotThrow(() -> {
            RectangleCommand.fromString("RECTANGLE 3.232 6.2342 0.2 0.5");
        });

        assertDoesNotThrow(() -> {
            RectangleCommand.fromString("RECTANGLE -0.2 1.2 1.5 -0.2");
        });
    }

    @Test
    public void testFromStringConversion(){
        try {
            RectangleCommand rc = RectangleCommand.fromString("RECTANGLE 0.2 0.2 0.5 0.5");
            assertEquals(0.2, rc.getPositions().get(0).getX());
            assertEquals(0.2, rc.getPositions().get(0).getY());
            assertEquals(0.5, rc.getPositions().get(1).getX());
            assertEquals(0.5, rc.getPositions().get(1).getY());

            RectangleCommand rc2 = RectangleCommand.fromString("RECTANGLE 0 0 1 1");
            assertEquals(0.0, rc2.getPositions().get(0).getX());
            assertEquals(0.0, rc2.getPositions().get(0).getY());
            assertEquals(1.0, rc2.getPositions().get(1).getX());
            assertEquals(1.0, rc2.getPositions().get(1).getY());
        }
        catch (Exception e){
            e.printStackTrace();;
        }
    }

    @Test
    public void testToStringConversion(){
        try {
            RectangleCommand rc = new RectangleCommand(new ArrayList<>(){{
                add(new Position(0.2, 0.2));
                add(new Position(0.8, 0.8));
            }});
            assertEquals("RECTANGLE 0.2 0.2 0.8 0.8", rc.toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
