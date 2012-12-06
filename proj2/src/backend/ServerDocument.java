package backend;

import java.util.ArrayList;

import javax.swing.text.DefaultStyledDocument;
//import javax.swing.event.DocumentListener;
//import javax.swing.event.UndoableEditListener;
//import javax.swing.text.AbstractDocument;
//import javax.swing.text.AttributeSet;
//import javax.swing.text.BadLocationException;
//import javax.swing.text.Document;
//import javax.swing.text.Element;
//import javax.swing.text.Position;
//import javax.swing.text.Segment;

/**
 * The ServerDocument is the datatype that stores the document that is being edited.
 * It consists of an ArrayList of Edits.
 * @author User
 *
 */
public class ServerDocument extends DefaultStyledDocument{
    /*
     * 
     */
    private static final long serialVersionUID = 1L;
    private String docTitle;
    private ArrayList<Edit> content;
    
    public ServerDocument(String title) {
        docTitle = title;
        content = new ArrayList<Edit>();
    }  
    
    public String getTitle(){
        return docTitle;
    }

    public void setTitle(String title){
        docTitle = title;
    }
    
    public void updateContent(String c){
        content = stringToList(c);
        System.out.println("updated content is" + content.toString());
    }
    
    private ArrayList<Edit> stringToList(String s){
        System.out.println("string is" + s);
        char[] newarray = s.toCharArray();
        System.out.println("array is" + newarray.toString() + "with size" + newarray.length);
        ArrayList<Edit> newList = new ArrayList<Edit>();
        for(int i = 0; i < newarray.length; i++){
            // TODO: Is having "document" here correct?
            newList.add(new Edit(String.valueOf(newarray[i]), "document"));
        }
        
        return newList;
    }
    private String listToString(ArrayList<Edit> e){
        StringBuilder string = new StringBuilder("");
        for (int i=0; i< e.size(); i++){
            string.append(e.get(i).toString());
        }
        return string.toString();
    }
    
    public String getDocContent(){
        return listToString(content);
    }
    
    public synchronized String insertContent(Edit edit, int loc, String client) {
        if (loc > 0 && loc < content.size() - 1) {
            if (content.get(loc - 1).getOwner().equals(content.get(loc + 1).getOwner()))
                if (!content.get(loc - 1).getOwner().equals(client)) {
                    
                }
        }
        //check if insertion is allowed
        content.add(loc, edit);
        return "Done";
    }
    
    public synchronized String removeContent(int begin, int end, String client) {
        //check if removal is allowed
        for (int i = begin; i < end; i++) {
            content.remove(begin);
        }
        return "Done";
    }
    
    public synchronized String endEdit(String client) {
        for (int i = 0; i < content.length; i++) {
            if (content.get(i).getOwner().equals(client)) {
                content.get(i).changeOwner();
            }
        }
        return "Done";
    }
}
