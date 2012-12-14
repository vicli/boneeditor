package backend;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

import org.junit.Test;
import java.util.HashMap;

/**
 * Tests the EditController class
 */
public class EditControllerTest {    
    @Test
    public void getQueueTest() {
        // Tests getQueue
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);
        Map<String, ServerDocument> docList = new HashMap<String, ServerDocument>();
        EditController cont = new EditController(queue, docList);
        assertEquals(queue, cont.getQueue());
    }
    
    @Test
    public void insertTest() {
        // Tests insert
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);
        Map<String, ServerDocument> docList = new HashMap<String, ServerDocument>();
        docList.put("doc", new ServerDocument("doc"));
        EditController cont = new EditController(queue, docList);
        String normal = "miren doc insert s 0";
        String space = "miren doc spaceEntered space 0";
        String tab = "miren doc spaceEntered tab 0";
        String enter = "miren doc spaceEntered enter 0";
              
        assertEquals("InsertDone", cont.insert(normal));
        assertEquals("InsertDone", cont.insert(space));
        assertEquals("InsertDone", cont.insert(tab));
        assertEquals("InsertDone", cont.insert(enter));
    }
    
    @Test
    public void removeTest() {
        // Tests remove
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);
        Map<String, ServerDocument> docList = new HashMap<String, ServerDocument>();
        docList.put("doc", new ServerDocument("doc"));
        EditController cont = new EditController(queue, docList);
        cont.insert("miren doc insert a 0");
        cont.insert("miren doc insert b 1");
        cont.insert("miren doc insert c 2");
        cont.insert("miren doc insert d 3");
        String remove1 = "miren doc remove 0 2";
        String remove2 = "miren doc remove 0 0";
              
        assertEquals("Done", cont.remove(remove1));
        assertEquals("Done", cont.remove(remove2));
    }
    
    @Test
    public void endEditTest() {
        // Tests endEdit
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);
        Map<String, ServerDocument> docList = new HashMap<String, ServerDocument>();
        docList.put("doc", new ServerDocument("doc"));
        EditController cont = new EditController(queue, docList);
        cont.insert("miren doc insert a 0");
        cont.insert("miren doc insert b 1");
        cont.insert("miren doc insert c 2");
        cont.insert("miren doc insert d 3");
        
        assertEquals("EndEditDone", cont.endEdit("miren doc"));
    }
    
    @Test
    public void putOnQueueTest() {
        // Tests putOnQueue
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);
        Map<String, ServerDocument> docList = new HashMap<String, ServerDocument>();
        EditController cont = new EditController(queue, docList);
        assertEquals("", cont.putOnQueue("testing input"));
    }
    
    @Test
    public void takeFromQueueTest() throws InterruptedException {
        // Tests takeFromQueue using every possible type of input
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);
        Map<String, ServerDocument> docList = new HashMap<String, ServerDocument>();
        EditController cont = new EditController(queue, docList);
        cont.putOnQueue("miren doc new");
        assertEquals("miren|doc|new|success", cont.takeFromQueue());
        cont.putOnQueue("miren doc open");
        assertEquals("miren|doc|open|1|", cont.takeFromQueue());
        cont.putOnQueue("miren doc insert a 0");
        assertEquals("miren|doc|insert|0", cont.takeFromQueue());
        cont.putOnQueue("miren doc spaceEntered space 0");
        assertEquals("miren|doc|spaceEntered|0", cont.takeFromQueue());
        cont.putOnQueue("miren doc spaceEntered tab 0");
        assertEquals("miren|doc|spaceEntered|0", cont.takeFromQueue());
        cont.putOnQueue("miren doc spaceEntered enter 0");
        assertEquals("miren|doc|spaceEntered|0", cont.takeFromQueue());
        cont.putOnQueue("miren doc remove 0 0");
        assertEquals("miren|doc|remove|0|0", cont.takeFromQueue());
        cont.putOnQueue("miren doc checkNames");
        assertEquals("miren|doc|checkNames|doc", cont.takeFromQueue());
        cont.putOnQueue("miren doc getDocNames");
        assertEquals("miren|doc|getDocNames|doc", cont.takeFromQueue());
        cont.putOnQueue("miren doc save");
        assertEquals("miren|doc|save", cont.takeFromQueue());
        cont.putOnQueue("miren doc cursorMoved");
        assertEquals("miren|doc|cursorMoved", cont.takeFromQueue());
    }
}