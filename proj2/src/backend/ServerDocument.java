package backend;

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
    private String docTitle;
    
    public ServerDocument(String title) {
        docTitle = title;
        
    }  
    
    public String getTitle(){
        return docTitle;
    }

    public void setTitle(String title){
        docTitle = title;
    }

}
