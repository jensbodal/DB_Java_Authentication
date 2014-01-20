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
    private static final int FRAME_HEIGHT = 250;
    private static final int FRAME_X = 40;
    private static final int FRAME_Y = 40;
    private static final String TITLE = "Dropbox Authentication Example";
    private static final BorderLayout MAINLAYOUT = new BorderLayout();
    private static DB_Authentication DBA;
    // private variables
    private JPanel mainFrame;
    
    public DB_AppGUI() {
        DBA = new DB_Authentication();
        // Create GUI
        mainFrame = new JPanel();
        mainFrame.setLayout(MAINLAYOUT);
        
        // Add Components
        
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
                DBA.getAccountInfo();
            }
            catch (NullPointerException e) {
            }
        }
    };
    
    
}
