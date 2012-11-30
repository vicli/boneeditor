package backend;

import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Position;
import javax.swing.text.Segment;

public class ServerDocument implements Document {

    public void insertEdit(int cursorLoc, String val) {
        
    }
    
    public void removeEdit(int begin, int end) {
        
    }
    
    public void endEdit(String clientName) {
        
    }
    
    @Override
    public void addDocumentListener(DocumentListener arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addUndoableEditListener(UndoableEditListener arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public Position createPosition(int arg0) throws BadLocationException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Element getDefaultRootElement() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Position getEndPosition() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getLength() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Object getProperty(Object arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Element[] getRootElements() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Position getStartPosition() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getText(int arg0, int arg1) throws BadLocationException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void getText(int arg0, int arg1, Segment arg2)
            throws BadLocationException {
        // TODO Auto-generated method stub

    }

    @Override
    public void insertString(int arg0, String arg1, AttributeSet arg2)
            throws BadLocationException {
        // TODO Auto-generated method stub

    }

    @Override
    public void putProperty(Object arg0, Object arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void remove(int arg0, int arg1) throws BadLocationException {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeDocumentListener(DocumentListener arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeUndoableEditListener(UndoableEditListener arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void render(Runnable arg0) {
        // TODO Auto-generated method stub

    }

}
