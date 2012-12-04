package gui;

import gui.DocGUI.DocumentWindow.RedoAction;
import gui.DocGUI.DocumentWindow.UndoAction;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import backend.Server;
import backend.ServerDocument;

public class DocGUI extends JFrame implements ActionListener, KeyListener{
    
    /**
     * This is the DocGUI, which takes care of all windows that the user will see. 
     * There are 5 main windows, as listed below:
     * 1. Welcome window (sets name and color of that particular client, which is used later on when editing)
     *      Welcome Message : "Welcome to Bone Editor!..."
     *      1. Name Field (6 letters max)      (okay button only enabled when name is filled and < 6 letters)
     *      2. Color Field                      (optional)
     *      Okay button        (brings user to Window 2)
     * 
     * 2. New/Open screen
     *       Three possible options:
     *       1. New:     New button, New Screen pops up   
     *       2. Open:   Open button, Open Screen pops up
     *       3. Close:   client disconnects from server
     * 
     * 3. Open screen  
     *       1. Client chooses from existing inventory of ServerDocument. Server will open the appropriate document in Document Screen   
     *       2. Cancel: client goes back to screen 1.
     *       
     * 4. New Screen       
     *       1. Client types in ServerDocument name, and clicks Okay. creates a new ServerDocument in the Server under the name, and opens document in Document Screen.
     *       2. Cancel: client goes back to screen 1.
     * 
     * 5. Document Screen
     *      1. Menu bars up on top: Edit drop down menu    (Style drop down menu may be implemented later)
     *      2. Document edit area (a JTextPane over a grey panel)
     *    Closing the Document Screen saves the Document, returns to screen 1.      
     *       
     *
     * >>> TESTING STRATEGY <<<<
     * Currently: Manual Testing        (If time allows, figure out how to do it via JUnit)
     * 
     * GUI LOOK TESTING
     * The look of the GUI will simply be tested manually; all tests below must be passed.
     *      1. All windows open up at the center of the screen
     *      2. Windows are all frames: have a close, minimize, and expand button on frame
     *      3. all components laid out in the correct place / gridbaglayout does work
     *      4. Check GUIs on Windows and Mac computers to ensure layout still holds
     *      5. Enlarge and shrink windows to make sure layout still holds
     *      6. Check scrolling works (not yet implemented)   
     * 
     * GUI FUNCTIONALITY TESTING
     * We will test functionality by running the Server and going through each possible option
     *      1. Buttons are enabled only when user gives a valid input.
     *      2. Buttons can be pressed.
     *      3. Buttons lead to the right screen (check design above)
     *      4. Client can only disconnect on New/Open screen and Welcome screen, by closing the window.
     *      5. Closing all other screens should redirect user to screen 1
     *      6. Text in TextFields are read and stored as appropriate
     *      7. Open window opens up the window with a list of documents that are on the server
     *      8. JComboBox of documents does indeed list all the documents on the server
     *      9. JComboBox allows you to select document you want
     *      10. selecting document and clicking okay opens a Document window
     *      11. Loaded document is the correct document
     *      12. closing Document saves the document, checked by closing and reopening document
     *      13. creating a new document saves the document under the given name
     *      14. creating a new document, closing it, and then opening document allows you to open
     *          document you just created
     *      15. Multiple documents can be created and saved
     *      16. Undo and Redo works
     *      17. Copy and paste works
     *      18. Styling works (to be implmented) 
     * (Refer to design document for possible transition paths between windows. Each transition path 
     * will be traversed for testing purposes)     
     *
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
    
    private Document displayedDoc;
    protected UndoAction undoAction;
    protected RedoAction redoAction;
    protected UndoManager undo = new UndoManager();
    private String newline = "\n";
    private HashMap<Object, Action> action;
    private Border docBorder;
    private boolean isNew;
    
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
    //Key listener to check if the user input is valid, and only enabling the button when it is
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
    // Action listeners for buttons and textfields    
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
    /**
     * Creates and Shows the GUI
     */
    private static void createAndShowGUI(){
        final DocGUI startframe = new DocGUI();
        startframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startframe.pack();
    }
    
