package thekineticz.vectool.vec.commands;

import org.junit.jupiter.api.Test;
import thekineticz.vectool.exception.VecCommandException;
import thekineticz.vectool.vec.common.Position;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the functionality of the LineCommand class.
 */
public class LineCommandTest {

    @Test
    public void testConstruction() {
        assertDoesNotThrow(() -> {
            ArrayList<Position> pc1Pos = new ArrayList<>() {{
                add(new Position(0.2, 0.2));
                add(new Position(0.8, 0.8));
            }};

            ArrayList<Position> pc2Pos = new ArrayList<>() {{
                add(new Position(0.5, 0.5));
                add(new Position(0.5, 0.3));
            }};

            LineCommand pc1 = new LineCommand(pc1Pos);
            LineCommand pc2 = new LineCommand(pc2Pos);

            assertNotEquals(pc1.getPositions(), pc2.getPositions());
        });
    }

    @Test
    public void testValidPositions() {
        assertDoesNotThrow(() ->
                new LineCommand(new ArrayList<>() {{
                    add(new Position(0.2, 0.2));
                    add(new Position(0.8, 0.8));
                }})
        );

        assertDoesNotThrow(() ->
                new LineCommand(new ArrayList<>() {{
                    add(new Position(0.0, 0.0));
                    add(new Position(1.0, 1.0));
                }})
        );
    }

    @Test
    public void testInvalidPositions() {
        assertThrows(VecCommandException.class, () -> new LineCommand(new ArrayList<>()));

        assertThrows(VecCommandException.class, () ->
                new LineCommand(new ArrayList<>() {{
                    add(new Position(0.0, 0.0));
                }})
        );

        assertThrows(VecCommandException.class, () ->
                new LineCommand(new ArrayList<>() {{
                    add(new Position(0.0, 0.0));
                    add(new Position(0.2, 0.2));
                    add(new Position(0.5, 0.5));
                }})
        );
    }

    @Test
    public void testFromInvalidStrings() {
        assertThrows(VecCommandException.class, () -> {
            LineCommand.fromString("");
        });

        assertThrows(VecCommandException.class, () -> {
            LineCommand.fromString("LINE");
        });

        assertThrows(VecCommandException.class, () -> {
            LineCommand.fromString("LINE 0.3");
        });

        assertThrows(VecCommandException.class, () -> {
            LineCommand.fromString("LINE 0.3 0.5 0.1");
        });

        assertThrows(VecCommandException.class, () -> {
            LineCommand.fromString("LINE 0.5 0.5 0.7 0.7 0.2");
        });

        assertThrows(VecCommandException.class, () -> {
            LineCommand.fromString("LINE these are garbage values");
        });

        assertThrows(VecCommandException.class, () -> {
            LineCommand.fromString("kjsaldhflkjsah");
        });

        assertThrows(VecCommandException.class, () -> {
            LineCommand.fromString("PLOT 0.5 0.5");
        });

        assertThrows(VecCommandException.class, () -> {
            LineCommand.fromString("line 0.5 0.5 0.7 0.7");
        });
    }

    @Test
    public void testFromValidStrings() {
        assertDoesNotThrow(() -> {
            LineCommand.fromString("LINE 0 0 1 1");
        });

        assertDoesNotThrow(() -> {
            LineCommand.fromString("LINE 0.0 0.5 0.2 0.3");
        });

        assertDoesNotThrow(() -> {
            LineCommand.fromString("LINE 0.000001 0.999999 0.121233 0.43242");
        });

        assertDoesNotThrow(() -> {
            LineCommand.fromString("LINE 3.232 6.2342 0.2 0.5");
        });

        assertDoesNotThrow(() -> {
            LineCommand.fromString("LINE -0.2 1.2 1.5 -0.2");
        });
    }

    @Test
    public void testFromStringConversion() {
        try {
            LineCommand lc = LineCommand.fromString("LINE 0.2 0.2 0.5 0.5");
            assertEquals(0.2, lc.getPositions().get(0).getX());
            assertEquals(0.2, lc.getPositions().get(0).getY());
            assertEquals(0.5, lc.getPositions().get(1).getX());
            assertEquals(0.5, lc.getPositions().get(1).getY());

            LineCommand lc2 = LineCommand.fromString("LINE 0 0 1 1");
            assertEquals(0.0, lc2.getPositions().get(0).getX());
            assertEquals(0.0, lc2.getPositions().get(0).getY());
            assertEquals(1.0, lc2.getPositions().get(1).getX());
            assertEquals(1.0, lc2.getPositions().get(1).getY());
        } catch (Exception e) {
            e.printStackTrace();
            ;
        }
    }

    @Test
    public void testToStringConversion() {
        try {
            LineCommand lc = new LineCommand(new ArrayList<>() {{
                add(new Position(0.2, 0.2));
                add(new Position(0.8, 0.8));
            }});
            assertEquals("LINE 0.2 0.2 0.8 0.8", lc.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
