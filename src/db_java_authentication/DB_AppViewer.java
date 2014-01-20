/*
 * DB_AppViewer.java
 */ 

package db_java_authentication;

import java.io.IOException;

/**
 *
 * @author Jens Bodal
 * @version
 *
 * Compiler Java 1.7
 * OS: Windows 7, OSX
 * Hardware: PC
 *
 * Date, JB completed vX.x
 */
public class DB_AppViewer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        DB_Authentication DBA = new DB_Authentication();
        DBA.getAccountInfo();
    }
    
    

}
