/*
 * DB_AppGUI.java
 */ 

package db_java_authentication;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


/**
 *
 * @author Jens Bodal
 */
public class DB_AppGUI extends JFrame {
    private static final int FRAME_WIDTH = 700;
    private static final int FRAME_HEIGHT = 400;
    private static final int FRAME_X = 40;
    private static final int FRAME_Y = 40;
    private static final String TITLE = "Dropbox Authentication Example";
    private static final BorderLayout MAINLAYOUT = new BorderLayout();
    private static DB_Authentication DBA;
    // private variables
    private JPanel mainFrame;
    private JTextArea mainTextArea = new JTextArea();
    private JTextField mainTextField = new JTextField();
    
    public DB_AppGUI() {
        DBA = new DB_Authentication();
        // Create GUI
        mainFrame = new JPanel();
        mainFrame.setLayout(MAINLAYOUT);
        
        // Add Components
        mainFrame.add(mainTextArea, BorderLayout.NORTH);
        mainFrame.add(mainTextField, BorderLayout.SOUTH);
        
        // Display GUI
        this.setResizable(false);
        this.setBounds(FRAME_X, FRAME_Y, FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle(TITLE);
        this.add(mainFrame);
        
        // Add Menu Bar
        this.setJMenuBar(createMenuBar());        
    }
    
    
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // File Menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem open = new JMenuItem("Open...");
        fileMenu.add(open);
        JMenuItem exit = new JMenuItem("Exit");
        fileMenu.add(exit);
        exit.addActionListener(menu_file_exit);
        
        // Dropbox Menu
        JMenu dropboxMenu = new JMenu("Dropbox");
        JMenuItem getAccountInfo = 
                new JMenuItem("Get Account Information");
        getAccountInfo.addActionListener(DBAL_getAccountInfo);
        dropboxMenu.add(getAccountInfo);
        
        // Tools Menu
        JMenu toolsMenu = new JMenu("Tools");
        JMenuItem authenticateDropbox = 
                new JMenuItem("New Dropbox Authentication");
        authenticateDropbox.addActionListener(DBAL_newAuthentication);
        toolsMenu.add(authenticateDropbox);

        
        // Create menuBar
        menuBar.add(fileMenu);
        menuBar.add(dropboxMenu);
        menuBar.add(toolsMenu);
        
        return menuBar;
    }
    

    
    
    // Action Listeners
    private ActionListener menu_file_exit = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                System.exit(0);
            }
            catch (NullPointerException e) {
            }
        }
    };
    
    private ActionListener DBAL_newAuthentication = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                DBA.init_newDBAuth();
            }
            catch (NullPointerException e) {
            }
        }
    };
    
    private ActionListener DBAL_getAccountInfo = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                mainTextArea.setText(DBA.getAccountInfo());
                mainTextField.setText(DBA.getRequestConfig());
            }
            catch (NullPointerException e) {
            }
        }
    };
    
    
}
