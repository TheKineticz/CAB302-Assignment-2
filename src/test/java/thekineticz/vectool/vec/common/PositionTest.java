package thekineticz.vectool.vec.common;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the functionality of the Position class.
 */
public class PositionTest {

    @Test
    public void returnsCorrectValues(){
        Position<Integer> pos = new Position<>(2, 8);
        assertEquals(2, (int) pos.getX());
        assertEquals(8, (int) pos.getY());
    }

    @Test
    public void testEquality(){
        Position<Integer> pos1 = new Position<>(2, 8);
        Position<Integer> pos2 = new Position<>(-2, -8);
        Position<Integer> pos3 = new Position<>(2, 8);

        assertEquals(pos1, pos3);
        assertEquals(pos3, pos1);
        assertNotEquals(pos1, pos2);
        assertNotEquals(pos3, pos2);
        assertNotEquals(10, pos3);
    }

    @Test
    public void testHashEquality(){
        Position<Integer> pos1 = new Position<>(2, 8);
        Position<Integer> pos2 = new Position<>(-2, -8);
        Position<Integer> pos3 = new Position<>(2, 8);

        assertEquals(pos1.hashCode(), pos3.hashCode());
        assertEquals(pos3.hashCode(), pos1.hashCode());
        assertNotEquals(pos1.hashCode(), pos2.hashCode());
        assertNotEquals(pos3.hashCode(), pos2.hashCode());
    }
}
