package backend;

import static org.junit.Assert.assertEquals;

/**
 * Tests the Edit class
 */
public class EditTest {
    public void basicTest() {
        Edit edit1 = new Edit("a", "name");
        assertEquals("name", edit1.getOwner());
    }
}