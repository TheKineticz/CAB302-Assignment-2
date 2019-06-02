package thekineticz.vectool.vec.commands;

import org.junit.jupiter.api.Test;
import thekineticz.vectool.exception.VecCommandException;

import static org.junit.jupiter.api.Assertions.*;

public class PenCommandTest {

    @Test
    public void testConstruction() {
        assertDoesNotThrow(() -> {
            PenCommand pc1 = new PenCommand("#FF00FF");
            PenCommand pc2 = new PenCommand("#FF0000");

            assertNotEquals(pc1.getColour(), pc2.getColour());
        });
    }

    @Test
    public void testGetArgs() {
        assertDoesNotThrow(() -> {
            PenCommand pc1 = new PenCommand("#FF00FF");
            PenCommand pc2 = new PenCommand("#FF0000");

            assertEquals(pc1.getArgs(), "#FF00FF");
            assertEquals(pc2.getArgs(), "#FF0000");
        });
    }

    @Test
    public void testFromInvalidStrings() {
        assertThrows(VecCommandException.class, () -> {
            PenCommand.fromString("");
        });

        assertThrows(VecCommandException.class, () -> {
            PenCommand.fromString("PEN");
        });

        assertThrows(VecCommandException.class, () -> {
            PenCommand.fromString("PEN 0.3");
        });

        assertThrows(VecCommandException.class, () -> {
            PenCommand.fromString("PEN 0.3 0.5 0.1");
        });

        assertThrows(VecCommandException.class, () -> {
            PenCommand.fromString("PEN garbage values");
        });

        assertThrows(VecCommandException.class, () -> {
            PenCommand.fromString("kjsaldhflkjsah");
        });

        assertThrows(VecCommandException.class, () -> {
            PenCommand.fromString("POLYGON 0.0 0.0 1.0 1.0");
        });

        assertThrows(VecCommandException.class, () -> {
            PenCommand.fromString("pen #FF0000");
        });

        assertThrows(VecCommandException.class, () -> {
            PenCommand.fromString("PEN #ab0v.4");
        });

        assertThrows(VecCommandException.class, () -> {
            PenCommand.fromString("PEN #012TYU");
        });
    }

    @Test
    public void testFromValidStrings() {
        assertDoesNotThrow(() -> {
            PenCommand.fromString("PEN #FF00FA");
        });

        assertDoesNotThrow(() -> {
            PenCommand.fromString("PEN #000000");
        });

        assertDoesNotThrow(() -> {
            PenCommand.fromString("PEN #ff00fa");
        });

        assertDoesNotThrow(() -> {
            PenCommand.fromString("PEN #fF00Fa");
        });
    }

    @Test
    public void testFromStringConversion() {
        try {
            PenCommand pc = PenCommand.fromString("PEN #FF0000");
            assertEquals("#FF0000", pc.getColour());

            PenCommand pc2 = PenCommand.fromString("PEN #FFFFFF");
            assertEquals("#FFFFFF", pc2.getColour());
        } catch (Exception e) {
            e.printStackTrace();
            ;
        }
    }

    @Test
    public void testToStringConversion() {
        try {
            PenCommand pc = new PenCommand("#Ff0032");
            assertEquals("PEN #Ff0032", pc.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
