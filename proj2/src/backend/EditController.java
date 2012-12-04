package backend;

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
    public String insert(String input, ServerDocument doc) {
        return "";
    }
    
    /**
     * Deals with removals. Interacts with the EditQueue to make sure that
     *   everything is threadsafe and in order
     * @param input The message from the GUI, passed through the server
     * @param doc The ServerDocument that the GUI is currently editing
     * @return The message to return to the server
     */
    public String remove(String input, ServerDocument doc) {
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
    public String endEdit(String input, ServerDocument doc) {
        return "";
    }
}
