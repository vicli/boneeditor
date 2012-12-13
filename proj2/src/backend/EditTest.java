package backend;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests the Edit class
 */
public class EditTest {
    @Test
    public void ownerTest() {
        //tests getOwner
        Edit edit1 = new Edit("a", "name");
        assertEquals("name", edit1.getOwner());
    }
    
    @Test
    public void valueTest() {
        //tests getValue
        Edit edit1 = new Edit("a", "name");
        assertEquals("a", edit1.getValue());
    }
}