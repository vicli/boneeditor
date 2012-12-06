package backend;

/**
 * Deals with the actual Edits in the docs by adding things to the
 * EditQueue and dealing with the response from the queue. Also does
 * things like reassign Edit owners at the end of an Edit.
 *
 *
 * Testing strategy -- This will be tested using arbitrary Strings and
 * ServerDocs to make sure that every method works correctly. The testing
 * will include mock objects since this class interacts with so many
 * other classes.
 */
public class EditController {
    /**
     * Empty EditController constructor
     */
    public EditController() {
        
    }
    
    /**
     * Deals with inserts. Interacts with the EditQueue to make sure that 
     *   everything is threadsafe and in order
     * @param input The message from the GUI, passed through the server
     * @param doc The ServerDocument that the GUI is currently editing
     * @return The message to return to the server. Returns "Success" if 
     *   successful and "Error" if not successful
     */
    public String insert(String input, ServerDocument doc, EditQueue queue) {
        return "";
    }
    
    /**
     * Deals with removals. Interacts with the EditQueue to make sure that
     *   everything is threadsafe and in order
     * @param input The message from the GUI, passed through the server
     * @param doc The ServerDocument that the GUI is currently editing
     * @return The message to return to the server
     */
    public String remove(String input, ServerDocument doc, EditQueue queue) {
        return "";
    }
    
    /**
     * Deals with the end of an edit, iterates through and changes
     *   the owner and color of the finished edit. Interacts with the
     *   EditQUeue to make sure that everything is threadsafe and in order
     * @param input The message from the GUI, passed through the server
     * @param doc The ServerDocument that the GUI is currently editing
     * @return The message to return to the server
     */
    public String endEdit(String input, ServerDocument doc, EditQueue queue) {
        return "";
    }
}
