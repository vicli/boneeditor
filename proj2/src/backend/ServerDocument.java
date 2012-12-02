package backend;

import java.util.ArrayList;

import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Position;
import javax.swing.text.Segment;

public class ServerDocument extends DefaultStyledDocument{
    /**
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
    }
    
    private ArrayList<Edit> stringToList(String s){
        char[] newarray = s.toCharArray();
        ArrayList<Edit> newList = new ArrayList<Edit>();
        for(int i = 0; i < newarray.length; i++){
            newList.add(Edit.toEdit(newarray[i]));
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
}
