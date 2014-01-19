/*
 * DB_Authentication.java
 */ 

package db_java_authentication;
import com.dropbox.core.*;
import com.dropbox.core.json.JsonReader.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
/**
 *
 * @author Jens Bodal
 */
public class DB_Authentication {
    
    
    public DB_Authentication() {
        // Default Constructor
    }
    
    public void DB_NewAuth() throws IOException {
        DbxAppInfo appInfo = null;
        try {
            appInfo = DbxAppInfo.Reader.readFromFile("auth/app-info.json");
        }
        catch (FileLoadException e) {
            System.out.printf("DB_NewAuth() Reader error: %s%n", e);
        }
        
        // Run through Dropbox API authorization process
        String userLocale = Locale.getDefault().toString();
        DbxRequestConfig requestConfig = new DbxRequestConfig("examples-authorize", userLocale);
        DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(requestConfig, appInfo);

        String authorizeUrl = webAuth.start();
        System.out.println("1. Go to " + authorizeUrl);
        System.out.println("2. Click \"Allow\" (you might have to log in first).");
        System.out.println("3. Copy the authorization code.");
        System.out.print("Enter the authorization code here: ");
        
        String code = "";
        try {
            code = new BufferedReader(new InputStreamReader(System.in)).readLine();
            if (code == null) {
                System.exit(1); return;
            }
            code = code.trim();
        }
        catch (IOException e) {
            System.out.printf("BufferedReader error: %s%n", e);
        }
        

        DbxAuthFinish authFinish;
        try {
            authFinish = webAuth.finish(code);
        }
        catch (DbxException ex) {
            System.err.println("Error in DbxWebAuth.start: " + ex.getMessage());
            System.exit(1); return;
        }

        System.out.println("Authorization complete.");
        System.out.println("- User ID: " + authFinish.userId);
        System.out.println("- Access Token: " + authFinish.accessToken);

        // Save auth information to output file.
        DbxAuthInfo authInfo = new DbxAuthInfo(authFinish.accessToken, appInfo.host);
        String argAuthFileOutput = "auth/something.json";
        try {
            DbxAuthInfo.Writer.writeToFile(authInfo, argAuthFileOutput);
            System.out.println("Saved authorization information to \"" + argAuthFileOutput + "\".");
        }
        catch (IOException ex) {
            System.err.println("Error saving to <auth-file-out>: " + ex.getMessage());
            System.err.println("Dumping to stderr instead:");
            DbxAuthInfo.Writer.writeToStream(authInfo, System.err);
            System.exit(1); return;
        }
    }
    
    public void DB_Auth() {
        
    }
    
}
