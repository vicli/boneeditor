package backend;

public class Edit {
    private static String value;
    private static String owner;
    /**
     * Edit class: includes buffer
     */
    public Edit(String input, String clientName) {
        value = input;
        this.owner = clientName;
    }

    /**
     * Returns the owner
     * @return The owner of the edit
     */
    public String getOwner() {
        return this.owner;
    }
    public static Edit toEdit(char a){
        return new Edit(String.valueOf(a), owner);
    }
    
    @Override
    public String toString(){
        return value;
    }
    /**
     * Returns the value
     * @return The value of this edit
     */
    public String getValue() {
        return this.value;
    }
    
    /**
     * Changes the owner to the doc
     */
    public void changeOwner() {
        this.owner = "doc";
    }
}
