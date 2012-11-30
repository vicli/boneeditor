package backend;

public class Edit {

    /**
     * Edit class:
     * - has lock so that the contents of the edit is mutated by only one thread
     * - method to take the string so we can add to the serverDoc
     * - method to update the contents of the edit without accessing the edit directly
     *
     * use string or arraylist of characters?
     * does the edit need to know where its position is so the serverDoc can just add
     *      or does the serverDoc keep cursor position so it just adds edit content?
     * does synchronize lock for just the one instance of updating, or how do i ensure
     *      that the lock stays on the entire edit until the edit is done?
     * if client inputs to gui, how does the gui input translate to code/how do i work with that?
     * how does deleting work in terms of editing the string/does this apply to highlighting?
     * what happens to the instance of the edit after it is added to serverDoc?
     */

    private String content;
    private String client;
    
    public Edit(String client, String content) {
        this.content = content;
        this.client = client;
    }
    
    // allows the serverDoc to insert the value of the content
    @Override
    public synchronized String toString() {
        return this.content;
    }
    
    // allows the edit to update if the client trying to update matches the owner of the edit
    public synchronized void updateEdit(String client, String s) {
        if (client == this.client) {
            this.content += s;
        }
    }
}
