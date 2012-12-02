package backend;

import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Position;
import javax.swing.text.Segment;

public class ServerDocument {
    
    public final String TitleProperty;
    
    public ServerDocument (String title) {
        this.TitleProperty = title;
    }

    public void insertEdit(int cursorLoc, String val) {
        
    }
    
    public void removeEdit(int begin, int end) {
        
    }
    
    public void endEdit(String clientName) {
        
    }
    
    public void addDocumentListener(DocumentListener arg0) {
        // TODO Auto-generated method stub

    }

    public void addUndoableEditListener(UndoableEditListener arg0) {
        // TODO Auto-generated method stub

    }

    
    public Position createPosition(int arg0) throws BadLocationException {
        // TODO Auto-generated method stub
        return null;
    }

    
    public Element getDefaultRootElement() {
        // TODO Auto-generated method stub
        return null;
    }

    
    public Position getEndPosition() {
        // TODO Auto-generated method stub
        return null;
    }

    
    public int getLength() {
        // TODO Auto-generated method stub
        return 0;
    }

    
    public Object getProperty(Object arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    
    public Element[] getRootElements() {
        // TODO Auto-generated method stub
        return null;
    }

    
    public Position getStartPosition() {
        // TODO Auto-generated method stub
        return null;
    }

    
    public String getText(int arg0, int arg1) throws BadLocationException {
        // TODO Auto-generated method stub
        return null;
    }

    
    public void getText(int arg0, int arg1, Segment arg2)
            throws BadLocationException {
        // TODO Auto-generated method stub

    }

    
    public void insertString(int arg0, String arg1, AttributeSet arg2)
            throws BadLocationException {
        // TODO Auto-generated method stub

    }

    
    public void putProperty(Object arg0, Object arg1) {
        // TODO Auto-generated method stub

    }

    
    public void remove(int arg0, int arg1) throws BadLocationException {
        // TODO Auto-generated method stub

    }

    
    public void removeDocumentListener(DocumentListener arg0) {
        // TODO Auto-generated method stub

    }

    
    public void removeUndoableEditListener(UndoableEditListener arg0) {
        // TODO Auto-generated method stub

    }

    
    public void render(Runnable arg0) {
        // TODO Auto-generated method stub

    }

}
