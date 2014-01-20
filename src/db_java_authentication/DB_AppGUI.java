/*
 * DB_AppGUI.java
 */ 

package db_java_authentication;

import javax.swing.*;


/**
 *
 * @author Jens Bodal
 */
public class DB_AppGUI extends JFrame {

    // private variables
    private JPanel mainFrame;
    
    public DB_AppGUI() {
        mainFrame = new JPanel();
        
    }
    
    
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // File Menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem open = new JMenuItem("Open...");
        JMenuItem exit = new JMenuItem("Exit");
        fileMenu.add(open);
        fileMenu.add(exit);
        
        // Action Menu
        JMenu actionMenu = new JMenu("Dropbox");
        JMenuItem authenticateDropbox = 
                new JMenuItem("New Dropbox Authentication");
        actionMenu.add(authenticateDropbox);
        
        // Create menuBar
        menuBar.add(fileMenu);
        menuBar.add(actionMenu);
        
        return menuBar;
    }
}
