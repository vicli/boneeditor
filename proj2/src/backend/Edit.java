package backend;

public class Edit {
    final private String value;
    private String owner;
    /**
     * Edit class: includes buffer
     */
    public Edit(String input, String clientName) {
        value = input;
        owner = clientName;
    }

    /**
     * Returns the owner
     * @return The owner of the edit
     */
    public String getOwner() {
        return owner;
    }
    
    /**
     * Returns the value
     * @return The value of this edit
     */
    public String getValue() {
        return value;
    }
    
    /**
     * Changes the owner to the doc
     */
    public void changeOwner() {
        owner = "doc";
    }
}
