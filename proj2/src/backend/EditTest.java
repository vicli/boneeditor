package backend;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests the Edit class like a regular object
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
    
    @Test
    public void changeOwnerTest() {
        //tests changeOwner
        Edit edit = new Edit("m", "me");
        assertEquals("me", edit.getOwner());
        edit.changeOwner();
        assertEquals("document", edit.getOwner());
    }
    
    @Test
    public void toStringTest() {
        //tests toString
        Edit edit1 = new Edit("a", "name");
        assertEquals("a", edit1.toString());
    }
}