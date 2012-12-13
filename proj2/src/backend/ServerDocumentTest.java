package backend;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


/**
 * Tests the Edit class
 */
public class ServerDocumentTest {
    @Test
    public void basicTest() {
        ServerDocument doc = new ServerDocument("title");
        doc.insertContent(new Edit("a", "miren"), "0", "miren");
        doc.insertContent(new Edit("b", "miren"), "1", "miren");
        doc.insertContent(new Edit("\n", "miren"), "2", "miren");
        doc.insertContent(new Edit("c", "miren"), "3", "miren");
        doc.insertContent(new Edit("d", "miren"), "4", "miren");
        String str = doc.getDocContent();
        StringBuilder string = new StringBuilder("");
        string.append("2|ab");
        string.append(System.getProperty("line.separator"));
        string.append("cd");
        assertEquals(string.toString(), str);
    }
}