    /**
     * Runs the GUI
     * 
     */
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
}
    /**
     * This is window 2 (refer to above)
     * Creates a new window 2.
     *
     */
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
        /**
         * Action listener so clicking the button performs its intended function
         */
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == newButton){
                nameWindow = new NameWindow();
                openButton.setEnabled(false);
                newButton.setEnabled(false);
                //windowOne.setVisible(false);
            }
            
            if(e.getSource() == openButton){
                fileWindow = new FileWindow();
                isNew = false;
            }
            
        }
        
    }
    /**
     * This is window 4 (refer to above)
     * Creates a new window 4.
     *
     */
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
        /**
         * Key Listener
         * we listen to what the user is typing. if the user types a valid name, we enable the
         * okay button.
         */
        @Override
        public void keyPressed(KeyEvent arg0) {}
        @Override
        public void keyTyped(KeyEvent arg0) {}
        @Override
        public void keyReleased(KeyEvent arg0) {
            String text = nameField.getText();
            if(!Server.docListEmptyCheck()){
                if(text.length() > 0 && text.matches("[a-zA-Z0-9]+") &&!existingCheck(Server.getDocs(), text)){
                    nameOkay.setEnabled(true);
                    }
            }
            else if(text.length() > 0 && text.matches("[a-zA-Z0-9]+")){
                nameOkay.setEnabled(true);
            }
            else{
                nameOkay.setEnabled(false);
                }
        }
        
        private boolean existingCheck(ArrayList<String> titles, String newdoc){
            if(!titles.equals(null)){
                for(int i = 0; i < titles.size(); i++){
                    if(titles.get(i).equals(newdoc)){
                        return true;
                    }
                }
            }   
            return false;
        }
        /**
         * Action Listener
         * when we click okay, we save the name of the document and close the name window
         * When we click cancel, we dispose of the window and return to Window 1
         */

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == nameOkay){           
                docName = nameField.getText();
                System.out.println("docname is now" + docName);
                Server.addDocument(docName);
                nameWindow.dispose();
                docWindow = new DocumentWindow();
            }
            if(e.getSource() == nameCancel){
                nameWindow.dispose();
                openButton.setEnabled(true);
                newButton.setEnabled(true);
            }
        }
        
        /**
         * Window listener to ensure we don't disconnect the client, and we reactive all 
         * buttons on window 1
         */
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
     * This is window 5, as described above.
     *
     */
    public class DocumentWindow extends JFrame implements ActionListener, DocumentListener,KeyListener, WindowListener{
        //write get cursor position
        private JTextPane docpane; 
        private JPanel menu;
        private JTextArea content;
        private JPanel documentPanel;
        private ServerDocument loadDoc;
        
        public DocumentWindow(){
            super(docName);
            docpane = new JTextPane();

            loadDoc = Server.getDocument(docName);
            String content = loadDoc.getDocContent();
            System.out.println("docname is " + docName);
            System.out.println("doc content is" + loadDoc.getDocContent());
            displayedDoc = loadDoc;
            docpane.setText(content);
            documentPanel = new JPanel();
            documentPanel.add(docpane);
            //JPanel stacked = new JPanel(new CardLayout());
            
            JPanel grayPanel = new JPanel();
            grayPanel.setVisible(true);
            grayPanel.setBackground(Color.LIGHT_GRAY);
            grayPanel.setSize(600, 600);
            
            grayPanel.setLocation(0, 0);
            
            FlowLayout layout = new FlowLayout();
            docpane.setLayout(layout);
            docpane.setName("docpane");
            docpane.setCaretPosition(0);
            //docpane.setCaretColor(Color.decode(clientColor));
            //docBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 50);
            //docpane.setBorder(docBorder);
            
            docpane.setMargin(new Insets(100,100,100,100));
            docpane.setBounds(20, 20, 560, 580);
            docpane.setVisible(true);
            
            
            JScrollPane scroll = new JScrollPane(docpane);
            setPreferredSize(new Dimension(450, 500));
            //scroll.setPreferredScrollableViewportSize(docpane.getPreferredScrollableViewportSize());

            
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
            docpane.addKeyListener(this);
            displayedDoc.addDocumentListener(this);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            pack();
            
            add(grayPanel);
            add(docpane);
            add(scroll, BorderLayout.CENTER);
            addWindowListener(this);
            
            setSize(600, 600);
            setLocationRelativeTo(null);
            setVisible(true);
        }
        
        /**
         * Key Listeners. Should invoke a method that sends a message to the server everytime a key is pressed.
         * Still yet to be implemented. 
         */
        @Override
        public void keyPressed(KeyEvent e) {
            e.getKeyChar();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void keyTyped(KeyEvent e) {
            if(String.valueOf(e.getKeyChar()).matches("[a-zA-Z0-9]")){
                
            }
            
        }
        
        /**
         * Handles communication with the server. Follows the following
         * protocol:
         * 
         * Overall structure: clientName messageType messageContents
         * 
         * clientName currentDoc Insert edit -for when there is an insertion
         * edit
         * 
         * clientName currentDoc Remove edit -for when there is a deletion edit
         * 
         * clientName currentDoc SpaceEntered -for when a space is entered, aka
         * an edit finished
         * 
         * clientName currentDoc CursorMoved -for when the cursor is moved, aka
         * an edit finished
         * 
         * clientName NewDoc fileTitle -for when a new file is made with the
         * fileTitle
         * 
         * clientName currentDoc Save -for when the user presses the save button
         * or closes the editor window
         * 
         * clientName currentDoc Disconnect -for when someone exits out of the
         * whole program
         * 
         * @param message
         */
        public void serverMessage (String message){
            
        }
        
        
        /**
         * Document listener that listens to updates. May or may not be needed
         */
        @Override
        public void changedUpdate(DocumentEvent e) {
            
        }
        @Override
        public void insertUpdate(DocumentEvent e) {}
        @Override
        public void removeUpdate(DocumentEvent e) {}
        
        /**
         * Creates a hashmap of actions, used for undo and redo 
         * @param JTextComponent comp
         * @return Action
         */
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
        /**
         * Add bindings to create keyboard shortcuts.
         * Optional, may or may not be implemented       
         */
        private void addBindings(){
            ActionMap actionMap = docpane.getActionMap();
            actionMap.put("Undo", new UndoAction());
            InputMap[] inputMaps =  new InputMap[]{
                    docpane.getInputMap(docpane.WHEN_FOCUSED),
                    docpane.getInputMap(docpane.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT),
                    docpane.getInputMap(docpane.WHEN_IN_FOCUSED_WINDOW)
            };
            for(InputMap i : inputMaps) {
                i.put(KeyStroke.getKeyStroke("control Z"), "Undo");
            }
            
            
            
            // ctrl z to redo 
//            KeyStroke key = KeyStroke.getKeyStroke(Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()+ " Z");
//            inputMap.put(key, "Undo");
//            actionMap.put("Undo", new UndoAction());   
        }
        
        /**
         * Creates the menu bar on top of the document window
         * @return
         */
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
            copyAction.putValue(Action.NAME, "Copy (Ctrl c)");
            Action pasteAction = new DefaultEditorKit.PasteAction();
            pasteAction.putValue(Action.NAME, "Paste (Ctrl v)");
            Action cutAction = new DefaultEditorKit.CutAction();
            cutAction.putValue(Action.NAME, "Cut (Ctrl x)");
            
            menu.add(copyAction);
            menu.add(pasteAction);
            menu.add(cutAction);
                        
            return menu;
        }
        
        /**
         * Action that saves doc
         * @return an action 
         */
        private Action saveAction(){
            return null;
            //TODO fucking save 
        }
        /**
         * Helper method that gets an action by its name
         * @param name
         * @return Aciton
         */
        private Action getAction(String name) {
            return action.get(name);
        }
        /**
         * Method to track where the cursor is, and reports this in a label.
         * @author vicli
         *
         */
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
        
        /**
         *  Class for the UNDO method, with listeners that updates the undo state.
         *
         */
        class UndoAction extends AbstractAction {
            public UndoAction() {
                putValue(Action.NAME, "Undo");
                setEnabled(false);
            }
     
            public void actionPerformed(ActionEvent e) {
                try {
                    if (undo.canUndo()){
                        undo.undo();
                    }                    
                } catch (CannotUndoException ex) {                
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
        /**
         *  Class for the REDO method, with listeners that updates the redo state.
         *
         */
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
        
        /**
         * Action Listener for DocumentWindow, will be needed if we include buttons
         * 
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            
        }
        /**
         * Method for saving the document. Is called every time the document is closed.
         * 
         */
        private void save(){
            String saveContents = docpane.getText();
            loadDoc.updateContent(saveContents);
            System.out.println("save contents" + saveContents);
            System.out.println("document content is" + loadDoc.getDocContent().toString());
        }
        /**
         * Window Listeners for the Document window, tells document to save when window is closed
         */
        @Override
        public void windowActivated(WindowEvent e) {}

        @Override
        public void windowClosed(WindowEvent e) {}

        @Override
        public void windowClosing(WindowEvent e) {
            save();   
        }

        @Override
        public void windowDeactivated(WindowEvent e) {}

        @Override
        public void windowDeiconified(WindowEvent e) {}

        @Override
        public void windowIconified(WindowEvent e) {}

        @Override
        public void windowOpened(WindowEvent e) {}

        
    }
    
    /**
     * Window when we click "open"
     * Displays possible documens to open using the list of saved docuemnts on the server.
     * 
     * @author vicli
     *
     */
    public class FileWindow extends JFrame implements ActionListener{
        private JFrame fileWindow = new JFrame ("Files");
        private JLabel fileLabel = new JLabel("Please select a file:");
        private  JComboBox docList;
        private JButton fileOpen;
        private JButton fileCancel;
        private String fileName;
        
        public FileWindow(){
            FlowLayout layout = new FlowLayout();
            fileWindow.setLayout(layout);
            fileWindow.setSize(300, 150);
            
            fileLabel.setBounds(30,30, 100, 20);
            fileLabel.setVisible(true);
            fileWindow.add(fileLabel);
            
            ArrayList<String> fileNames = Server.getDocs();
            docList = new JComboBox();
            for (String i: fileNames){
                docList.addItem(i);
            }
            docList.setSelectedIndex(0);
            docList.setName("docList");
            docList.setLocation(130, 20);
            
            fileOpen =  new JButton("Open");
            fileOpen.setName("fileOpen");
            fileOpen.setSize(80, 35);
            fileOpen.setLocation(90, 90);
            fileOpen.addActionListener(this);
            
            fileCancel =  new JButton("Cancel");
            fileCancel.setName("fileCancel");
            fileCancel.setSize(80, 35);
            fileCancel.setLocation(200, 90);
            fileCancel.addActionListener(this);
            
            
            fileWindow.add(docList);
            fileWindow.add(fileOpen);
            fileWindow.add(fileCancel);
            fileWindow.setLocationRelativeTo(null);
            fileWindow.setVisible(true);
            //setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource()  == docList){
                fileName = docList.getSelectedItem().toString();
            }
           
           if(e.getSource() == fileOpen){
               fileName = docList.getSelectedItem().toString();
               System.out.println(fileName);
               docName = fileName;
               fileWindow.dispose();
               docWindow = new DocumentWindow();
               // take care of setting text 
           }
           if(e.getSource() == fileCancel){
               fileWindow.dispose();
           }
            
        }
    }
}