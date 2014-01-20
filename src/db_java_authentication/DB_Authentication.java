/*
 * DB_Authentication.java
 */ 

package db_java_authentication;
import com.dropbox.core.*;
import com.dropbox.core.json.*;
import java.io.*;
import java.util.Locale;
/**
 *
 * @author Jens Bodal
 * Example code used from Drpobox API SDK examples
 */
public class DB_Authentication {
    // final variables
    private static final String USERHOME = System.getProperty("user.home");
    private static final String HOST_OS = System.getProperty("os.name");
    private static final int TAB = 4;
    
    // private variables
    private static String DB_AuthFilePath = (USERHOME + "/DB_AuthFiles");
    private File appinfo_file = new File(DB_AuthFilePath + 
            "/App_Info.json");
    private File appauth_file = new File(DB_AuthFilePath + 
            "/App_Auth.json");
    private DbxAppInfo appInfo = null;
    DbxClient DBC;
    
    public DB_Authentication() {
        setDB_AuthFiles();
        if (checkAuthFiles()) {
            DB_Auth();
        }
        else {
            System.out.println("Missing one or more authentication files");
            System.out.println("Call DB_NewAuth()...");
        }
    }
    
    public void init_newDBAuth() {
        try {
            DB_NewAuth();
        }

        catch (IOException e) {
            System.out.println(e);
        }
    }
    
    private void setDB_AuthFiles() {
        File DB_AuthFiles = new File(DB_AuthFilePath);
        if (HOST_OS.toLowerCase().contains("win")) {
            DB_AuthFilePath = DB_AuthFilePath.replace("/", "\\");
        }
        if (!DB_AuthFiles.exists()) {
            System.out.printf("Directory does not exists, attempting to "
                    + "create directory: %s%n", DB_AuthFilePath);
            try {
                DB_AuthFiles.mkdir();
            }
            catch (SecurityException e) {
                System.out.printf("Error creating DB_AuthFilePath: %s%n",
                        e);
            }
        }
    }
    
    private boolean checkAuthFiles() {
        if (!appinfo_file.exists()) {
            System.out.println("appinfo_file missing");
            return false;
        }
        else if (!appauth_file.exists()) {
            System.out.println("appauth_file missing");
            return false;
        }
        else {
            return true;
        }
    }
    
    private void DB_NewAuth() throws IOException {
        try {
            appInfo = DbxAppInfo.Reader.readFromFile(appinfo_file);
        }
        catch (JsonReader.FileLoadException e) {
            System.out.println(e);
            setAppInfo();
        }
        
        // Run through Dropbox API authorization process
        String userLocale = Locale.getDefault().toString();
        DbxRequestConfig requestConfig = new DbxRequestConfig(
                "DB_Authorize", userLocale);
        DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(
                requestConfig, appInfo);

        String authorizeUrl = webAuth.start();
        System.out.println("1. Go to " + authorizeUrl);
        System.out.println("2. Click \"Allow\" (you might have to log in).");
        System.out.println("3. Copy the authorization code.");
        System.out.print("Enter the authorization code here: ");
        
        String code = "";
        try {
            code = new BufferedReader(
                    new InputStreamReader(System.in)).readLine();
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
        DbxAuthInfo authInfo = new DbxAuthInfo(
                authFinish.accessToken, appInfo.host);
        String argAuthFileOutput = appauth_file.getAbsolutePath();
        try {
            DbxAuthInfo.Writer.writeToFile(authInfo, argAuthFileOutput);
            System.out.println("Saved authorization information to \"" + 
                    argAuthFileOutput + "\".");
            DB_Auth();
        }
        catch (IOException ex) {
            System.err.println("Error saving to <auth-file-out>: " + 
                    ex.getMessage());
            System.err.println("Dumping to stderr instead:");
            DbxAuthInfo.Writer.writeToStream(authInfo, System.err);
        }
    }
    
    private void DB_Auth() {
        // Read auth info file.
        DbxAuthInfo authInfo;
        try {
            authInfo = DbxAuthInfo.Reader.readFromFile(appauth_file);
        }
        catch (JsonReader.FileLoadException ex) {
            System.err.println("Error loading <auth-file>: " + ex.getMessage());
            System.exit(1); return;
        }
        
        // Create a DbxClient, which is what you use to make API calls.
        String userLocale = Locale.getDefault().toString();
        DbxRequestConfig requestConfig = new DbxRequestConfig(
                "DB_Authentication.java", userLocale);
        DBC = new DbxClient(
                requestConfig, authInfo.accessToken, authInfo.host);
    }
    
    private void accountInfo() {
        // Make the /account/info API call.
        DbxAccountInfo dbxAccountInfo;
        try {
            dbxAccountInfo = DBC.getAccountInfo();
        }
        catch (DbxException ex) {
            System.err.println("Error in getAccountInfo(): " + ex.getMessage());
            System.exit(1); return;
        }
        System.out.println("User's account info: " + 
                dbxAccountInfo.toStringMultiline());
    }
    
    public void getAccountInfo() {
        accountInfo();
    }
    
    private void setAppInfo() throws IOException {
        String app_key;
        String app_secret;
        String app_info;
        
        System.out.print("Enter your APP Key: ");
        app_key = new BufferedReader(
                new InputStreamReader(System.in)).readLine();
        
        System.out.print("Enter your APP Secret: ");
        app_secret = new BufferedReader(
                new InputStreamReader(System.in)).readLine();
        
        app_key = String.format("\"key\" : \"%s\",", app_key);
        app_secret = String.format("\"secret\" : \"%s\"", app_secret);
        int keyPadding = app_key.length() + TAB;
        int secretPadding = app_secret.length() + TAB;
        
        app_info = String.format(
                "{%n%" + keyPadding + "s%n%" + secretPadding + "s%n}", 
                app_key, app_secret);
        
        appinfo_file.createNewFile();
        try (FileWriter write = new FileWriter(appinfo_file)) {
            write.write(app_info);
        }
        System.out.println("AppInfo.json file created");
        try {
            appInfo = DbxAppInfo.Reader.readFromFile(appinfo_file);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
