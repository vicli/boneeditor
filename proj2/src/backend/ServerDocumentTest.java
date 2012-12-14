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
    
    @Test
    public void getTitleTest() {
        ServerDocument doc = new ServerDocument("title");
        assertEquals("title", doc.getTitle());
    }
    
    @Test
    public void setTitleTest() {
        ServerDocument doc = new ServerDocument("title");
        assertEquals("title", doc.getTitle());
        doc.setTitle("newTitle");
        assertEquals("newTitle", doc.getTitle());
    }
    
    @Test
    public void getDocContentTest() {
        ServerDocument doc = new ServerDocument("title");
        doc.insertContent(new Edit("a", "document"), "0", "document");
        doc.insertContent(new Edit("b", "document"), "1", "document");
        doc.insertContent(new Edit("c", "document"), "2", "document");
        assertEquals("1|abc", doc.getDocContent());
    }
    
    @Test
    public void insertContentTest() {
        ServerDocument doc = new ServerDocument("title");
        assertEquals("InsertDone", doc.insertContent(new Edit("a", "document"), "0", "document"));
    }
    
    @Test
    public void removeContentTest() {
        ServerDocument doc = new ServerDocument("title");
        doc.insertContent(new Edit("a", "document"), "0", "document");
        assertEquals("Done", doc.removeContent("0", "0", "document"));
    }
    
    @Test
    public void endEditTest() {
        ServerDocument doc = new ServerDocument("title");
        doc.insertContent(new Edit("a", "miren"), "0", "miren");
        assertEquals("EndEditDone", doc.endEdit("miren"));
    }
}