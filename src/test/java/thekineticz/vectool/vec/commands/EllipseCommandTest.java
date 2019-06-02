package thekineticz.vectool.vec.commands;

import thekineticz.vectool.exception.VecCommandException;
import thekineticz.vectool.vec.common.Position;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

/**
 * Tests the functionality of the EllipseCommand class.
 */
public class EllipseCommandTest {

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

            EllipseCommand pc1 = new EllipseCommand(pc1Pos);
            EllipseCommand pc2 = new EllipseCommand(pc2Pos);

            assertNotEquals(pc1.getPositions(), pc2.getPositions());
        });
    }

    @Test
    public void testValidPositions(){
        assertDoesNotThrow(() ->
                new EllipseCommand(new ArrayList<>(){{
                    add(new Position(0.2, 0.2));
                    add(new Position(0.8, 0.8));
                }})
        );

        assertDoesNotThrow(() ->
                new EllipseCommand(new ArrayList<>(){{
                    add(new Position(0.0, 0.0));
                    add(new Position(1.0, 1.0));
                }})
        );
    }

    @Test
    public void testInvalidPositions(){
        assertThrows(VecCommandException.class, () -> new EllipseCommand(new ArrayList<>()));

        assertThrows(VecCommandException.class, () ->
                new EllipseCommand(new ArrayList<>(){{
                    add(new Position(0.0, 0.0));
                }})
        );

        assertThrows(VecCommandException.class, () ->
                new EllipseCommand(new ArrayList<>(){{
                    add(new Position(0.0, 0.0));
                    add(new Position(0.2, 0.2));
                    add(new Position(0.5, 0.5));
                }})
        );
    }

    @Test
    public void testFromInvalidStrings(){
        assertThrows(VecCommandException.class, () -> {
            EllipseCommand.fromString("");
        });

        assertThrows(VecCommandException.class, () -> {
            EllipseCommand.fromString("ELLIPSE");
        });

        assertThrows(VecCommandException.class, () -> {
            EllipseCommand.fromString("ELLIPSE 0.3");
        });

        assertThrows(VecCommandException.class, () -> {
            EllipseCommand.fromString("ELLIPSE 0.3 0.5 0.1");
        });

        assertThrows(VecCommandException.class, () -> {
            EllipseCommand.fromString("ELLIPSE 0.5 0.5 0.7 0.7 0.2");
        });

        assertThrows(VecCommandException.class, () -> {
            EllipseCommand.fromString("ELLIPSE these are garbage values");
        });

        assertThrows(VecCommandException.class, () -> {
            EllipseCommand.fromString("kjsaldhflkjsah");
        });

        assertThrows(VecCommandException.class, () -> {
            EllipseCommand.fromString("LINE 0.5 0.5 0.2 0.2");
        });

        assertThrows(VecCommandException.class, () -> {
            EllipseCommand.fromString("ellipse 0.5 0.5 0.7 0.7");
        });
    }

    @Test
    public void testFromValidStrings(){
        assertDoesNotThrow(() -> {
            EllipseCommand.fromString("ELLIPSE 0 0 1 1");
        });

        assertDoesNotThrow(() -> {
            EllipseCommand.fromString("ELLIPSE 0.0 0.5 0.2 0.3");
        });

        assertDoesNotThrow(() -> {
            EllipseCommand.fromString("ELLIPSE 0.000001 0.999999 0.121233 0.43242");
        });

        assertDoesNotThrow(() -> {
            EllipseCommand.fromString("ELLIPSE 3.232 6.2342 0.2 0.5");
        });

        assertDoesNotThrow(() -> {
            EllipseCommand.fromString("ELLIPSE -0.2 1.2 1.5 -0.2");
        });
    }

    @Test
    public void testFromStringConversion(){
        try {
            EllipseCommand ec = EllipseCommand.fromString("ELLIPSE 0.2 0.2 0.5 0.5");
            assertEquals(0.2, ec.getPositions().get(0).getX());
            assertEquals(0.2, ec.getPositions().get(0).getY());
            assertEquals(0.5, ec.getPositions().get(1).getX());
            assertEquals(0.5, ec.getPositions().get(1).getY());

            EllipseCommand ec2 = EllipseCommand.fromString("ELLIPSE 0 0 1 1");
            assertEquals(0.0, ec2.getPositions().get(0).getX());
            assertEquals(0.0, ec2.getPositions().get(0).getY());
            assertEquals(1.0, ec2.getPositions().get(1).getX());
            assertEquals(1.0, ec2.getPositions().get(1).getY());
        }
        catch (Exception e){
            e.printStackTrace();;
        }
    }

    @Test
    public void testToStringConversion(){
        try {
            EllipseCommand ec = new EllipseCommand(new ArrayList<>(){{
                add(new Position(0.2, 0.2));
                add(new Position(0.8, 0.8));
            }});
            assertEquals("ELLIPSE 0.2 0.2 0.8 0.8", ec.toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
