package gui;

import gui.DocGUI.DocumentWindow.RedoAction;
import gui.DocGUI.DocumentWindow.UndoAction;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;



/**
 * This is the DocGUI, which re
 *
 */
public class DocGUI extends JFrame implements ActionListener, KeyListener{
    
    /**
     * The GUI
     */
    private static JFrame frameOwner;
    private static JFrame welcomeWindow = new JFrame("Welcome");
    private static JButton okay;
    private static JTextField nameField;
    private static JTextField colorField;
    private static WindowOne dialog1;
    private static DocumentWindow docWindow;
    private static FileWindow fileWindow;
    private static NameWindow nameWindow;
    private JButton newButton; 
    private JButton openButton;
    
    private String clientColor;
    private String clientName;
    private String docName;
    
    private AbstractDocument displayedDoc;
    protected UndoAction undoAction;
    protected RedoAction redoAction;
    protected UndoManager undo = new UndoManager();
    private String newline = "\n";
    private HashMap<Object, Action> action;
    private Border docBorder;
    
    public DocGUI(){
        FlowLayout layout = new FlowLayout();
        welcomeWindow.setLayout(layout);
        welcomeWindow.setSize(600, 200);
        welcomeWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeWindow.setLocationRelativeTo(null);
        welcomeWindow.setVisible(true);
        
        JLabel welcomeMessage = new JLabel("Welcome to Bone Editor! Please enter your name and color to get started.");
        JLabel name = new JLabel("Name (6 letters max):");
        nameField = new JTextField();
        JLabel color = new JLabel("Color:");
        colorField = new JTextField();
        okay = new JButton("Okay");
        name.setName("name");
        nameField.setName("nameField");
        color.setName("color");
        colorField.setName("colorField");
        okay.setName("okay");
        
        welcomeWindow.add(welcomeMessage);
        welcomeMessage.setBounds(70, 20, 560, 20);
        welcomeWindow.add(name);
        name.setBounds(160, 55, 150, 20);
        welcomeWindow.add(nameField);
        nameField.setBounds(310, 55, 120, 20);
        nameField.addKeyListener(this);
        welcomeWindow.add(color);
        color.setBounds(160, 80, 100, 20);
        welcomeWindow.add(colorField);
        colorField.setBounds(310, 80, 120, 20);
        colorField.addActionListener(this);
        welcomeWindow.add(okay);
        okay.setSize(80, 35);
        okay.setLocation(260, 120);
        okay.addActionListener(this);
        okay.setEnabled(false);
        welcomeWindow.addWindowListener(closeWindow);
    }
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {
        clientName = nameField.getText();
        if(clientName.length() < 6 && clientName.matches("[a-zA-Z]+")){
            okay.setEnabled(true);
        }
        else{
            okay.setEnabled(false);
            }
    }
    private static WindowListener closeWindow = new WindowAdapter(){
        public void windowClosing(WindowEvent e) {
        e.getWindow().dispose();
        }
        };
        
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == okay){
            
            //WindowOne windowOne = new WindowOne();
            
            dialog1 = new WindowOne();
            welcomeWindow.setVisible(false);
            
        }
        //if(e.getSource() == nameField){
            //clientName = nameField.getText();
        //}
        if(e.getSource() == colorField){
            clientColor = colorField.getText();
        }
    }

    private static void createAndShowGUI(){
        final DocGUI startframe = new DocGUI();
        startframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startframe.pack();
    }
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
}
    
    public class WindowOne extends JFrame implements ActionListener{
        JFrame windowOne = new JFrame("New/Open");

        public WindowOne(){
            FlowLayout layout = new FlowLayout();
            windowOne.setLayout(layout);
            windowOne.setSize(300, 150);
            windowOne.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            windowOne.setLocationRelativeTo(null);
            windowOne.setVisible(true);
            
            ImageIcon newicon = new ImageIcon ("/Users/vicli/Documents/workspace/vicli-liherman-miren/proj2/src/resources/newicon.png");
            newButton = new JButton(newicon);
            newButton.setName("newButton");
            
            ImageIcon openicon = new ImageIcon("/Users/vicli/Documents/workspace/vicli-liherman-miren/proj2/src/resources/openicon.png");
            openButton = new JButton(openicon);
            openButton.setName("openButton");
            
            windowOne.add(newButton);
            newButton.setBounds(30, 10, 100, 100);
            newButton.addActionListener(this);
            windowOne.add(openButton);
            openButton.setBounds(170, 10, 100, 100);
            openButton.addActionListener(this);
            
            
        }
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == newButton){
                nameWindow = new NameWindow();
                openButton.setEnabled(false);
                newButton.setEnabled(false);
                //windowOne.setVisible(false);
            }
            
            if(e.getSource() == openButton){
                fileWindow = new FileWindow();
                windowOne.setVisible(false);
            }
            
        }
        
    }
    public class NameWindow extends JFrame implements ActionListener, WindowListener, KeyListener {
        private JFrame frm = new JFrame();
        private JFrame nameWindow = new JFrame("New");
        private JLabel nameInstruction;
        private JButton nameOkay;
        private JButton nameCancel;
        private JTextField nameField;
        
        public NameWindow(){
            FlowLayout layout = new FlowLayout();
            nameWindow.setLayout(layout);
            nameWindow.setSize(500, 150);
            nameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            
            nameWindow.setLocationRelativeTo(null);
            frm.setLocationRelativeTo(null);
            nameWindow.addWindowListener(this);
            nameWindow.setVisible(true);
            
            nameInstruction = new JLabel("Document Name (Letters/numbers without spaces only):");
            nameInstruction.setBounds(60, 20, 400, 20);
            nameWindow.add(nameInstruction);
           
            nameField = new JTextField();
            nameField.setName("nameField");
            nameField.setBounds(140, 50, 200, 20);
            nameWindow.add(nameField);
            nameField.addKeyListener(this);
            
            nameOkay = new JButton("Okay");
            nameOkay.setEnabled(false);
            nameOkay.setName("nameOkay");
            nameWindow.add(nameOkay);
            nameOkay.setBounds(150, 80, 80, 30);
            nameOkay.addActionListener(this);
            
            nameCancel = new JButton("Cancel");
            nameCancel.setName("nameCancel");
            nameWindow.add(nameCancel);
            nameCancel.setBounds(250, 80, 80, 30);
            nameCancel.addActionListener(this);
        }

        //Key Listener
        // we listen to what the user is typing. if the user types a valid name, we enable the
        // okay button. 
        @Override
        public void keyPressed(KeyEvent arg0) {}
        @Override
        public void keyTyped(KeyEvent arg0) {}
        @Override
        public void keyReleased(KeyEvent arg0) {
            String text = nameField.getText();
            if(text.length() > 0 && text.matches("[a-zA-Z0-9]+")){
                nameOkay.setEnabled(true);
            }
            else{
                nameOkay.setEnabled(false);
                }
        }
        
        //Action Listener
        // when we click okay, we save the name of the document and close the name window
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == nameOkay){
                docName = nameField.getText();
                nameWindow.dispose();
               
                docWindow = new DocumentWindow();
            }
            if(e.getSource() == nameCancel){
                nameWindow.dispose();
                openButton.setEnabled(true);
                newButton.setEnabled(true);
            }
        }
        
        
        //Window Listener
        @Override
        public void windowActivated(WindowEvent arg0) {}

        @Override
        public void windowClosed(WindowEvent arg0) {
            openButton.setEnabled(true);
            newButton.setEnabled(true);    
        }

        @Override
        public void windowClosing(WindowEvent arg0) {}
        @Override
        public void windowDeactivated(WindowEvent arg0) {}
        @Override
        public void windowDeiconified(WindowEvent arg0) {}
        @Override
        public void windowOpened(WindowEvent arg0) {}
        @Override
        public void windowIconified(WindowEvent e) {}
        

    }
    /**
     * Main window that displays the Document 
     * @author vicli
     *
     */
    public class DocumentWindow extends JFrame implements ActionListener, DocumentListener{
        
        private JTextPane docpane; 
        private JPanel menu;
        private JTextArea content;
        
        public DocumentWindow(){
            super(docName);
            docpane = new JTextPane();
            FlowLayout layout = new FlowLayout();
            docpane.setLayout(layout);
            docpane.setName("docpane");
            docpane.setCaretPosition(0);
            //docpane.setCaretColor(Color.decode(clientColor));
            //docBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 50);
            //docpane.setBorder(docBorder);
            
            docpane.setMargin(new Insets(100,100,100,100));
            StyledDocument styled = docpane.getStyledDocument();
            displayedDoc = (AbstractDocument) styled;
            JScrollPane scroll = new JScrollPane(docpane);
            scroll.setPreferredSize(new Dimension(200, 200));
            
            // StatusPane keeps track of the caret location; this will make debugging 
            // less painful, and also allows user to know where their cursor is.
            JPanel statusPane = new JPanel(new GridLayout(1,1));
            CaretListenerLabel caretLabel = new CaretListenerLabel("Caret Status");
            statusPane.add(caretLabel);
            
            //Adding statusPane to the main pane.
            getContentPane().add(statusPane, BorderLayout.PAGE_END);
            
            //Creating the Menubar.
            action = createActions(docpane);
            JMenu editMenu = createEditMenu();
            JMenuBar menuBar = new JMenuBar();
            menuBar.add(editMenu);
            setJMenuBar(menuBar);
            
            //Adding key bindings for keyboard shortcuts (if necessary)
            addBindings();
            
            //Initial text is empty, set caret position
            docpane.setCaretPosition(0);
            
            // Listener for undoable edits and for caret changes
            displayedDoc.addUndoableEditListener(new UndoEditListener());
            docpane.addCaretListener(caretLabel);
            displayedDoc.addDocumentListener(this);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            pack();
            
            add(docpane);
            setSize(1200, 800);
            setLocationRelativeTo(null);
            setVisible(true);
        }
        
        @Override
        public void changedUpdate(DocumentEvent e) {}
        @Override
        public void insertUpdate(DocumentEvent e) {}
        @Override
        public void removeUpdate(DocumentEvent e) {}
        
        private HashMap<Object, Action>  createActions(JTextComponent comp){
            HashMap<Object, Action> action = new HashMap<Object, Action>();
            Action [] actionArray = comp.getActions();
            for (int i = 0; i< actionArray.length; i++){
                Action a = actionArray[i];
                action.put(a.getValue(Action.NAME), a);
            }
            return action;
        }
        
        // Used to listen for undoable edits. 
        protected class UndoEditListener implements UndoableEditListener{
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                undo.addEdit(e.getEdit());
                undoAction.updateUndoState();
                redoAction.updateRedoState();
            }        
        }
              
        protected void addBindings(){
            InputMap map = docpane.getInputMap();
            
            // ctrl z to redo 
            KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK);
            map.put(key, new UndoAction());
        }
        
        private JMenu createEditMenu() {
            JMenu menu = new JMenu("Edit");
            
            //Add the undo and redo actions that we defined
            undoAction = new UndoAction();
            menu.add(undoAction);
            redoAction = new RedoAction();
            menu.add(redoAction);
            menu.addSeparator();
            
            //We add actions that come from default editor kit
            Action copyAction = new DefaultEditorKit.CopyAction();
            copyAction.putValue(Action.NAME, "Copy");
            Action pasteAction = new DefaultEditorKit.PasteAction();
            pasteAction.putValue(Action.NAME, "Paste");
            Action cutAction = new DefaultEditorKit.CutAction();
            cutAction.putValue(Action.NAME, "Cut");
            menu.add(copyAction);
            menu.add(pasteAction);
            menu.add(cutAction);
                        
            return menu;
        }
        private Action getAction(String name) {
            return action.get(name);
        }

        protected class CaretListenerLabel extends JLabel implements CaretListener {
            public CaretListenerLabel(String label) {
                super(label);
            }

            //Might not be invoked from the event dispatch thread.
            public void caretUpdate(CaretEvent e) {
                displaySelectionInfo(e.getDot(), e.getMark());
            }

            //This method can be invoked from any thread.  It 
            //invokes the setText and modelToView methods, which 
            //must run on the event dispatch thread. We use
            //invokeLater to schedule the code for execution
            //on the event dispatch thread.
            protected void displaySelectionInfo(final int dot,final int mark) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        if (dot == mark) {  // no selection
                            try {
                                Rectangle caretCoords = docpane.modelToView(dot);
                                //Convert it to view coordinates.
                                setText("Caret Position: " + dot
                                        + ", view location = ["
                                        + caretCoords.x + ", "
                                        + caretCoords.y + "]"
                                        + newline);
                            } catch (BadLocationException ble) {
                                setText("Caret Position: " + dot + newline);
                            }
                        } else if (dot < mark) {
                            setText("selection from: " + dot
                                    + " to " + mark + newline);
                        } else {
                            setText("selection from: " + mark
                                    + " to " + dot + newline);
                        }
                    }
                });
            }
        }
        class UndoAction extends AbstractAction {
            public UndoAction() {
                super("Undo");
                setEnabled(false);
            }
     
            public void actionPerformed(ActionEvent e) {
                try {
                    undo.undo();
                } catch (CannotUndoException ex) {
                    System.out.println("Unable to undo: " + ex);
                    ex.printStackTrace();
                }
                updateUndoState();
                redoAction.updateRedoState();
            }
     
            protected void updateUndoState() {
                if (undo.canUndo()) {
                    setEnabled(true);
                    putValue(Action.NAME, undo.getUndoPresentationName());
                } else {
                    setEnabled(false);
                    putValue(Action.NAME, "Undo");
                }
            }
        }
        
        protected class MyUndoableEditListener implements UndoableEditListener {
            public void undoableEditHappened(UndoableEditEvent e) {
                //Remember the edit and update the menus.
                undo.addEdit(e.getEdit());
                undoAction.updateUndoState();
                redoAction.updateRedoState();
                }
        }
        
        class RedoAction extends AbstractAction {
            public RedoAction() {
                super("Redo");
                setEnabled(false);
            }
     
            public void actionPerformed(ActionEvent e) {
                try {
                    undo.redo();
                } catch (CannotRedoException ex) {
                    System.out.println("Unable to redo: " + ex);
                    ex.printStackTrace();
                }
                updateRedoState();
                undoAction.updateUndoState();
            }
     
            protected void updateRedoState() {
                if (undo.canRedo()) {
                    setEnabled(true);
                    putValue(Action.NAME, undo.getRedoPresentationName());
                } else {
                    setEnabled(false);
                    putValue(Action.NAME, "Redo");
                }
            }
        }
        

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            
        }
        
    }
    /**
     * Window when we click "open"
     * @author vicli
     *
     */
    public class FileWindow extends JFrame implements ActionListener{
        private JFrame fileWindow = new JFrame ("Files");
        public FileWindow(){
            FlowLayout layout = new FlowLayout();
            fileWindow.setLayout(layout);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            
        }
    }
}