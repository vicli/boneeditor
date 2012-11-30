package gui;

import java.awt.Dialog;
import java.awt.Image;
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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;



/**
 * This is the DocGUI, which re
 *
 */
public class DocGUI extends JFrame implements ActionListener{
    
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
    
    public DocGUI(){
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
        nameField.addActionListener(this);
        welcomeWindow.add(color);
        color.setBounds(160, 80, 100, 20);
        welcomeWindow.add(colorField);
        colorField.setBounds(310, 80, 120, 20);
        colorField.addActionListener(this);
        welcomeWindow.add(okay);
        okay.setSize(80, 35);
        okay.setLocation(260, 120);
        okay.addActionListener(this);
        welcomeWindow.addWindowListener(closeWindow);
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
            
            /**
             * Prog1 prog = new Prog1();
        prog.setTextFieldText("ls -l");
         
        JDialog dialog = new JDialog(frm, "Prog 1", false);
        dialog.getContentPane().add(prog.getMainPanel());
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
             */
        }
        if(e.getSource() == nameField){
            clientName = nameField.getText();
        }
        if(e.getSource() == colorField){
            clientColor = colorField.getText();
        }
    }

    
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DocGUI();
                
            }
        });
}
    
    public class WindowOne extends JFrame implements ActionListener{
        JFrame windowOne = new JFrame("New/Open");
        
        
        public WindowOne(){
            windowOne.setSize(300, 150);
            windowOne.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            windowOne.setLocationRelativeTo(null);
            windowOne.setVisible(true);
            
            ImageIcon newicon = new ImageIcon ("/Users/vicli/Documents/workspace/vicli-liherman-miren/proj2/src/resources/newicon.jpg");
            newButton = new JButton(newicon);
            newButton.setName("newButton");
            
            
            openButton = new JButton("Open");
            openButton.setName("openButton");
            
            windowOne.add(newButton);
            newButton.setBounds(50, 20, 80, 80);
            newButton.addActionListener(this);
            windowOne.add(openButton);
            openButton.setBounds(170, 20, 80, 80);
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
               // dialog1.setVisible(false);
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
    public class DocumentWindow extends JFrame implements ActionListener{
        public DocumentWindow(){
            
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
        public FileWindow(){
            // TODO have to wait until server is written
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            
        }
    }
}