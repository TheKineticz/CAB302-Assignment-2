package thekineticz.vectool.vec.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the functionality of the ColourHexMatcher class.
 */
public class ColourHexMatcherTest {

    @Test
    public void testInvalidValues() {
        assertFalse(ColourHexMatcher.isValid(""));
        assertFalse(ColourHexMatcher.isValid("      "));
        assertFalse(ColourHexMatcher.isValid("a7a7sd"));
        assertFalse(ColourHexMatcher.isValid("FF0023"));
        assertFalse(ColourHexMatcher.isValid("#FF028i"));
        assertFalse(ColourHexMatcher.isValid("#-/*5g"));
        assertFalse(ColourHexMatcher.isValid("#FF2"));
        assertFalse(ColourHexMatcher.isValid("#FF2362A"));
    }

    @Test
    public void testValidValues() {
        assertTrue(ColourHexMatcher.isValid("#012345"));
        assertTrue(ColourHexMatcher.isValid("#6789AB"));
        assertTrue(ColourHexMatcher.isValid("#CDEFFF"));
        assertTrue(ColourHexMatcher.isValid("#abcdef"));
        assertTrue(ColourHexMatcher.isValid("#ABCDEF"));

        assertTrue(ColourHexMatcher.isValid("#FF0000"));
        assertTrue(ColourHexMatcher.isValid("#00FF00"));
        assertTrue(ColourHexMatcher.isValid("#0000FF"));
    }
}
