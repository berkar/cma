package cma.common;

import cma.Constants;

import java.net.URL;
import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Bertil
 * Date: Nov 2, 2003
 * Time: 10:03:32 PM
 * To change this template use Options | File Templates.
 */
public class BrowserHelper
{
    public static void view(String url)
    {
        // Show the result if a browser path exists
        try {
            String browserPath = ApplicationPropertyManager.getInstance(Constants.APPLICATION_NAME).getBrowserPath();
            if (browserPath != null) {
                URL tmp = new URL(browserPath);
                File file = new File(tmp.getFile());
                if(file.exists()) {
                    Runtime.getRuntime().exec(file.getPath() + " " + url);
                }
            }
        }
        catch (IOException e) {
            System.err.println("Could not view " + url + "in a browser!");
            e.printStackTrace(System.err);
        }
    }
}
