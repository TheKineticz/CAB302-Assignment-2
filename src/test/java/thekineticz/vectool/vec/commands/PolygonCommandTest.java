package thekineticz.vectool.vec.commands;

import thekineticz.vectool.exception.VecCommandException;
import thekineticz.vectool.vec.common.Position;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Tests the functionality of the PolygonCommand class.
 */
public class PolygonCommandTest {

    @Test
    public void testConstruction(){
        assertDoesNotThrow(() -> {
            ArrayList<Position> pc1Pos = new ArrayList<>(){{
                add(new Position(0.2, 0.2));
                add(new Position(0.8, 0.8));
                add(new Position(0.1, 0.1));
                add(new Position(0.2, 0.3));
            }};

            ArrayList<Position> pc2Pos = new ArrayList<>(){{
                add(new Position(0.5, 0.5));
                add(new Position(0.5, 0.3));
            }};

            PolygonCommand pc1 = new PolygonCommand(pc1Pos);
            PolygonCommand pc2 = new PolygonCommand(pc2Pos);

            assertNotEquals(pc1.getVertices(), pc2.getVertices());
        });
    }

    @Test
    public void testValidPositions(){
        assertDoesNotThrow(() ->
                new PolygonCommand(new ArrayList<>(){{
                    add(new Position(0.0, 0.0));
                    add(new Position(0.0, 1.0));
                    add(new Position(1.0, 1.0));
                    add(new Position(1.0, 0.0));
                }})
        );

        assertDoesNotThrow(() ->
                new PolygonCommand(new ArrayList<>(){{
                    add(new Position(0.2, 0.2));
                    add(new Position(0.5, 0.8));
                    add(new Position(0.8, 0.2));
                }})
        );
    }

    @Test
    public void testInvalidPositions(){
        assertThrows(VecCommandException.class, () -> new PolygonCommand(new ArrayList<>()));
    }

    @Test
    public void testFromInvalidStrings(){
        assertThrows(VecCommandException.class, () -> {
            PolygonCommand.fromString("");
        });

        assertThrows(VecCommandException.class, () -> {
            PolygonCommand.fromString("POLYGON");
        });

        assertThrows(VecCommandException.class, () -> {
            PolygonCommand.fromString("POLYGON 0.3");
        });

        assertThrows(VecCommandException.class, () -> {
            PolygonCommand.fromString("POLYGON 0.3 0.5 0.1");
        });

        assertThrows(VecCommandException.class, () -> {
            PolygonCommand.fromString("POLYGON 0.5 0.5 0.7 0.7 0.2");
        });

        assertThrows(VecCommandException.class, () -> {
            PolygonCommand.fromString("POLYGON these are garbage values");
        });

        assertThrows(VecCommandException.class, () -> {
            PolygonCommand.fromString("kjsaldhflkjsah");
        });

        assertThrows(VecCommandException.class, () -> {
            PolygonCommand.fromString("LINE 0.5 0.5 0.2 0.2");
        });

        assertThrows(VecCommandException.class, () -> {
            PolygonCommand.fromString("polygon 0.5 0.5 0.7 0.7");
        });
    }

    @Test
    public void testFromValidStrings(){
        assertDoesNotThrow(() -> {
            PolygonCommand.fromString("POLYGON 0 0 1 1");
        });

        assertDoesNotThrow(() -> {
            PolygonCommand.fromString("POLYGON 0.5 0.2");
        });

        assertDoesNotThrow(() -> {
            PolygonCommand.fromString("POLYGON 0.5 0.2 0.3 0.6");
        });

        assertDoesNotThrow(() -> {
            PolygonCommand.fromString("POLYGON 0.5 0.2 0.3 0.6 0.1 0.4");
        });

        assertDoesNotThrow(() -> {
            Random rand = new Random();
            StringBuilder string = new StringBuilder("POLYGON");

            int n = 2 + rand.nextInt(98);
            n = n % 2 == 0 ? n : n + 1;

            for (int i = 0; i < n; i++){
                string.append(" ");
                string.append(((Double)rand.nextDouble()).toString());
            }

            PolygonCommand.fromString(string.toString());
        });
    }

    @Test
    public void testFromStringConversion(){
        try {
            PolygonCommand pc = PolygonCommand.fromString("POLYGON 0.2 0.2 0.5 0.5 0.7 0.7");
            assertEquals(0.2, pc.getVertices().get(0).getX());
            assertEquals(0.2, pc.getVertices().get(0).getY());
            assertEquals(0.5, pc.getVertices().get(1).getX());
            assertEquals(0.5, pc.getVertices().get(1).getY());
            assertEquals(0.7, pc.getVertices().get(2).getX());
            assertEquals(0.7, pc.getVertices().get(2).getY());

            PolygonCommand pc2 = PolygonCommand.fromString("POLYGON 0 0 1 01 0.3 0.3");
            assertEquals(0.0, pc2.getVertices().get(0).getX());
            assertEquals(0.0, pc2.getVertices().get(0).getY());
            assertEquals(1.0, pc2.getVertices().get(1).getX());
            assertEquals(1.0, pc2.getVertices().get(1).getY());
            assertEquals(0.3, pc2.getVertices().get(2).getX());
            assertEquals(0.3, pc2.getVertices().get(2).getY());
        }
        catch (Exception e){
            e.printStackTrace();;
        }
    }

    @Test
    public void testToStringConversion(){
        try {
            PolygonCommand pc = new PolygonCommand(new ArrayList<>(){{
                add(new Position(0.2, 0.2));
                add(new Position(0.8, 0.8));
                add(new Position(0.334, 0.32));
            }});
            assertEquals("POLYGON 0.2 0.2 0.8 0.8 0.334 0.32", pc.toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
