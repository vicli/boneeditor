package gui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
    
    private String clientColor;
    private String clientName;
    
    public DocGUI(){
        welcomeWindow.setSize(600, 200);
        welcomeWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeWindow.setVisible(true);
        welcomeWindow.setLocationRelativeTo(null);
        
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
    public class WindowOne extends JFrame{
        private JFrame windowOne = new JFrame("New/Open");
        private JButton newButton; 
        private JButton openButton;
        
        public WindowOne(){
            windowOne.setSize(300, 150);
            windowOne.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            windowOne.setVisible(true);
            windowOne.setLocationRelativeTo(null);
            
            newButton = new JButton ();
            newButton.setName("newButton");
            try {
                Image img = ImageIO.read(getClass().getResource("resources/newicon.bmp"));
                newButton.setIcon(new ImageIcon(img));
              } catch (IOException ex) {
              }
            
            openButton = new JButton("Open");
            openButton.setName("openButton");
            
            windowOne.add(newButton);
            newButton.setBounds(50, 20, 80, 80);
            windowOne.add(openButton);
            openButton.setBounds(170, 20, 80, 80);
            
        }
        
        
    }
}