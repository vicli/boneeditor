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
    
 
    public String getDocContent(){
        return content.toString();
    }
}
